package rlmixins.handlers.reskillable;

import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import codersafterdark.reskillable.lib.LibMisc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DropGuarantee32Handler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDropGuaranteeLooting(LootingLevelEvent event) {
        if(!(event.getDamageSource().getTrueSource() instanceof EntityPlayer)) return;

        EntityPlayer player = (EntityPlayer)event.getDamageSource().getTrueSource();
        PlayerSkillInfo gatheringSkillData = PlayerDataHandler.get(player).getSkillInfo(ReskillableRegistries.SKILLS.getValue(new ResourceLocation(LibMisc.MOD_ID, "gathering")));
        if(gatheringSkillData.isUnlocked(ReskillableRegistries.UNLOCKABLES.getValue(new ResourceLocation(LibMisc.MOD_ID, "drop_guarantee")))){
            int bonus = (gatheringSkillData.getLevel() >= 32) ? (event.getLootingLevel()+1) : Math.max(1, event.getLootingLevel());
            event.setLootingLevel(bonus);
        }
    }
}
