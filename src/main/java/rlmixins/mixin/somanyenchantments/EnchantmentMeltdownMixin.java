package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_4_0.EnchantmentMeltdown;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(EnchantmentMeltdown.class)
public abstract class EnchantmentMeltdownMixin {

    @Redirect(
            method = "damageArmor",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;attemptDamageItem(ILjava/util/Random;Lnet/minecraft/entity/player/EntityPlayerMP;)Z")
    )
    public boolean rlmixins_soManyEnchantmentsEnchantmentMeltdown_damageArmor(ItemStack instance, int amount, Random rand, EntityPlayerMP damager) {
        instance.damageItem(amount, damager);
        return false;
    }
}