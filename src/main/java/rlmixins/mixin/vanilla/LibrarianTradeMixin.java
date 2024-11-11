package rlmixins.mixin.vanilla;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.RLMixins;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.Arrays;
import java.util.Random;

@Mixin(EntityVillager.ListEnchantedBookForEmeralds.class)
public abstract class LibrarianTradeMixin {
    @Inject(
            method = "addMerchantRecipe",
            at = @At(value = "HEAD")
    )
    void mixin(IMerchant merchant, MerchantRecipeList recipeList, Random random, CallbackInfo ci){
        RLMixins.LOGGER.info("testmsg by nischi");
    }


    /**
     * @author Nischhelm
     * @reason redirecting getRandomObject doesnt work
     */
    @Overwrite
    public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random){
        ResourceLocation[] validEnchants = Enchantment.REGISTRY.getKeys()
                .stream().filter(this::rlmixins_isValidEnchantForTrade)
                .toArray(ResourceLocation[]::new);

        Enchantment enchantment;
        if(validEnchants.length>0) {
            ResourceLocation chosenEnchant = validEnchants[random.nextInt(validEnchants.length)];
            enchantment = Enchantment.REGISTRY.getObject(chosenEnchant);
        } else
            enchantment = Enchantment.REGISTRY.getRandomObject(random);

        int i = MathHelper.getInt(random, enchantment.getMinLevel(), enchantment.getMaxLevel());
        ItemStack itemstack = ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(enchantment, i));
        int j = 2 + random.nextInt(5 + i * 10) + 3 * i;

        if (enchantment.isTreasureEnchantment()) j *= 2;
        if (j > 64) j = 64;

        recipeList.add(new MerchantRecipe(new ItemStack(Items.BOOK), new ItemStack(Items.EMERALD, j), itemstack));
    }

    @Unique
    private boolean rlmixins_isValidEnchantForTrade(ResourceLocation chosenEnchant) {
        String enchantName = chosenEnchant.getPath();
        boolean isInList = Arrays.asList(ForgeConfigHandler.server.blacklistedLibrarianEnchants).contains(enchantName);
        return isInList == ForgeConfigHandler.server.blacklistedLibrarianEnchantsIsWhitelist;
    }
}