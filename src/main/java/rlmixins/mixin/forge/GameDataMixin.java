package rlmixins.mixin.forge;

import net.minecraftforge.registries.GameData;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameData.class)
public abstract class GameDataMixin {

    @Redirect(
            method = "checkPrefix(Ljava/lang/String;Z)Lnet/minecraft/util/ResourceLocation;",
            at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V"),
            remap = false
    )
    private static void rlmixins_forgeGameData_checkPrefix(Logger instance, String s, Object x, Object y, Object z) {
        //noop
    }
}