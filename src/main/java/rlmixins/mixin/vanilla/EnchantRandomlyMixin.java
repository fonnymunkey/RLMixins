package rlmixins.mixin.vanilla;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.EnchantRandomly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.RLMixins;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.Arrays;
import java.util.List;

@Mixin(value = EnchantRandomly.class, priority = 2000)
public abstract class EnchantRandomlyMixin {

    @Final @Shadow
    private List<Enchantment> enchantments;

    @Inject(
            method = "<init>",
            at = @At(value = "TAIL")
    )
    private void rlmixins_vanillaEnchantRandomly_init(LootCondition[] conditionsIn, List<Enchantment> enchantmentsIn, CallbackInfo ci) {
        RLMixins.LOGGER.info("testmsg2");
        RLMixins.LOGGER.info(this.enchantments.size());
        this.enchantments.removeIf(e -> !rlmixins$isValidEnchantForRandomEnchanting(e));
        RLMixins.LOGGER.info(this.enchantments.size());
    }

    @Redirect(
            method = "apply",
            at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z")
    )
    boolean rlmixins_vanillaEnchantRandomly_apply(List<Enchantment> instance)
    {
        RLMixins.LOGGER.info("testmsg3");
        RLMixins.LOGGER.info(instance.size());
        instance.removeIf(e -> !rlmixins$isValidEnchantForRandomEnchanting(e));
        RLMixins.LOGGER.info(instance.size());
        return instance.isEmpty();
    }

    @Unique
    private boolean rlmixins$isValidEnchantForRandomEnchanting(Enchantment chosenEnchant) {
        String enchantName = chosenEnchant.getRegistryName().toString();
        boolean isInList = Arrays.asList(ForgeConfigHandler.server.blacklistedRandomEnchants).contains(enchantName);
        return isInList == ForgeConfigHandler.server.blacklistedRandomEnchantsIsWhitelist;
    }
}