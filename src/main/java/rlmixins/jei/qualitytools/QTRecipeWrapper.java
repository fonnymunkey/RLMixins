package rlmixins.jei.qualitytools;

import com.tmtravlr.qualitytools.config.CustomMaterial;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.*;

public class QTRecipeWrapper implements IRecipeWrapper {
    public final ItemStack gear;
    public final List<ItemStack> materials;
    private static final Map<CustomMaterial, List<ItemStack>> customMaterialToStacks = new HashMap<>();

    public QTRecipeWrapper(ItemStack gear, List<ItemStack> materials){
        this.gear = gear;
        this.materials = materials;
    }

    public void addMaterial(CustomMaterial newMaterial) {
        this.materials.addAll(getStacksForCustomMaterial(newMaterial));
    }

    public static List<ItemStack> getStacksForCustomMaterial(CustomMaterial mat){
        if(customMaterialToStacks.containsKey(mat))
            return customMaterialToStacks.get(mat);
        else {
            List<ItemStack> toReturn;

            if (mat.item != null) {
                ItemStack newStack = new ItemStack(mat.item, 1, mat.meta == 32767 ? 0 : mat.meta);
                if (mat.tag != null)
                    newStack.setTagCompound(mat.tag);
                toReturn = Collections.singletonList(newStack);
            } else if (mat.oreDict != null)
                toReturn = OreDictionary.getOres(mat.oreDict);
            else
                toReturn = Collections.emptyList();

            customMaterialToStacks.put(mat, toReturn);
            return toReturn;
        }
    }

    public static void clearCache(){
        customMaterialToStacks.clear();
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(materials));
        ingredients.setOutput(VanillaTypes.ITEM, gear);
    }
}
