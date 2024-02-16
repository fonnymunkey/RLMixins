package rlmixins.mixin.charm;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import svenhjol.charm.enchanting.enchantment.EnchantmentRustingCurse;

import java.util.Random;

@Mixin(EnchantmentRustingCurse.class)
public abstract class EnchantmentRustingCurseMixin {

    @Redirect(
            method = "damageItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;attemptDamageItem(ILjava/util/Random;Lnet/minecraft/entity/player/EntityPlayerMP;)Z")
    )
    public boolean rlmixins_charmEnchantmentRustingCurse_damageItem(ItemStack instance, int amount, Random rand, EntityPlayerMP damager) {
        instance.damageItem(amount, damager);
        return false;
    }
}