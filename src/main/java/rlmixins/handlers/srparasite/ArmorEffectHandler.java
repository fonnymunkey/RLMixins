package rlmixins.handlers.srparasite;

import com.dhanantry.scapeandrunparasites.init.SRPPotions;
import com.dhanantry.scapeandrunparasites.item.tool.WeaponToolArmorBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import rlmixins.handlers.ForgeConfigHandler;

public class ArmorEffectHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.player == null || event.phase != TickEvent.Phase.END) return;
        cureFearAndViral(event.player);
    }

    /**
     * Viral extra dmg gets applied in LivingHurtEvent @normal
     */
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onEntityHurt(LivingHurtEvent event) {
        if(event.getEntity() == null) return;
        cureFearAndViral((EntityLivingBase)event.getEntity());
    }

    /**
     * Fear extra dmg gets applied in LivingDamageEvent @normal
     */
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onEntityDamaged(LivingDamageEvent event) {
        if(event.getEntity() == null) return;
        cureFearAndViral((EntityLivingBase)event.getEntity());
    }

    private static void cureFearAndViral(EntityLivingBase entity){
        PotionEffect viral = entity.getActivePotionEffect(SRPPotions.VIRA_E);
        PotionEffect fear = entity.getActivePotionEffect(SRPPotions.FEAR_E);
        if(viral != null && ForgeConfigHandler.server.parasiteArmorViralCuring || fear != null && ForgeConfigHandler.server.parasiteArmorFearCuring) {
            for(ItemStack stack : entity.getArmorInventoryList()) {
                if(!(stack.getItem() instanceof WeaponToolArmorBase)) return;
            }

            if(viral != null && ForgeConfigHandler.server.parasiteArmorViralCuring) removeAndLimit(entity, viral, ForgeConfigHandler.server.parasiteArmorViralMax);
            if(fear != null && ForgeConfigHandler.server.parasiteArmorFearCuring) removeAndLimit(entity, fear, ForgeConfigHandler.server.parasiteArmorFearMax);
        }
    }

    private static void removeAndLimit(EntityLivingBase player, PotionEffect previous, int maxAmp) {
        if(maxAmp >= previous.getAmplifier()) return;
        player.removeActivePotionEffect(previous.getPotion());
        if(maxAmp != -1) player.addPotionEffect(new PotionEffect(previous.getPotion(), previous.getDuration(), maxAmp, previous.getIsAmbient(), previous.doesShowParticles()));
    }
}