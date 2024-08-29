package rlmixins.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import rlmixins.util.ModLoadedUtil;
import rlmixins.wrapper.RLCombatWrapper;
import rlmixins.wrapper.SMEWrapper;

@Mixin(value = EnchantmentHelper.class, priority = 2000)
public abstract class EnchantmentHelperOffhandMixin {
    
    @ModifyReturnValue(
            method = "getKnockbackModifier",
            at = @At("RETURN")
    )
    private static int rlmixins_vanillaEnchantmentHelper_getKnockbackModifier(int original, EntityLivingBase player) {
        ItemStack stack;
        if(ModLoadedUtil.isRLCombatLoaded() && RLCombatWrapper.isKnockbackOffhand()) stack = player.getHeldItemOffhand();
        else stack = player.getHeldItemMainhand();
        
        if(ModLoadedUtil.isSoManyEnchantmentsLoaded()) {
            original += 2 * EnchantmentHelper.getEnchantmentLevel(SMEWrapper.getAdvancedKnockback(), stack);
        }
        
        return original;
    }
    
    @ModifyReturnValue(
            method = "getFireAspectModifier",
            at = @At("RETURN")
    )
    private static int rlmixins_vanillaEnchantmentHelper_getFireAspectModifier(int original, EntityLivingBase player) {
        ItemStack stack;
        if(ModLoadedUtil.isRLCombatLoaded() && RLCombatWrapper.isFireAspectOffhand()) stack = player.getHeldItemOffhand();
        else stack = player.getHeldItemMainhand();
        
        if(ModLoadedUtil.isSoManyEnchantmentsLoaded()) {
            original += EnchantmentHelper.getEnchantmentLevel(SMEWrapper.getLesserFireAspect(), stack) / 2;
            original += 2 * EnchantmentHelper.getEnchantmentLevel(SMEWrapper.getAdvancedFireAspect(), stack);
            original += 4 * EnchantmentHelper.getEnchantmentLevel(SMEWrapper.getSupremeFireAspect(), stack);
        }
        
        return original;
    }
}