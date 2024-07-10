package rlmixins.mixin.inspirations;

import knightminer.inspirations.library.ItemMetaKey;
import knightminer.inspirations.library.client.ClientUtil;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ClientUtil.class)
public abstract class ClientUtilMixin {

    @Inject(
            method = "getStackColor(Lknightminer/inspirations/library/ItemMetaKey;)Ljava/lang/Integer;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;getFrameTextureData(I)[[I"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private static void rlmixins_inspirationsClientUtil_getStackColor(ItemMetaKey key, CallbackInfoReturnable<Integer> cir, IBakedModel model, TextureAtlasSprite sprite) {
        if(sprite.getFrameCount() < 1) cir.setReturnValue(-1);
    }
}