package rlmixins.mixin.quark;

import baubles.api.BaublesApi;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vazkii.quark.base.module.Feature;
import vazkii.quark.world.feature.Archaeologist;

/**
 * By cdstk
 */
@Mixin(Archaeologist.class)
public abstract class Archaeologist_BaubleEffectsMixin extends Feature {

    @Shadow(remap = false)
    public static Item archaeologist_hat;

    @Redirect(
            method = "onDrops",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", ordinal = 0)
    )
    private Item rlmixins_quarkArchaeologist_onDrops(ItemStack instance, @Local EntityPlayer player) {
        if(BaublesApi.isBaubleEquipped(player, archaeologist_hat) != -1) return archaeologist_hat;
        return instance.getItem();
    }
}