package rlmixins.jei;

import net.minecraft.init.Items;

import java.util.ArrayList;
import java.util.List;

public final class AntimagicRecipeMaker {

    public static List<AntimagicRecipeWrapper> getAntimagicRecipe() {
        List<AntimagicRecipeWrapper> recipes = new ArrayList<>();
        recipes.add(new AntimagicRecipeWrapper(Items.DIAMOND_SWORD));
        recipes.add(new AntimagicRecipeWrapper(Items.DIAMOND_AXE));
        recipes.add(new AntimagicRecipeWrapper(Items.DIAMOND_PICKAXE));
        recipes.add(new AntimagicRecipeWrapper(Items.DIAMOND_SHOVEL));
        recipes.add(new AntimagicRecipeWrapper(Items.DIAMOND_HELMET));
        recipes.add(new AntimagicRecipeWrapper(Items.DIAMOND_CHESTPLATE));
        recipes.add(new AntimagicRecipeWrapper(Items.DIAMOND_LEGGINGS));
        recipes.add(new AntimagicRecipeWrapper(Items.DIAMOND_BOOTS));
        return recipes;
    }
}