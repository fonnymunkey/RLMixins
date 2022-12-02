package rlmixins.mixin.reskillable;

import codersafterdark.reskillable.base.LevelLockHandler;
import codersafterdark.reskillable.skill.farming.TraitHungryFarmer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.Arrays;

@Mixin(TraitHungryFarmer.class)
public abstract class TraitHungryFarmerMixin {

    @Redirect(
            method = "onPlayerTick",
            at = @At(value = "INVOKE", target = "Lcodersafterdark/reskillable/base/LevelLockHandler;canPlayerUseItem(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)Z"),
            remap = false
    )
    public boolean rlmixins_reskillableTraitHungryFarmer_onPlayerTick(EntityPlayer player, ItemStack stack) {
        return LevelLockHandler.canPlayerUseItem(player, stack) && !Arrays.asList(ForgeConfigHandler.server.hungryFarmerBlacklist).contains(stack.getItem().getRegistryName().toString());
    }
}