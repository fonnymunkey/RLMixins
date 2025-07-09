package rlmixins.handlers.potioncore;

import com.tmtravlr.potioncore.potion.PotionCure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Fix by Nischhelm
 */
public class CureHandler {
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingHurt(LivingHurtEvent event) {
        if(event.getEntityLiving().world.isRemote) return;
        EntityLivingBase victim = event.getEntityLiving();
        
        if(victim.getActivePotionEffect(PotionCure.INSTANCE) == null) return;
        PotionCure.INSTANCE.performEffect(victim,0);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingDamage(LivingDamageEvent event) {
        if(event.getEntityLiving().world.isRemote) return;
        EntityLivingBase victim = event.getEntityLiving();
        
        if(victim.getActivePotionEffect(PotionCure.INSTANCE) == null) return;
        PotionCure.INSTANCE.performEffect(victim,0);
    }
}