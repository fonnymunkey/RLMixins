package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.enums.EnumDragonTextures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.ForgeConfigHandler;

@Mixin(EnumDragonTextures.class)
public abstract class EnumDragonTexturesMixin {

    @Redirect(
            method = "getFireDragonTextures",
            at = @At(value = "INVOKE", target = "Lcom/github/alexthe666/iceandfire/entity/EntityDragonBase;isModelDead()Z"),
            remap = false
    )
    private static boolean rlmixins_iceAndFireEnumDragonTextures_getFireDragonTextures_isModelDead(EntityDragonBase instance) {
        return instance.isModelDead() || ForgeConfigHandler.client.spookyDragons;
    }

    @Redirect(
            method = "getFireDragonTextures",
            at = @At(value = "INVOKE", target = "Lcom/github/alexthe666/iceandfire/entity/EntityDragonBase;getDeathStage()I"),
            remap = false
    )
    private static int rlmixins_iceAndFireEnumDragonTextures_getFireDragonTextures_getDeathStage(EntityDragonBase instance) {
        return ForgeConfigHandler.client.spookyDragons ? 10000 : instance.getDeathStage();
    }

    @Redirect(
            method = "getIceDragonTextures",
            at = @At(value = "INVOKE", target = "Lcom/github/alexthe666/iceandfire/entity/EntityDragonBase;isModelDead()Z"),
            remap = false
    )
    private static boolean rlmixins_iceAndFireEnumDragonTextures_getIceDragonTextures_isModelDead(EntityDragonBase instance) {
        return instance.isModelDead() || ForgeConfigHandler.client.spookyDragons;
    }

    @Redirect(
            method = "getIceDragonTextures",
            at = @At(value = "INVOKE", target = "Lcom/github/alexthe666/iceandfire/entity/EntityDragonBase;getDeathStage()I"),
            remap = false
    )
    private static int rlmixins_iceAndFireEnumDragonTextures_getIceDragonTextures_getDeathStage(EntityDragonBase instance) {
        return ForgeConfigHandler.client.spookyDragons ? 10000 : instance.getDeathStage();
    }
}