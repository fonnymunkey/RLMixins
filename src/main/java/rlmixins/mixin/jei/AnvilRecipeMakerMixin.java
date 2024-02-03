package rlmixins.mixin.jei;

import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IVanillaRecipeFactory;
import mezz.jei.plugins.vanilla.anvil.AnvilRecipeMaker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(AnvilRecipeMaker.class)
public abstract class AnvilRecipeMakerMixin {

    @Inject(
            method = "getBookEnchantmentRecipes(Ljava/util/List;Lmezz/jei/api/recipe/IVanillaRecipeFactory;Lmezz/jei/api/ingredients/IIngredientRegistry;)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_jeiAnvilRecipeMaker_getBookEnchantmentRecipes0(List<IRecipeWrapper> recipes, IVanillaRecipeFactory vanillaRecipeFactory, IIngredientRegistry ingredientRegistry, CallbackInfo ci) {
        ci.cancel();
    }
}