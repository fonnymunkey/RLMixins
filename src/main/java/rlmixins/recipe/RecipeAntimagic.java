package rlmixins.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import rlmixins.handlers.ModRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RecipeAntimagic extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return validInput(inv) != null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        Integer[] slots = validInput(inv);
        if(slots==null) return ItemStack.EMPTY;

        ItemStack output = inv.getStackInSlot(slots[0]).copy();
        NBTTagCompound compound = output.getTagCompound();
        if(compound == null) compound = new NBTTagCompound();
        compound.removeTag("ench");
        compound.removeTag("RepairCost");
        output.setTagCompound(compound);

        return output;
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
        int itemSlot = -1;
        int talismanSlot = -1;
        List<Integer> occupiedSlots = new ArrayList<>();

        for(int i = 0; i < inv.getSizeInventory(); i++) {
            if(!inv.getStackInSlot(i).isEmpty()) {
                numStacks++;
                occupiedSlots.add(i);
            }
        }
        if(numStacks != 2) return null;

        for(int i : occupiedSlots) {
            ItemStack itemStack = inv.getStackInSlot(i);

            if(!(itemStack.getItem() instanceof ItemEnchantedBook) && itemStack.isItemEnchanted()) itemSlot = i;
            else if(itemStack.getItem().equals(ModRegistry.antimagicTalisman)) talismanSlot = i;
            else return null;
        }
        Integer[] slots = new Integer[2];
        slots[0] = itemSlot;
        slots[1] = talismanSlot;
        return (itemSlot != -1 && talismanSlot != -1) ? slots : null;
    }
}