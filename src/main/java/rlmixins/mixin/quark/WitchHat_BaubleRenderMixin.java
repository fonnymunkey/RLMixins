package rlmixins.mixin.quark;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import rlmixins.client.layer.LayerWitchBaubleHat;
import vazkii.quark.base.module.Feature;
import vazkii.quark.vanity.feature.WitchHat;

import java.util.Map;

/**
 * By cdstk
 */
@Mixin(WitchHat.class)
public abstract class WitchHat_BaubleRenderMixin extends Feature {
    
    @Override
    public void initClient() {
        rlmixins$addRenderLayers();
    }

    @Unique
    private static void rlmixins$addRenderLayers() {
        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
        rlmixins$addLayersToSkin(skinMap.get("default"));
        rlmixins$addLayersToSkin(skinMap.get("slim"));
    }
    
    @Unique
    private static void rlmixins$addLayersToSkin(RenderPlayer renderPlayer) {
        renderPlayer.addLayer(new LayerWitchBaubleHat(renderPlayer));
    }
}