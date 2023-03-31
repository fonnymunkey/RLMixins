package rlmixins.mixin.foodexpansion;

import lellson.foodexpansion.items.ItemFoodBasic;
import lellson.foodexpansion.items.ItemNetherWartSoup;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;
import java.util.Collection;

@Mixin(ItemNetherWartSoup.class)
public abstract class ItemNetherWartSoupMixin extends ItemFoodBasic {

    public ItemNetherWartSoupMixin(String name, int foodAmount, float saturation) {
        super(name, foodAmount, saturation);
    }

    /**
     * @author fonnymunkey
     * @reason Fix FoodExpansion calling client-sided isBeneficial from server-side
     */
    @Overwrite
    public ItemStack onItemUseFinish(ItemStack item, World world, EntityLivingBase player) {
        if(!world.isRemote) {
            Collection<PotionEffect> activeeffects = player.getActivePotionEffects();
            ArrayList<PotionEffect> effectstoremove = new ArrayList<PotionEffect>();
            for(PotionEffect effect : activeeffects) {
                if(effect.getPotion().isBadEffect()) effectstoremove.add(effect);
            }
            for(PotionEffect potionEffect : effectstoremove) {
                Potion potion = potionEffect.getPotion();
                player.removePotionEffect(potion);
            }
        }
        player.setFire(5);
        return super.onItemUseFinish(item, world, player);
    }
}