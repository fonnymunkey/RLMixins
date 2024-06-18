package rlmixins.mixin.waystones;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.blay09.mods.waystones.WaystoneConfig;
import net.blay09.mods.waystones.util.GenerateWaystoneNameEvent;
import net.blay09.mods.waystones.worldgen.NameGenerator;
import net.blay09.mods.waystones.worldgen.RomanNumber;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import rlmixins.handlers.ForgeConfigHandler;
import rlmixins.mixin.vanilla.BiomeAccessor;

import java.util.*;

@Mixin(NameGenerator.class)
public abstract class NameGeneratorMixin extends WorldSavedData {

    @Shadow(remap = false)
    private Map<String, String> BIOME_NAMES;

    @Shadow(remap = false)
    public abstract void init();

    @Shadow(remap = false)
    protected abstract String randomName(Random rand);

    public NameGeneratorMixin(String name) {
        super(name);
    }

    @Unique
    private final Object2IntMap<String> rlmixins$usedNamesMap = new Object2IntOpenHashMap<String>();

    /**
     * @author fonnymunkey
     * @reason reduce bloat on large maps
     */
    @Overwrite(remap = false)
    public String getName(BlockPos pos, int dimension, Biome biome, Random rand) {
        if(this.BIOME_NAMES == null) this.init();

        String name = null;
        List<String> customNames = Arrays.asList(WaystoneConfig.worldGen.customNames);
        Collections.shuffle(customNames);

        for(String tryName : customNames) {
            if(!this.rlmixins$usedNamesMap.containsKey(tryName)) {
                name = tryName;
                break;
            }
        }

        int count = 0;
        if(name == null) {
            boolean isVillage = false;
            if(ForgeConfigHandler.server.villageWaystoneRemoveBiome) {
                World world = DimensionManager.getWorld(dimension);
                if(world != null && !world.isRemote) {
                    Block block = world.getBlockState(pos.down()).getBlock();
                    if(block == Blocks.GLOWSTONE) isVillage = true;
                }
            }
            String biomeSuffix = isVillage ? null : this.BIOME_NAMES.get(((BiomeAccessor)biome).getBiomeName());
            name = this.randomName(rand) + (biomeSuffix != null ? " " + biomeSuffix : "");

            count = this.rlmixins$usedNamesMap.getInt(name);
            if(count > 0) name = name + " " + RomanNumber.toRoman(count);
        }
        if(!ForgeConfigHandler.server.waystonesIgnoreUsedNames) this.rlmixins$usedNamesMap.put(name, count+1);

        GenerateWaystoneNameEvent event = new GenerateWaystoneNameEvent(pos, dimension, name);
        //TODO: Allow changing name from event (Probably not im lazy)
        MinecraftForge.EVENT_BUS.post(event);

        this.markDirty();
        return name;
    }

    /**
     * @author fonnymunkey
     * @reason reduce bloat on large maps
     */
    @Overwrite
    public void readFromNBT(NBTTagCompound compound) {
        if(ForgeConfigHandler.server.waystonesIgnoreUsedNames) return;

        NBTTagList tagList = compound.getTagList("UsedNamesMap", 10);
        for(int i = 0; i < tagList.tagCount(); ++i) {
            NBTTagCompound comp = tagList.getCompoundTagAt(i);
            this.rlmixins$usedNamesMap.put(comp.getString("Name"), comp.getInteger("Val"));
        }
    }

    /**
     * @author fonnymunkey
     * @reason reduce bloat on large maps
     */
    @Overwrite
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if(ForgeConfigHandler.server.waystonesIgnoreUsedNames) return compound;

        NBTTagList tagList = new NBTTagList();
        for(Map.Entry<String, Integer> entry : this.rlmixins$usedNamesMap.entrySet()) {
            NBTTagCompound comp = new NBTTagCompound();
            comp.setString("Name", entry.getKey());
            comp.setInteger("Val", entry.getValue());
            tagList.appendTag(comp);
        }

        compound.setTag("UsedNamesMap", tagList);
        return compound;
    }
}