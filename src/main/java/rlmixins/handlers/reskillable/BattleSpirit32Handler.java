package rlmixins.handlers.reskillable;

import bettercombat.mod.event.RLCombatCriticalHitEvent;
import bettercombat.mod.event.RLCombatSweepEvent;
import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import codersafterdark.reskillable.lib.LibMisc;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BattleSpirit32Handler {

    private static boolean wasCritical = false;

    @SubscribeEvent
    public static void onBattleSpiritCrit(RLCombatCriticalHitEvent event) {
        if (event.getEntityPlayer() == null || event.getEntityPlayer().world.isRemote) return;
        if (!(event.getTarget() instanceof EntityLivingBase)) return;

        PlayerSkillInfo attackSkillData = PlayerDataHandler.get(event.getEntityPlayer()).getSkillInfo(ReskillableRegistries.SKILLS.getValue(new ResourceLocation(LibMisc.MOD_ID, "attack")));
        if (attackSkillData.isUnlocked(
                ReskillableRegistries.UNLOCKABLES.getValue(new ResourceLocation(LibMisc.MOD_ID, "battle_spirit"))) &&
                attackSkillData.getLevel() >= 32) {
            wasCritical = event.isVanillaCritical();
        }
    }

    @SubscribeEvent
    public static void onBattleSpiritKill(RLCombatSweepEvent event) {
        if (event.getEntityPlayer() == null || event.getEntityPlayer().world.isRemote) return;
        if (!(event.getTargetEntity() instanceof EntityLivingBase)) return;

        if(((EntityLivingBase)event.getTargetEntity()).getHealth() <= 0.0F && wasCritical) {
            PlayerSkillInfo attackSkillData = PlayerDataHandler.get(event.getEntityPlayer()).getSkillInfo(ReskillableRegistries.SKILLS.getValue(new ResourceLocation(LibMisc.MOD_ID, "attack")));
            if (attackSkillData.isUnlocked(
                    ReskillableRegistries.UNLOCKABLES.getValue(new ResourceLocation(LibMisc.MOD_ID, "battle_spirit"))) &&
                    attackSkillData.getLevel() >= 32) {
                wasCritical = false;
                event.getEntityPlayer().addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 120, 1));
            }
        }
    }
}
