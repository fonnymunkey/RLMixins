package rlmixins.mixin.vanilla;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.wrapper.SMEWrapper;

@Mixin(value = EnchantmentHelper.class, priority = 2000)
public abstract class EnchantmentHelperOffhandMixin {

    @Redirect(
            method = "applyThornEnchantments",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;getHeldItemMainhand()Lnet/minecraft/item/ItemStack;")
    )
    private static ItemStack rlmixins_vanillaEnchantmentHelper_applyThornEnchantments(EntityLivingBase instance) {
        if(instance instanceof EntityPlayer && isFromOffhand()) return instance.getHeldItemOffhand();
        return instance.getHeldItemMainhand();
    }

    @Redirect(
            method = "applyArthropodEnchantments",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;getHeldItemMainhand()Lnet/minecraft/item/ItemStack;")
    )
    private static ItemStack rlmixins_vanillaEnchantmentHelper_applyArthropodEnchantments(EntityLivingBase instance) {
        if(instance instanceof EntityPlayer && isFromOffhand()) return instance.getHeldItemOffhand();
        return instance.getHeldItemMainhand();
    }


    @Inject(
            method = "getFireAspectModifier",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void rlmixins_vanillaEnchantmentHelper_getFireAspectModifier(EntityLivingBase player, CallbackInfoReturnable<Integer> cir) {
        int level = 0;
        ItemStack stack;

        if(player instanceof EntityPlayer && isFromOffhand()) stack = player.getHeldItemOffhand();
        else stack = player.getHeldItemMainhand();

        level = EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack);
        if(Loader.isModLoaded("somanyenchantments")) {
            level += EnchantmentHelper.getEnchantmentLevel(SMEWrapper.getLesserFireAspect(), stack) / 2;
            level += 2 * EnchantmentHelper.getEnchantmentLevel(SMEWrapper.getAdvancedFireAspect(), stack);
            level += 4 * EnchantmentHelper.getEnchantmentLevel(SMEWrapper.getSupremeFireAspect(), stack);
        }

        cir.setReturnValue(level);
    }

    @Inject(
            method = "getFireAspectModifier",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void rlmixins_vanillaEnchantmentHelper_getKnockbackModifier(EntityLivingBase player, CallbackInfoReturnable<Integer> cir) {
        int level = 0;
        ItemStack stack;

        if(player instanceof EntityPlayer && isFromOffhand()) stack = player.getHeldItemOffhand();
        else stack = player.getHeldItemMainhand();

        level = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack);
        if(Loader.isModLoaded("somanyenchantments")) {
            level += 2 * EnchantmentHelper.getEnchantmentLevel(SMEWrapper.getAdvancedKnockback(), stack);
        }

        cir.setReturnValue(level);
    }

    private static boolean isFromOffhand() {
        for(StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if(element.getClassName().equals("bettercombat.mod.network.PacketOffhandAttack$Handler")) return true;
        }
        return false;
    }
}