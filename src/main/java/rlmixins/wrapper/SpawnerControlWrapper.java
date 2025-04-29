package rlmixins.wrapper;

import ladysnake.spawnercontrol.SpawnerControl;
import ladysnake.spawnercontrol.controlledspawner.CapabilityControllableSpawner;
import ladysnake.spawnercontrol.controlledspawner.IControllableSpawner;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public abstract class SpawnerControlWrapper {
    private static final String NBT_TAG_SPAWNER_POS = SpawnerControl.MOD_ID + ":spawnerPos";

    /**
     * Based on https://github.com/Pyrofab/SpawnerControl/blob/master/src/main/java/ladysnake/spawnercontrol/SpawnerEventHandler.java#L181
     * Extracted out for compat
     * returns false and cancels conversion if too many mobs killed/converted
     */
    public static boolean increaseSpawnerCount(Entity entity) {
        NBTTagCompound data = entity.getEntityData();
        if(data.hasKey(NBT_TAG_SPAWNER_POS)) {
            NBTBase nbt = entity.getEntityData().getTag(NBT_TAG_SPAWNER_POS);
            World world;
            long spawnerPos;
            if(nbt instanceof NBTTagCompound) {
                spawnerPos = ((NBTTagCompound)nbt).getLong("pos");
                world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(((NBTTagCompound) nbt).getInteger("dimension"));
            }
            else if(nbt instanceof NBTTagLong) {
                spawnerPos = ((NBTTagLong) nbt).getLong();
                world = entity.getEntityWorld();
            }
            else return false;

            TileEntity tile = world.getTileEntity(BlockPos.fromLong(spawnerPos));
            if(tile instanceof TileEntityMobSpawner) {
                TileEntityMobSpawner spawner = (TileEntityMobSpawner) tile;
                IControllableSpawner handler = CapabilityControllableSpawner.getHandler(spawner);
                if(handler.getConfig().incrementOnMobDeath){
                    if(handler.getSpawnedMobsCount() == handler.getConfig().mobThreshold - 1)
                        registerBrokenSpawner(world.provider.getDimension(), spawnerPos);
                    handler.incrementSpawnedMobsCount();
                }
                return true;
            }
            //If spawner is broken, refuse conversion if above grace threshold
            else
                return incrementBrokenSpawner(world.provider.getDimension(), spawnerPos);
        }
        return true;
    }

    //dimension, <spawnerPos, deathCount>
    private static final Map<Integer, Map<Long, Integer>> brokenSpawnerMobCounter = new HashMap<>();

    public static void registerBrokenSpawner(int dimId, long spawnerPos){
        if(!brokenSpawnerMobCounter.containsKey(dimId))
            brokenSpawnerMobCounter.put(dimId, new HashMap<>());

        //putIfAbsent -> If someone puts a spawner back at the same position, we do not update the value back to 0
        //since that is definitely only happening if someone's trying to cheese
        brokenSpawnerMobCounter.get(dimId).putIfAbsent(spawnerPos, 0);
    }

    public static boolean incrementBrokenSpawner(int dimId, long spawnerPos){
        Map<Long, Integer> dimMap = brokenSpawnerMobCounter.get(dimId);
        if(dimMap == null)
            //Should almost never happen, would need the whole dimension to never have had a broken spawner, but the current dying mob still have a spawnerPos tag at a position where no spawner is anymore
            //So this could only be achieved by restarting the server without losing the mobs to despawning, after the spawner has broken already
            return false;
        else {
            Integer deadMobsCount = dimMap.get(spawnerPos);
            if (deadMobsCount != null && deadMobsCount < ForgeConfigHandler.server.spawnerControlGraceThreshold) {
                //Count dead mobs until it hits grace threshold
                dimMap.put(spawnerPos, ++deadMobsCount);
                brokenSpawnerMobCounter.put(dimId, dimMap);
                return true;
            } else {
                //afterward the mobs get summoned tag to not drop anything
                //same thing happens if we don't have an entry for the spawner anymore (after restart or after removing the entry here)
                dimMap.remove(spawnerPos);
                return false;
            }
        }
    }

    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event){
        //The map does not need to be saved on file.
        // This only matters if people leave their world after breaking a spawner and return to mobs that were spawned by the spawner. Those won't drop anything
        // It WOULD be possible to save the map in a data file (and erase old entries regularly) but i feel like that's absolute overkill
        // The map needs to be cleared however, as it otherwise would move over to other saves in singleplayer until a proper restart
        if(!event.getWorld().isRemote)
            brokenSpawnerMobCounter.clear();
    }
}