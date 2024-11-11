package rlmixins.mixin.vanilla;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.village.MerchantRecipeList;
import org.spongepowered.asm.mixin.Mixin;
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

    @Redirect(
            method = "addMerchantRecipe",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/util/registry/RegistryNamespaced;getRandomObject(Ljava/util/Random;)Ljava/lang/Object;")
    )
    Enchantment rlmixins_limitEnchantTrades(Random random){
        RLMixins.LOGGER.info("nischtest00");
        ResourceLocation[] validEnchants = Enchantment.REGISTRY.getKeys()
                .stream().filter(this::rlmixins_isValidEnchantForTrade)
                .toArray(ResourceLocation[]::new);
        RLMixins.LOGGER.info(validEnchants.length);

        ResourceLocation chosenEnchant = validEnchants[random.nextInt(validEnchants.length)];
        return Enchantment.REGISTRY.getObject(chosenEnchant);
    }

    @Unique
    private boolean rlmixins_isValidEnchantForTrade(ResourceLocation chosenEnchant) {
        String enchantName = chosenEnchant.getPath();
        boolean isInList = Arrays.asList(ForgeConfigHandler.server.blacklistedLibrarianEnchants).contains(enchantName);
        return isInList == ForgeConfigHandler.server.blacklistedLibrarianEnchantsIsWhitelist;
    }
}