package rlmixins.handlers.vanilla;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureData;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class MapGenStructureHandler {

    //Essentially the same as RLTweakers handling for Mineshafts, but for Villages
    @SubscribeEvent
    public static void onWorldSave(WorldEvent.Save event) {
        World world = event.getWorld();
        if(world.isRemote) return;
        MapGenStructureData structureData = (MapGenStructureData)world.getPerWorldStorage().getOrLoadData(MapGenStructureData.class, "Village");
        if(structureData == null) return;

        NBTTagCompound structureDataCompound = structureData.getTagCompound();
        List<String> toRemove = new ArrayList<>(structureDataCompound.getKeySet());
        for(String remove : toRemove) structureDataCompound.removeTag(remove);
        if(toRemove.size() > 0) structureData.markDirty();
        toRemove.clear();
    }
}
