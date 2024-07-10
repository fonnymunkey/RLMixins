package rlmixins.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import rlmixins.handlers.ForgeConfigHandler;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        if(ForgeConfigHandler.server.registerCleansingTalisman)
            registry.addRecipes(CurseBreakRecipeMaker.getCurseBreakRecipe(), VanillaRecipeCategoryUid.CRAFTING);
        if(ForgeConfigHandler.server.registerAntimagicTalisman)
            registry.addRecipes(AntimagicRecipeMaker.getAntimagicRecipe(), VanillaRecipeCategoryUid.CRAFTING);
    }
}