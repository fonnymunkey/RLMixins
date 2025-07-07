package rlmixins.mixin.quark;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import rlmixins.layer.LayerPirateHat;
import vazkii.quark.base.module.Feature;
import vazkii.quark.world.feature.PirateShips;

import java.util.Map;

@Mixin(PirateShips.class)
public abstract class FeaturePirateHatMixin extends Feature {

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
        renderPlayer.addLayer(new LayerPirateHat(renderPlayer));
    }
}
