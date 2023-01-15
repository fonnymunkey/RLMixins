package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_1_0.EnchantmentBluntness;
import com.Shultrea.Rin.Enchantment_Base_Sector.EnchantmentBase;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import rlmixins.wrapper.SMEWrapper;

@Mixin(EnchantmentBluntness.class)
public abstract class EnchantmentBluntnessMixin extends EnchantmentBase {

    public EnchantmentBluntnessMixin(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @ModifyArg(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lcom/Shultrea/Rin/Enchantment_Base_Sector/EnchantmentBase;<init>(Lnet/minecraft/enchantment/Enchantment$Rarity;Lnet/minecraft/enchantment/EnumEnchantmentType;[Lnet/minecraft/inventory/EntityEquipmentSlot;)V")
    )
    private static EnumEnchantmentType rlmixins_soManyEnchantmentsEnchantmentBluntness_init(EnumEnchantmentType type) {
        return SMEWrapper.getCombatAxeType();
    }
}