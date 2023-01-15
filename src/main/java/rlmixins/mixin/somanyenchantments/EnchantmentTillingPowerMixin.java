package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_4_0.EnchantmentTillingPower;
import com.Shultrea.Rin.Enchantment_Base_Sector.EnchantmentBase;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentTillingPower.class)
public abstract class EnchantmentTillingPowerMixin extends EnchantmentBase {

    public EnchantmentTillingPowerMixin(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }

    /**
     * Fix Plowing enchantment incorrectly calculating minimum enchantability causing book wyrm exploit
     */
    @Inject(
            method = "getMinEnchantability",
            at = @At("HEAD"),
            cancellable = true
    )
    public void rlmixins_soManyEnchantmentsEnchantmentTillingPower_getMinEnchantability(int par1, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(25 + (par1 - 1) * 15);
    }

    @Override
    public boolean canApply(ItemStack fTest) {
        return fTest.getItem() instanceof ItemHoe && super.canApply(fTest);
    }
}