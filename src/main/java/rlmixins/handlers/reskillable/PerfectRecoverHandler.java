package rlmixins.handlers.reskillable;

import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.lib.LibMisc;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.handlers.ForgeConfigHandler;

public class PerfectRecoverHandler {

    @SubscribeEvent
    public static void onBlockDrops(BlockEvent.HarvestDropsEvent event) {
        if(event.getFortuneLevel() != 0 || event.isSilkTouching()) return;
        if (PlayerDataHandler.get(event.getHarvester()).getSkillInfo(
                ReskillableRegistries.SKILLS.getValue(new ResourceLocation(LibMisc.MOD_ID, "building"))).isUnlocked(
                ReskillableRegistries.UNLOCKABLES.getValue(new ResourceLocation(LibMisc.MOD_ID, "perfect_recover")))) {
            if(ForgeConfigHandler.getReskillablePerfectRecoverList().contains(event.getState().getBlock())) {
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(event.getState().getBlock(), 1));
            }
        }
    }
}
