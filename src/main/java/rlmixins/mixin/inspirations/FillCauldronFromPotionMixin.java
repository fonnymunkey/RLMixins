package rlmixins.mixin.inspirations;

import knightminer.inspirations.library.recipe.cauldron.ICauldronRecipe;
import knightminer.inspirations.recipes.recipe.FillCauldronFromPotion;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FillCauldronFromPotion.class)
public abstract class FillCauldronFromPotionMixin {

    /**
     * Make incorrect cauldron mixing return Mundane potion instead of Thick potion
     */
    @Redirect(
            method = "getState",
            at = @At(value = "INVOKE", target = "Lknightminer/inspirations/library/recipe/cauldron/ICauldronRecipe$CauldronState;potion(Lnet/minecraft/potion/PotionType;)Lknightminer/inspirations/library/recipe/cauldron/ICauldronRecipe$CauldronState;", ordinal = 1),
            remap = false
    )
    public ICauldronRecipe.CauldronState rlmixins_inspirationsFillCauldronFromPotion_getState(PotionType potion) {
        return ICauldronRecipe.CauldronState.potion(PotionTypes.MUNDANE);
    }
}