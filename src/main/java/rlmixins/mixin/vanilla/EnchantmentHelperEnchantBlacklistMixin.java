package rlmixins.mixin.vanilla;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.RLMixins;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.Arrays;
import java.util.List;

@Mixin(value = EnchantmentHelper.class, priority = 2000)
public abstract class EnchantmentHelperEnchantBlacklistMixin {

    @Redirect(
            method = "buildEnchantmentList",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getEnchantmentDatas(ILnet/minecraft/item/ItemStack;Z)Ljava/util/List;")
    )
    private static List<EnchantmentData> rlmixins_vanillaEnchantmentHelper_buildEnchantmentList(int level, ItemStack itemStackIn, boolean allowTreasure) {
        RLMixins.LOGGER.info("testmsg1");
        List<EnchantmentData> list = EnchantmentHelper.getEnchantmentDatas(level, itemStackIn, allowTreasure);
        RLMixins.LOGGER.info(list.size());
        list.removeIf(enchantmentData -> !rlmixins$isValidEnchantForRandomEnchanting(enchantmentData.enchantment.getRegistryName()));
        RLMixins.LOGGER.info(list.size());
        return list;
    }

    @Unique
    private static boolean rlmixins$isValidEnchantForRandomEnchanting(ResourceLocation chosenEnchant) {
        String enchantName = chosenEnchant.toString();
        boolean isInList = Arrays.asList(ForgeConfigHandler.server.blacklistedRandomEnchants).contains(enchantName);
        return isInList == ForgeConfigHandler.server.blacklistedRandomEnchantsIsWhitelist;
    }
}