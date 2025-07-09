package rlmixins.mixin.bettersurvival;

import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentMultishot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.wrapper.InFRLCraftWrapper;

@Mixin(EnchantmentMultishot.class)
public abstract class EnchantmentMultishot_DragonBoneMixin {

    @Unique
    private static final ResourceLocation rlmixins$DRAGONBOW_REGISTRY = new ResourceLocation("iceandfire", "dragonbone_bow");

    @Unique
    private static ItemStack rlmixins$currentBow = ItemStack.EMPTY;

    @Inject(
            method = "shootMoreArrows",
            at = @At(value = "INVOKE", target = "Lcom/mujmajnkraft/bettersurvival/enchantments/EnchantmentMultishot;findAmmo(Lnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;", shift = At.Shift.BEFORE),
            remap = false
    )
    private static void rlmixins_betterSurvivalEnchantmentMultishot_shootMoreArrows(World worldIn, EntityPlayer shooter, ItemStack bowIn, int charge, CallbackInfo ci) {
        rlmixins$currentBow = bowIn;
    }

    @Inject(
            method = "findAmmo",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_betterSurvivalEnchantmentMultishot_findAmmo(EntityPlayer player, CallbackInfoReturnable<ItemStack> cir) {
        if(rlmixins$currentBow.getItem().getRegistryName().equals(rlmixins$DRAGONBOW_REGISTRY)) cir.setReturnValue(InFRLCraftWrapper.getDragonBowAmmo(player));
    }
}