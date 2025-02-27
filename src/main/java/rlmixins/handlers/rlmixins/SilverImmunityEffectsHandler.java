package rlmixins.handlers.rlmixins;

import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.handlers.ForgeConfigHandler;
import rlmixins.potion.PotionSilverImmunity;

import com.charles445.rltweaker.hook.HookPotionCore;

public class SilverImmunityEffectsHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void cureOnApply(PotionEvent.PotionApplicableEvent event){
        if(event.getEntityLiving().getActivePotionEffect(PotionSilverImmunity.INSTANCE) == null) return;

        PotionEffect effect = event.getPotionEffect();
        if(SilverImmunityEffectsHandler.isCurable(effect)) event.setResult(Event.Result.DENY);
    }

    public static boolean isCurable(PotionEffect effect){
        if(ForgeConfigHandler.server.silverImmunityRLTweakerCheck && !HookPotionCore.isCurable(effect)) return false;

        ResourceLocation registryName = effect.getPotion().getRegistryName();
        if(ForgeConfigHandler.getSilverImmunityBlacklistedPotionEffects().contains(registryName)) return false;

        return (effect.getPotion().isBadEffect());
    }
}
