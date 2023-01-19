package rlmixins.recipe;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import rlmixins.handlers.ModRegistry;
import rlmixins.wrapper.CharmWrapper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RecipeCurseBreak extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return validInput(inv) != null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        Integer[] slots = validInput(inv);
        if(slots==null) return ItemStack.EMPTY;

        return ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(CharmWrapper.getCurseBreak(), 1));
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Nullable
    private Integer[] validInput(InventoryCrafting inv) {
        int numStacks = 0;
        int bookSlot = -1;
        int talismanSlot = -1;
        List<Integer> occupiedSlots = new ArrayList<>();

        for(int i = 0; i < inv.getSizeInventory(); i++) {
            if (!inv.getStackInSlot(i).isEmpty()) {
                numStacks++;
                occupiedSlots.add(i);
            }
        }
        if(numStacks != 2) return null;

        for(int i : occupiedSlots) {
            ItemStack itemStack = inv.getStackInSlot(i);

            if(itemStack.getItem() instanceof ItemEnchantedBook) bookSlot = i;
            else if(itemStack.getItem().equals(ModRegistry.cleansingTalisman)) talismanSlot = i;
            else return null;
        }
        Integer[] slots = new Integer[2];
        slots[0] = bookSlot;
        slots[1] = talismanSlot;
        return (bookSlot != -1 && talismanSlot != -1) ? slots : null;
    }
}