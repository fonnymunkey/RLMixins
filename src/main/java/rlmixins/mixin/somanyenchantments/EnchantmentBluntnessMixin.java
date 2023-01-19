package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_1_0.EnchantmentBluntness;
import com.Shultrea.Rin.Enchantment_Base_Sector.EnchantmentBase;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import rlmixins.wrapper.SMEWrapper;

@Mixin(EnchantmentBluntness.class)
public abstract class EnchantmentBluntnessMixin extends EnchantmentBase {

    public EnchantmentBluntnessMixin(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }
/*
    @ModifyArg(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lcom/Shultrea/Rin/Enchantment_Base_Sector/EnchantmentBase;<init>(Lnet/minecraft/enchantment/Enchantment$Rarity;Lnet/minecraft/enchantment/EnumEnchantmentType;[Lnet/minecraft/inventory/EntityEquipmentSlot;)V")
    )
    private static Rarity rlmixins_soManyEnchantmentsEnchantmentBluntness_initRarity(Rarity rarity) {
        return Rarity.RARE;
    }
*/
    @ModifyArg(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lcom/Shultrea/Rin/Enchantment_Base_Sector/EnchantmentBase;<init>(Lnet/minecraft/enchantment/Enchantment$Rarity;Lnet/minecraft/enchantment/EnumEnchantmentType;[Lnet/minecraft/inventory/EntityEquipmentSlot;)V")
    )
    private static EnumEnchantmentType rlmixins_soManyEnchantmentsEnchantmentBluntness_initType(EnumEnchantmentType type) {
        return SMEWrapper.getCombatType();
    }

    /**
     * @author fonnymunkey
     * @reason change what bluntness can be applied to
     */
    @Overwrite
    public boolean canApply(ItemStack fTest) {
        return super.canApply(fTest) && (fTest.getItem() instanceof ItemAxe || fTest.getItem() instanceof ItemSword);
    }
}