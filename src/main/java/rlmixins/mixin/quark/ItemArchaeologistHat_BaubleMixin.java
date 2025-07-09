package rlmixins.mixin.quark;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import vazkii.quark.world.item.ItemArchaeologistHat;

/**
 * By cdstk
 */
@Mixin(ItemArchaeologistHat.class)
public abstract class ItemArchaeologistHat_BaubleMixin implements IBauble {

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.HEAD;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {

    }
}