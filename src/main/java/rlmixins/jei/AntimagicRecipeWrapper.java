package rlmixins.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import rlmixins.handlers.ModRegistry;

import java.util.Arrays;
import java.util.List;

public class AntimagicRecipeWrapper implements ICraftingRecipeWrapper {

    private final List<ItemStack> inputs;
    private final ItemStack output;

    public AntimagicRecipeWrapper(Item item) {
        ItemStack antimagicTalisman = new ItemStack(ModRegistry.antimagicTalisman);
        ItemStack enchantedItem = new ItemStack(item);
        enchantedItem.addEnchantment(Enchantments.UNBREAKING, 3);
        ItemStack outputItem = new ItemStack(item);;

        this.inputs = Arrays.asList(antimagicTalisman, enchantedItem);
        this.output = outputItem;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, this.inputs);
        ingredients.setOutput(VanillaTypes.ITEM, this.output);
    }
}