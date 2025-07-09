package rlmixins.mixin.quark;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import vazkii.quark.vanity.item.ItemWitchHat;

/**
 * By cdstk
 */
@Mixin(ItemWitchHat.class)
public abstract class ItemWitchHat_BaubleMixin implements IBauble {

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.HEAD;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {

    }
}