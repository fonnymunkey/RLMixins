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
import net.minecraftforge.fml.common.FMLCommonHandler;

public abstract class SpawnerControlWrapper {
    private static final String NBT_TAG_SPAWNER_POS = SpawnerControl.MOD_ID + ":spawnerPos";

    /**
     * Based on https://github.com/Pyrofab/SpawnerControl/blob/master/src/main/java/ladysnake/spawnercontrol/SpawnerEventHandler.java#L181
     * Extracted out for compat
     */
    public static void increaseSpawnerCount(Entity entity) {
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
            else return;
            TileEntity tile = world.getTileEntity(BlockPos.fromLong(spawnerPos));
            if(tile instanceof TileEntityMobSpawner) {
                TileEntityMobSpawner spawner = (TileEntityMobSpawner) tile;
                IControllableSpawner handler = CapabilityControllableSpawner.getHandler(spawner);
                if(handler.getConfig().incrementOnMobDeath) handler.incrementSpawnedMobsCount();
            }
        }
    }
}