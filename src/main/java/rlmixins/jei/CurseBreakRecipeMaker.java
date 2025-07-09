package rlmixins.jei;

import java.util.ArrayList;
import java.util.List;

public abstract class CurseBreakRecipeMaker {

    public static List<CurseBreakRecipeWrapper> getCurseBreakRecipe() {
        List<CurseBreakRecipeWrapper> recipes = new ArrayList<>();
        recipes.add(new CurseBreakRecipeWrapper());
        return recipes;
    }
}