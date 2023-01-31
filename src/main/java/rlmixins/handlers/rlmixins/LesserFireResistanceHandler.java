package rlmixins.handlers.rlmixins;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.potion.PotionLesserFireResistance;

public class LesserFireResistanceHandler {

    /**
     * BountifulBaubles reworked Obsidian Skull effect, but as a potion
     */
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onLivingHurt(LivingHurtEvent event) {
        if(event.getEntityLiving() != null &&
                event.getSource().isFireDamage() &&
                !event.getEntityLiving().world.isRemote &&
                !event.getSource().equals(DamageSource.LAVA)) {
            EntityLivingBase entity = event.getEntityLiving();

            PotionEffect effect = entity.getActivePotionEffect(PotionLesserFireResistance.INSTANCE);
            if(effect == null) return;

            event.setAmount(event.getAmount() * Math.max(0F, 1.0F - (0.5F * (float)(effect.getAmplifier() + 1))));

            if(entity.isBurning()) entity.extinguish();
        }
    }
}