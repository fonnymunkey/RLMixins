package rlmixins.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import rlmixins.handlers.ModRegistry;
import rlmixins.wrapper.CharmWrapper;

import java.util.Arrays;
import java.util.List;

public class CurseBreakRecipeWrapper implements ICraftingRecipeWrapper {

    private final List<ItemStack> inputs;
    private final ItemStack output;

    public CurseBreakRecipeWrapper() {
        ItemStack cleansingTalisman = new ItemStack(ModRegistry.cleansingTalisman);
        ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemStack curseBreakBook = ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(CharmWrapper.getCurseBreak(), 1));

        this.inputs = Arrays.asList(cleansingTalisman, enchantedBook);
        this.output = curseBreakBook;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, this.inputs);
        ingredients.setOutput(VanillaTypes.ITEM, this.output);
    }
}