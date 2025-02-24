package rlmixins.mixin.quark;

import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vazkii.quark.world.feature.Archaeologist;

@Mixin(Archaeologist.class)
public abstract class FeatureArchaeologistMixin {

    @Shadow(remap = false)
    public static Item archaeologist_hat;

    @Redirect(
            method = "onDrops",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", ordinal = 0)
    )
    private Item rlmixins_quarkArchaeologist_onDrops(ItemStack instance, @Local EntityPlayer player, @Local ItemStack hat){
        if(hat.getItem() == archaeologist_hat) return hat.getItem();
        else {
            int archaeologistHatFound = BaublesApi.isBaubleEquipped(player, archaeologist_hat);
            if (archaeologistHatFound != -1) return BaublesApi.getBaublesHandler(player).getStackInSlot(archaeologistHatFound).getItem();
        }
        return null;
    }
}
