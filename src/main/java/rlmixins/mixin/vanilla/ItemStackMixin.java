package rlmixins.mixin.vanilla;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import rlmixins.wrapper.SMEWrapper;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @ModifyVariable(
            method = "damageItem",
            at = @At("HEAD"),
            argsOnly = true
    )
    public int rlmixins_vanillaItemStack_damageItem(int amount) {
        int level = EnchantmentHelper.getEnchantmentLevel(SMEWrapper.getRusted(), (ItemStack)(Object)this);
        if(level > 0 && amount > 0) return amount * (level+1);
        return amount;
    }
}