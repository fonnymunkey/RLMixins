package rlmixins.mixin.jsonpainting;

import git.jbredwards.jsonpaintings.common.EventHandler;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EventHandler.class)
public abstract class EventHandlerMixin {

    @Inject(
            method = "getRandomArt",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_jsonPaintingsEventHandler_getRandomArt(EntityPainting painting, EntityPlayer player, EnumFacing facing, CallbackInfoReturnable<EntityPainting.EnumArt> cir) {
        if(painting != null && !painting.onValidSurface()) cir.setReturnValue(EntityPainting.EnumArt.KEBAB);
    }
}