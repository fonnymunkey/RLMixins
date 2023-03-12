package rlmixins.recipe;

import com.github.alexthe666.iceandfire.core.ModItems;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.*;

public class RecipeFlameIceWeapon extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    private static final Map<Item, Item> weaponMapFire;
    private static final Map<Item, Item> weaponMapIce;

    // :(
    static {
        Map<Item, Item> tempFireMap = new HashMap<>();
        Map<Item, Item> tempIceMap = new HashMap<>();

        tempFireMap.put(ModItems.dragonbone_sword, ModItems.dragonbone_sword_fire);
        tempIceMap.put(ModItems.dragonbone_sword, ModItems.dragonbone_sword_ice);

        if(Loader.isModLoaded("mujmajnkraftsbettersurvival")) {
            tempFireMap.put(Item.getByNameOrId("mujmajnkraftsbettersurvival:itemdragonbonehammer"), Item.getByNameOrId("mujmajnkraftsbettersurvival:itemfiredragonbonehammer"));
            //tempFireMap.put(Item.getByNameOrId("mujmajnkraftsbettersurvival:itemdragonbonespear"), Item.getByNameOrId("mujmajnkraftsbettersurvival:itemfiredragonbonespear"));
            tempFireMap.put(Item.getByNameOrId("mujmajnkraftsbettersurvival:itemdragonbonedagger"), Item.getByNameOrId("mujmajnkraftsbettersurvival:itemfiredragonbonedagger"));
            tempFireMap.put(Item.getByNameOrId("mujmajnkraftsbettersurvival:itemdragonbonebattleaxe"), Item.getByNameOrId("mujmajnkraftsbettersurvival:itemfiredragonbonebattleaxe"));
            tempFireMap.put(Item.getByNameOrId("mujmajnkraftsbettersurvival:itemdragonbonenunchaku"), Item.getByNameOrId("mujmajnkraftsbettersurvival:itemfiredragonbonenunchaku"));

            tempIceMap.put(Item.getByNameOrId("mujmajnkraftsbettersurvival:itemdragonbonehammer"), Item.getByNameOrId("mujmajnkraftsbettersurvival:itemicedragonbonehammer"));
            //tempIceMap.put(Item.getByNameOrId("mujmajnkraftsbettersurvival:itemdragonbonespear"), Item.getByNameOrId("mujmajnkraftsbettersurvival:itemicedragonbonespear"));
            tempIceMap.put(Item.getByNameOrId("mujmajnkraftsbettersurvival:itemdragonbonedagger"), Item.getByNameOrId("mujmajnkraftsbettersurvival:itemicedragonbonedagger"));
            tempIceMap.put(Item.getByNameOrId("mujmajnkraftsbettersurvival:itemdragonbonebattleaxe"), Item.getByNameOrId("mujmajnkraftsbettersurvival:itemicedragonbonebattleaxe"));
            tempIceMap.put(Item.getByNameOrId("mujmajnkraftsbettersurvival:itemdragonbonenunchaku"), Item.getByNameOrId("mujmajnkraftsbettersurvival:itemicedragonbonenunchaku"));
        }

        if(Loader.isModLoaded("spartanfire")) {
            tempFireMap.put(Item.getByNameOrId("spartanfire:katana_dragonbone"), Item.getByNameOrId("spartanfire:katana_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:greatsword_dragonbone"), Item.getByNameOrId("spartanfire:greatsword_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:longsword_dragonbone"), Item.getByNameOrId("spartanfire:longsword_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:saber_dragonbone"), Item.getByNameOrId("spartanfire:saber_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:rapier_dragonbone"), Item.getByNameOrId("spartanfire:rapier_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:dagger_dragonbone"), Item.getByNameOrId("spartanfire:dagger_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:spear_dragonbone"), Item.getByNameOrId("spartanfire:spear_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:pike_dragonbone"), Item.getByNameOrId("spartanfire:pike_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:lance_dragonbone"), Item.getByNameOrId("spartanfire:lance_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:halberd_dragonbone"), Item.getByNameOrId("spartanfire:halberd_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:warhammer_dragonbone"), Item.getByNameOrId("spartanfire:warhammer_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:hammer_dragonbone"), Item.getByNameOrId("spartanfire:hammer_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:throwing_axe_dragonbone"), Item.getByNameOrId("spartanfire:throwing_axe_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:throwing_knife_dragonbone"), Item.getByNameOrId("spartanfire:throwing_knife_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:longbow_dragonbone"), Item.getByNameOrId("spartanfire:longbow_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:crossbow_dragonbone"), Item.getByNameOrId("spartanfire:crossbow_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:javelin_dragonbone"), Item.getByNameOrId("spartanfire:javelin_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:battleaxe_dragonbone"), Item.getByNameOrId("spartanfire:battleaxe_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:boomerang_dragonbone"), Item.getByNameOrId("spartanfire:boomerang_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:mace_dragonbone"), Item.getByNameOrId("spartanfire:mace_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:staff_dragonbone"), Item.getByNameOrId("spartanfire:staff_fire_dragonbone"));
            tempFireMap.put(Item.getByNameOrId("spartanfire:glaive_dragonbone"), Item.getByNameOrId("spartanfire:glaive_fire_dragonbone"));

            tempIceMap.put(Item.getByNameOrId("spartanfire:katana_dragonbone"), Item.getByNameOrId("spartanfire:katana_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:greatsword_dragonbone"), Item.getByNameOrId("spartanfire:greatsword_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:longsword_dragonbone"), Item.getByNameOrId("spartanfire:longsword_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:saber_dragonbone"), Item.getByNameOrId("spartanfire:saber_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:rapier_dragonbone"), Item.getByNameOrId("spartanfire:rapier_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:dagger_dragonbone"), Item.getByNameOrId("spartanfire:dagger_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:spear_dragonbone"), Item.getByNameOrId("spartanfire:spear_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:pike_dragonbone"), Item.getByNameOrId("spartanfire:pike_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:lance_dragonbone"), Item.getByNameOrId("spartanfire:lance_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:halberd_dragonbone"), Item.getByNameOrId("spartanfire:halberd_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:warhammer_dragonbone"), Item.getByNameOrId("spartanfire:warhammer_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:hammer_dragonbone"), Item.getByNameOrId("spartanfire:hammer_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:throwing_axe_dragonbone"), Item.getByNameOrId("spartanfire:throwing_axe_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:throwing_knife_dragonbone"), Item.getByNameOrId("spartanfire:throwing_knife_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:longbow_dragonbone"), Item.getByNameOrId("spartanfire:longbow_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:crossbow_dragonbone"), Item.getByNameOrId("spartanfire:crossbow_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:javelin_dragonbone"), Item.getByNameOrId("spartanfire:javelin_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:battleaxe_dragonbone"), Item.getByNameOrId("spartanfire:battleaxe_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:boomerang_dragonbone"), Item.getByNameOrId("spartanfire:boomerang_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:mace_dragonbone"), Item.getByNameOrId("spartanfire:mace_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:staff_dragonbone"), Item.getByNameOrId("spartanfire:staff_ice_dragonbone"));
            tempIceMap.put(Item.getByNameOrId("spartanfire:glaive_dragonbone"), Item.getByNameOrId("spartanfire:glaive_ice_dragonbone"));
        }

        weaponMapFire = Collections.unmodifiableMap(tempFireMap);
        weaponMapIce = Collections.unmodifiableMap(tempIceMap);
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) { return validInput(inv) != null; }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        Integer[] slots = validInput(inv);
        if(slots==null) return ItemStack.EMPTY;

        ItemStack stackInput = inv.getStackInSlot(slots[1]);
        ItemStack output;
        if(inv.getStackInSlot(slots[0]).getItem() == ModItems.fire_dragon_blood) output = new ItemStack(weaponMapFire.get(stackInput.getItem()));
        else output = new ItemStack(weaponMapIce.get(stackInput.getItem()));

        NBTTagCompound compound = output.getTagCompound();
        if(compound == null) compound = new NBTTagCompound();
        compound.setTag("ench", stackInput.getEnchantmentTagList());
        compound.setInteger("RepairCost", stackInput.getRepairCost());

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
        int bloodSlot = -1;
        int weaponSlot = -1;
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

            if(itemStack.getItem() == ModItems.fire_dragon_blood || itemStack.getItem() == ModItems.ice_dragon_blood) bloodSlot = i;
            else if(weaponMapFire.containsKey(itemStack.getItem()) && !itemStack.isItemDamaged()) {
                NBTTagCompound compound = itemStack.getTagCompound();
                if(compound != null && compound.hasKey("Original") && compound.getByte("Original") != 1) return null;//Prevent dupe with ammo weapons
                weaponSlot = i;//Shouldn't really need to check both maps, since everything will be in both maps
            }
            else return null;
        }
        Integer[] slots = new Integer[2];
        slots[0] = bloodSlot;
        slots[1] = weaponSlot;
        return (bloodSlot != -1 && weaponSlot != -1) ? slots : null;
    }
}
