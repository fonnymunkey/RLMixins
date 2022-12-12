package rlmixins.mixin.rustic;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import rustic.common.potions.EventHandlerPotions;
import rustic.common.potions.PotionsRustic;

@Mixin(EventHandlerPotions.class)
public abstract class EventHandlerPotionsMixin {

    /**
     * @author fonnymunkey
     * @reason rework to work with coffee
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @Overwrite(remap = false)
    public void onWaterBottleUse(LivingEntityUseItemEvent.Finish event) {
        EntityLivingBase entity = event.getEntityLiving();
        PotionEffect effect = entity.getActivePotionEffect(PotionsRustic.TIPSY);
        if(effect != null && !entity.world.isRemote) {
            ItemStack stack = event.getItem();
            if(stack.getItem() == Items.POTIONITEM && PotionUtils.getPotionFromItem(stack).getRegistryName() != null && PotionUtils.getPotionFromItem(stack).getRegistryName().toString().equals("charm:coffee")) {
                int duration = effect.getDuration();
                int amplifier = effect.getAmplifier();

                if(amplifier > 0 && entity.world.rand.nextFloat() < 0.2F) --amplifier;
                else duration -= entity.world.rand.nextInt(800) + 600;

                entity.removePotionEffect(PotionsRustic.TIPSY);
                if(amplifier >= 0 && duration > 0) {
                    entity.addPotionEffect(new PotionEffect(PotionsRustic.TIPSY, duration, amplifier, false, false));
                }
            }
        }
    }
}