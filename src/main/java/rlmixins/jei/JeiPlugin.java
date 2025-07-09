package rlmixins.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import rlmixins.handlers.ConfigHandler;
import rlmixins.util.ModLoadedUtil;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        if(ConfigHandler.CHARM_CONFIG.cleansingTalisman && ModLoadedUtil.isCharmLoaded()) {
            registry.addRecipes(CurseBreakRecipeMaker.getCurseBreakRecipe(), VanillaRecipeCategoryUid.CRAFTING);
        }
        if(ConfigHandler.RLMIXINS_CONFIG.antimagicTalisman) {
            registry.addRecipes(AntimagicRecipeMaker.getAntimagicRecipe(), VanillaRecipeCategoryUid.CRAFTING);
        }
    }
}