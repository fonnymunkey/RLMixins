package rlmixins.mixin.rustic;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import rustic.common.potions.EventHandlerPotions;

@Mixin(EventHandlerPotions.class)
public abstract class EventHandlerPotionsMixin {

    /**
     * Make coffee lower the tipsy effect, not water
     */
    @Redirect(
            method = "onWaterBottleUse",
            at = @At(value = "INVOKE",  target = "Lnet/minecraft/potion/PotionUtils;getPotionFromItem(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/potion/PotionType;"),
            remap = false
    )
    public PotionType rlmixins_rusticEventHandlerPotions_onWaterBottleUse_redirect(ItemStack itemIn) {
        return PotionType.getPotionTypeForName("charm:coffee");
    }

    /**
     * Increase chance of lowering tipsy amplifier since its now coffee
     */
    @ModifyConstant(
            method = "onWaterBottleUse",
            constant = @Constant(floatValue = 0.1F),
            remap = false
    )
    public float rlmixins_rusticEventHandlerPotions_onWaterBottleUse_constantFloat(float constant) {
        return 0.2F;
    }

    /**
     * Increase tipsy duration lowered by drinking coffee
     */
    @ModifyConstant(
            method = "onWaterBottleUse",
            constant = @Constant(intValue = 200),
            remap = false
    )
    public int rlmixins_rusticEventHandlerPotions_onWaterBottleUse_constantInt(int constant) {
        return 600;
    }
}
