package rlmixins.handlers.srparasite;

import com.dhanantry.scapeandrunparasites.init.SRPPotions;
import com.dhanantry.scapeandrunparasites.item.tool.WeaponToolArmorBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.handlers.ForgeConfigHandler;

public class ArmorEffectHandler {

    private static boolean handlingCuring = false;

    /*
     *  Instead of checking every hurt event and player tick
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPotionApply(PotionEvent.PotionApplicableEvent event){
        EntityLivingBase entity = event.getEntityLiving();
        if(entity == null) return;
        if(entity.getEntityWorld().isRemote) return;

        if(handlingCuring) return;
        if(ForgeConfigHandler.server.parasiteArmorViralCuring || ForgeConfigHandler.server.parasiteArmorFearCuring) {
            for(ItemStack stack : entity.getArmorInventoryList()) {
                if(!(stack.getItem() instanceof WeaponToolArmorBase)) return;
            }
            handlingCuring = true;
            if(ForgeConfigHandler.server.parasiteArmorViralCuring && (event.getPotionEffect().getPotion() == SRPPotions.VIRA_E)) removeAndLimit(entity, event.getPotionEffect(), ForgeConfigHandler.server.parasiteArmorViralMax, event);
            if(ForgeConfigHandler.server.parasiteArmorFearCuring && (event.getPotionEffect().getPotion() == SRPPotions.FEAR_E)) removeAndLimit(entity, event.getPotionEffect(), ForgeConfigHandler.server.parasiteArmorFearMax, event);
            handlingCuring = false;
        }
    }

    private static void removeAndLimit(EntityLivingBase player, PotionEffect previous, int maxAmp, PotionEvent.PotionApplicableEvent event) {
        if(previous.getAmplifier() > maxAmp){
            event.setResult(Event.Result.DENY);
            player.addPotionEffect(new PotionEffect(previous.getPotion(), previous.getDuration(), maxAmp, previous.getIsAmbient(), previous.doesShowParticles()));
        }
    }
}