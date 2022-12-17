package rlmixins.mixin.vanilla;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

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

    private static boolean isFromOffhand() {
        for(StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if(element.getClassName().equals("bettercombat.mod.network.PacketOffhandAttack$Handler")) return true;
        }
        return false;
    }
}