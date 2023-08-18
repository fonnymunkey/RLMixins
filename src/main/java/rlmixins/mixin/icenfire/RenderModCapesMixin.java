package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.client.render.entity.RenderModCapes;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

@Mixin(RenderModCapes.class)
public abstract class RenderModCapesMixin {

    @Shadow(remap = false) public ResourceLocation betaTex;
    @Shadow(remap = false) public ResourceLocation betaElytraTex;
    @Shadow(remap = false) public ResourceLocation redTex;
    @Shadow(remap = false) public ResourceLocation redElytraTex;
    @Shadow(remap = false) public ResourceLocation blueTex;
    @Shadow(remap = false) public ResourceLocation blueElytraTex;
    @Shadow(remap = false) protected abstract boolean hasBetaCape(UUID uniqueID);
    @Shadow(remap = false) protected abstract boolean hasRedCape(UUID uniqueID);
    @Shadow(remap = false) protected abstract boolean hasBlueCape(UUID uniqueID);

    private static Field playerInfoField;
    private static Field playerTexturesField;

    /**
     * @author fonnymunkey
     * @reason rewrite for performance
     */
    @Overwrite(remap = false)
    @SubscribeEvent
    public void playerRender(RenderPlayerEvent.Pre event) {
        if(event.getEntityPlayer() instanceof AbstractClientPlayer) {
            NetworkPlayerInfo info = null;

            try {
                if(playerInfoField == null) playerInfoField = ObfuscationReflectionHelper.findField(AbstractClientPlayer.class, "field_175157_a");
                info = (NetworkPlayerInfo)playerInfoField.get(event.getEntityPlayer());
            }
            catch(IllegalArgumentException | IllegalAccessException var7) {
                var7.printStackTrace();
            }

            if(info != null) {
                Map<MinecraftProfileTexture.Type, ResourceLocation> textureMap = null;

                try {
                    if(playerTexturesField == null) playerTexturesField = ObfuscationReflectionHelper.findField(NetworkPlayerInfo.class, "field_187107_a");
                    textureMap = (Map)playerTexturesField.get(info);
                }
                catch(IllegalArgumentException | IllegalAccessException var5) {
                    var5.printStackTrace();
                }

                if(textureMap != null) {
                    if(this.hasBetaCape(event.getEntityPlayer().getUniqueID())) {
                        textureMap.put(MinecraftProfileTexture.Type.CAPE, this.betaTex);
                        textureMap.put(MinecraftProfileTexture.Type.ELYTRA, this.betaElytraTex);
                    }

                    if(this.hasRedCape(event.getEntityPlayer().getUniqueID())) {
                        textureMap.put(MinecraftProfileTexture.Type.CAPE, this.redTex);
                        textureMap.put(MinecraftProfileTexture.Type.ELYTRA, this.redElytraTex);
                    }

                    if(this.hasBlueCape(event.getEntityPlayer().getUniqueID())) {
                        textureMap.put(MinecraftProfileTexture.Type.CAPE, this.blueTex);
                        textureMap.put(MinecraftProfileTexture.Type.ELYTRA, this.blueElytraTex);
                    }
                }
            }
        }
    }
}