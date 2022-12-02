package rlmixins.mixin.foodexpansion;

import lellson.foodexpansion.items.ItemFoodBasic;
import lellson.foodexpansion.items.ItemNetherWartSoup;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

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
        player.setFire(5);
        player.getActivePotionEffects().removeIf(potionEffect -> potionEffect.getPotion().isBadEffect());
        return super.onItemUseFinish(item, world, player);
    }
}