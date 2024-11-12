package rlmixins.mixin.vanilla;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipeList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.Arrays;
import java.util.Random;

@Mixin(EntityVillager.ListEnchantedBookForEmeralds.class)
public abstract class LibrarianTradeMixin {

    @Unique
    private Random rlmixins_random;

    @Inject(
            method = "addMerchantRecipe",
            at = @At(value = "HEAD")
    )
    void mixin(IMerchant merchant, MerchantRecipeList recipeList, Random random, CallbackInfo ci){
        this.rlmixins_random = random;
    }

    @ModifyVariable(
            method = "addMerchantRecipe",
            at = @At(value = "STORE"),
            ordinal = 0
    )
    Enchantment mixin(Enchantment enchantment){
        ResourceLocation[] validEnchants = Enchantment.REGISTRY.getKeys()
                .stream().filter(this::rlmixins_isValidEnchantForTrade)
                .toArray(ResourceLocation[]::new);

        if(validEnchants.length>0) {
            ResourceLocation chosenEnchant = validEnchants[rlmixins_random.nextInt(validEnchants.length)];
            enchantment = Enchantment.REGISTRY.getObject(chosenEnchant);
        }
        return enchantment;
    }

    @Unique
    private boolean rlmixins_isValidEnchantForTrade(ResourceLocation chosenEnchant) {
        String enchantName = chosenEnchant.getPath();
        boolean isInList = Arrays.asList(ForgeConfigHandler.server.blacklistedLibrarianEnchants).contains(enchantName);
        return isInList == ForgeConfigHandler.server.blacklistedLibrarianEnchantsIsWhitelist;
    }
}