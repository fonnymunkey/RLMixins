package rlmixins.handlers.srparasites;

import com.dhanantry.scapeandrunparasites.init.SRPPotions;
import com.dhanantry.scapeandrunparasites.item.tool.WeaponToolArmorBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.handlers.ConfigHandler;

public class ArmorCureHandler {

    private static boolean handlingCuring = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPotionApply(PotionEvent.PotionApplicableEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if(entity == null) return;
        if(entity.getEntityWorld().isRemote) return;

        if(handlingCuring) return;
        if(ConfigHandler.SRPARASITES_CONFIG.armorViralCure || ConfigHandler.SRPARASITES_CONFIG.armorFearCure) {
            for(ItemStack stack : entity.getArmorInventoryList()) {
                if(!(stack.getItem() instanceof WeaponToolArmorBase)) return;
            }
            handlingCuring = true;
            if(ConfigHandler.SRPARASITES_CONFIG.armorViralCure && (event.getPotionEffect().getPotion() == SRPPotions.VIRA_E)) removeAndLimit(entity, event.getPotionEffect(), ConfigHandler.SRPARASITES_CONFIG.armorViralCureMax, event);
            if(ConfigHandler.SRPARASITES_CONFIG.armorFearCure && (event.getPotionEffect().getPotion() == SRPPotions.FEAR_E)) removeAndLimit(entity, event.getPotionEffect(), ConfigHandler.SRPARASITES_CONFIG.armorFearCureMax, event);
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