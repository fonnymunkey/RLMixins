package rlmixins.jei.qualitytools;

import com.tmtravlr.qualitytools.QualityToolsMod;
import com.tmtravlr.qualitytools.reforging.BlockReforgingStation;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class QTRecipeCategory implements IRecipeCategory<IRecipeWrapper> {
    public static final String UID = "qualitytools.reforgingstation";

    private final IDrawable icon;
    private final IDrawable background;

    public QTRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(new ResourceLocation("rlmixins", "textures/gui/qt_reforging_station_jei.png"), 0, 0, 18, 58);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BlockReforgingStation.INSTANCE));
    }

    @Override
    @Nonnull
    public String getUid() {
        return UID;
    }

    @Override
    @Nonnull
    public String getTitle() {
        return BlockReforgingStation.INSTANCE.getLocalizedName();
    }

    @Override
    @Nonnull
    public String getModName() {
        return QualityToolsMod.MOD_NAME;
    }

    @Override
    @Nonnull
    public IDrawable getBackground() {
        return background;
    }

    @Override
    @Nonnull
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(0, false, 0, 0);
        guiItemStacks.init(1, true, 0, 40);
        guiItemStacks.set(ingredients);
    }
}