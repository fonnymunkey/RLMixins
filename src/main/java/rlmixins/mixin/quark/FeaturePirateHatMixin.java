package rlmixins.mixin.quark;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import rlmixins.layer.LayerPirateHat;
import vazkii.quark.base.module.Feature;
import vazkii.quark.world.feature.PirateShips;

import java.util.Map;

@Mixin(PirateShips.class)
public abstract class FeaturePirateHatMixin extends Feature {

    @Override
    public void init(){
        addRenderLayers();
    }

    @Unique
    private static void addRenderLayers() {
        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
        addLayersToSkin((RenderPlayer)skinMap.get("default"), false);
        addLayersToSkin((RenderPlayer)skinMap.get("slim"), true);
    }

    @Unique
    private static void addLayersToSkin(RenderPlayer renderPlayer, boolean slim) {
        renderPlayer.addLayer(new LayerPirateHat(renderPlayer));
    }
}
