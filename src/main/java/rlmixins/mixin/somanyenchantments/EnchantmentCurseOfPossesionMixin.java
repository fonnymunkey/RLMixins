package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_4_0.EnchantmentCurseofPossession;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentCurseofPossession.class)
public abstract class EnchantmentCurseOfPossesionMixin {
/*
    @ModifyArg(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lcom/Shultrea/Rin/Enchantment_Base_Sector/EnchantmentBase;<init>(Lnet/minecraft/enchantment/Enchantment$Rarity;Lnet/minecraft/enchantment/EnumEnchantmentType;[Lnet/minecraft/inventory/EntityEquipmentSlot;)V")
    )
    private static Enchantment.Rarity rlmixins_soManyEnchantmentsEnchantmentCurseOfPossesion_initRarity(Enchantment.Rarity rarity) {
        return Enchantment.Rarity.RARE;
    }
 */

    /**
     * Cancel this handler from running because it:
     * (Probably) Laggy with a large amount of loaded entities;
     * Doesn't always work, seems random;
     * Causes the bug that deletes items with CoP on them on death.
     *
     * Replaced with handler in EventHandler
     */
    @Inject(
            method = "onExist",
            at = @At(value = "HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_soManyEnchantmentsEnchantmentCurseOfPossesion_onExist(TickEvent.WorldTickEvent event, CallbackInfo ci) {
        ci.cancel();
    }
}