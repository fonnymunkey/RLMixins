package rlmixins.mixin.quark;

import baubles.api.BaublesApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.layer.LayerArchaeologistHat;
import vazkii.quark.base.module.Feature;
import vazkii.quark.world.feature.Archaeologist;

import java.util.Map;

@Mixin(Archaeologist.class)
public abstract class FeatureArchaeologistMixin extends Feature {

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

    @SideOnly(Side.CLIENT)
    @Override
    public void initClient(){
        rLMixins$addRenderLayers();
    }

    @SideOnly(Side.CLIENT)
    @Unique
    private static void rLMixins$addRenderLayers() {
        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
        rLMixins$addLayersToSkin(skinMap.get("default"), false);
        rLMixins$addLayersToSkin(skinMap.get("slim"), true);
    }

    @SideOnly(Side.CLIENT)
    @Unique
    private static void rLMixins$addLayersToSkin(RenderPlayer renderPlayer, boolean slim) {
        renderPlayer.addLayer(new LayerArchaeologistHat(renderPlayer));
    }
}
