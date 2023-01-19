package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_1_0.EnchantmentHeavyWeight;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentHeavyWeight.class)
public abstract class EnchantmentHeavyWeightMixin {

    @ModifyArg(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lcom/Shultrea/Rin/Enchantment_Base_Sector/EnchantmentBase;<init>(Lnet/minecraft/enchantment/Enchantment$Rarity;Lnet/minecraft/enchantment/EnumEnchantmentType;[Lnet/minecraft/inventory/EntityEquipmentSlot;)V")
    )
    private static Enchantment.Rarity rlmixins_soManyEnchantmentsEnchantmentHeavyWeight_initRarity(Enchantment.Rarity rarity) {
        return Enchantment.Rarity.RARE;
    }
    @Inject(
            method = "HandleEnchant",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_soManyEnchantmentsEnchantmentHeavyWeight_handleEnchant(LivingEvent.LivingUpdateEvent fEvent, CallbackInfo ci) {
        ci.cancel();
    }
}