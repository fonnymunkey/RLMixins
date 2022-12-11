package rlmixins.mixin.reskillable;

import codersafterdark.reskillable.skill.magic.TraitGoldenOsmosis;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.wrapper.DefiledWrapper;

@Mixin(TraitGoldenOsmosis.class)
public abstract class TraitGoldenOsmosisMixin {

    /**
     * Allow Golden Book wyrm armor to be repaired with golden osmosis
     */
    @Redirect(
            method = "tryRepair",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getIsRepairable(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z")
    )
    public boolean rlmixins_reskillableTraitGoldenOsmosisMixin_tryRepair(Item instance, ItemStack toRepair, ItemStack repair) {
        return instance.getIsRepairable(toRepair, repair) || instance.getIsRepairable(toRepair, new ItemStack(DefiledWrapper.getGoldenWyrmScale()));
    }
}