package rlmixins.jei.qualitytools;

import com.tmtravlr.qualitytools.QualityToolsMod;
import com.tmtravlr.qualitytools.reforging.BlockReforgingStation;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import mezz.jei.startup.ForgeModIdHelper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import noppes.vc.VCBlocks;

import javax.annotation.Nonnull;
import java.util.List;

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