package rlmixins.handlers.reskillable;

import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.lib.LibMisc;
import com.charles445.rltweaker.hook.HookPotionCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Collection;

public class EffectTwistHandler {

    @SubscribeEvent
    public static void onEffectTwistHurt(LivingHurtEvent event) {
        if (!event.isCanceled()) {
            if(!(event.getEntity() instanceof EntityPlayer)) return;
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();

            if (PlayerDataHandler.get(player).getSkillInfo(
                    ReskillableRegistries.SKILLS.getValue(new ResourceLocation(LibMisc.MOD_ID, "defense"))).isUnlocked(
                    ReskillableRegistries.UNLOCKABLES.getValue(new ResourceLocation(LibMisc.MOD_ID, "effect_twist")))) {
                Entity src = event.getSource().getTrueSource();
                if (src instanceof EntityLivingBase && src instanceof IMob){
                    Collection<PotionEffect> effects = player.getActivePotionEffects();
                    ArrayList<PotionEffect> effectsToRemove = new ArrayList<>();
                    for (PotionEffect effect : effects) {
                        if(effect.getPotion().isBadEffect() && HookPotionCore.isCurable(effect) && player.getRNG().nextFloat() < 0.01F * (float) (effect.getAmplifier() * effect.getAmplifier())) {
                            effectsToRemove.add(effect);
                        }
                    }
                    for(PotionEffect effect : effectsToRemove) {
                        player.removePotionEffect(effect.getPotion());
                        ((EntityLivingBase) src).addPotionEffect(effect);
                    }
                }
            }
        }
    }
}
