package rlmixins.mixin.quark;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import vazkii.quark.world.item.ItemPirateHat;

/**
 * By cdstk
 */
@Mixin(ItemPirateHat.class)
public abstract class ItemPirateHat_BaubleMixin implements IBauble {

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.HEAD;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {

    }
}