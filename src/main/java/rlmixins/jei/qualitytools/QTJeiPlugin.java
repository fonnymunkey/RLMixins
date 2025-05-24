package rlmixins.jei.qualitytools;

import com.google.gson.*;
import com.tmtravlr.qualitytools.QualityToolsHelper;
import com.tmtravlr.qualitytools.config.ConfigLoader;
import com.tmtravlr.qualitytools.config.CustomMaterial;
import com.tmtravlr.qualitytools.reforging.BlockReforgingStation;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.*;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@JEIPlugin
public class QTJeiPlugin implements IModPlugin {

    private static CustomMaterial universalMaterial = null;
    private static final Map<Item, List<ItemStack>> customMaterials = new HashMap<>();

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new QTRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {
        //Adding the recipes
        registry.addRecipes(getRecipes(registry.getJeiHelpers()), QTRecipeCategory.UID);

        //See CarpentryBench Recipes when right-clicking the Bench block in JEI
        registry.addRecipeCatalyst(new ItemStack(BlockReforgingStation.INSTANCE), QTRecipeCategory.UID);
    }

    private List<QTRecipeWrapper> getRecipes(IJeiHelpers helpers) {
        //Load configs early
        ConfigLoader.reloadConfigs();
        loadCustomReforgingMaterialsEarly();

        List<QTRecipeWrapper> recipeList = new ArrayList<>();
        for (Item gearItem : ForgeRegistries.ITEMS) {
            ItemStack gearStack = new ItemStack(gearItem); //this approach does not allow metadata on the gear stacks, but that doesn't make sense anyway

            //Is not hidden in JEI
            if(gearStack.isEmpty() || helpers.getIngredientBlacklist().isIngredientBlacklisted(gearStack)) continue;
            //Is able to get quality
            if (!QualityToolsHelper.generateQualityTag(gearStack, true)) continue;

            List<ItemStack> materials = new ArrayList<>();

            //For performance, we assume that all normal gear items don't have their own getIsRepairable overrides but use the default material.getRepairItemStack methods
            ItemStack orePlaceholder = null;
            if(gearItem instanceof ItemSword)
                orePlaceholder = Item.ToolMaterial.valueOf(((ItemSword) gearItem).getToolMaterialName()).getRepairItemStack();
            else if(gearItem instanceof ItemTool)
                orePlaceholder = Item.ToolMaterial.valueOf(((ItemTool) gearItem).getToolMaterialName()).getRepairItemStack();
            else if(gearItem instanceof ItemHoe)
                orePlaceholder = Item.ToolMaterial.valueOf(((ItemHoe) gearItem).getMaterialName()).getRepairItemStack();
            else if(gearItem instanceof ItemArmor)
                orePlaceholder = ((ItemArmor) gearItem).getArmorMaterial().getRepairItemStack();

            if(orePlaceholder != null && !orePlaceholder.isEmpty()) {
                int[] oreIds = OreDictionary.getOreIDs(orePlaceholder);
                if(oreIds.length > 0)
                    for (int oreIndex : oreIds)
                        materials.addAll(OreDictionary.getOres(OreDictionary.getOreName(oreIndex)));
                else
                    materials.add(orePlaceholder);
            }
            else {
                //Go through all registered items and check if they can reforge the gear item, quite a cursed approach
                for (Item materialItem : ForgeRegistries.ITEMS) {
                    ItemStack materialStack = new ItemStack(materialItem);
                    if (ConfigLoader.useRepairItem && gearItem.getIsRepairable(gearStack, materialStack))
                        materials.add(materialStack);
                }
            }

            //Add custom config entries
            if(customMaterials.containsKey(gearItem))
                materials.addAll(customMaterials.get(gearItem));

            if (!materials.isEmpty()) {
                //Remove generated Quality from display stack
                if (gearStack.hasTagCompound() && gearStack.getTagCompound().hasKey("Quality"))
                    gearStack.getTagCompound().removeTag("Quality");
                recipeList.add(new QTRecipeWrapper(gearStack, materials));
            }
        }

        if (universalMaterial != null)
            recipeList.forEach(r -> r.addMaterial(universalMaterial));

        //Clear caches, no need for them after startup
        QTRecipeWrapper.clearCache();
        customMaterials.clear();

        return recipeList;
    }

    private static void loadCustomReforgingMaterialsEarly() {
        if (ConfigLoader.configFolder == null) return;
        File customReforgingFile = new File(ConfigLoader.configFolder, "reforging materials.json");

        try {
            InputStreamReader reader = new InputStreamReader(Files.newInputStream(customReforgingFile.toPath()));
            Gson gson = new GsonBuilder().registerTypeAdapter(CustomMaterial.class, new CustomMaterial.Serializer()).create();

            for (JsonElement jsonElement : new JsonParser().parse(reader).getAsJsonArray()) {
                JsonObject json = JsonUtils.getJsonObject(jsonElement, "");

                CustomMaterial material = gson.fromJson(JsonUtils.getJsonObject(json, "material"), CustomMaterial.class);
                if (material == null) continue;

                //Get ItemStacks matching the material settings, using a cache for faster lookup
                List<ItemStack> matStacks = QTRecipeWrapper.getStacksForCustomMaterial(material);

                //We don't allow further gear customizability since for performance the JEI plugin only looks through all registered items, not through all combinations of item, metadata and NBT
                //We also don't allow oreDict entries for the gear, as that similarly doesnt make a lot of sense
                Item gearItem = null;
                if (json.get("tool").isJsonObject())
                    gearItem = gson.fromJson(JsonUtils.getJsonObject(json, "tool"), CustomMaterial.class).item;
                else {
                    String itemName = JsonUtils.getString(json, "tool");
                    if (itemName.equalsIgnoreCase("any"))
                        universalMaterial = material;
                    else
                        gearItem = Item.REGISTRY.getObject(new ResourceLocation(itemName));
                }

                if (gearItem != null) customMaterials.put(gearItem, matStacks);
            }
        } catch (Exception ignored) {}
    }
}
