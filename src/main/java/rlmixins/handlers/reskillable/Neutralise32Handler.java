package rlmixins.handlers.reskillable;

import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import codersafterdark.reskillable.lib.LibMisc;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Neutralise32Handler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onNeutraliseFirstHit(AttackEntityEvent event){
        if(event.getEntityPlayer() == null || event.getEntityPlayer().world.isRemote) return;
        if(!(event.getTarget() instanceof EntityLivingBase)) return;
        if(((EntityLivingBase) event.getTarget()).getAttackingEntity() == event.getEntityPlayer()) return;

        PlayerSkillInfo attackSkillData = PlayerDataHandler.get(event.getEntityPlayer()).getSkillInfo(ReskillableRegistries.SKILLS.getValue(new ResourceLocation(LibMisc.MOD_ID, "attack")));
        if(attackSkillData.isUnlocked(
                ReskillableRegistries.UNLOCKABLES.getValue(new ResourceLocation(LibMisc.MOD_ID, "neutralissse"))) &&
                attackSkillData.getLevel() >= 32){
            event.getEntityPlayer().addPotionEffect(new PotionEffect(MobEffects.HASTE, 120, 1));
        }
    }
}
