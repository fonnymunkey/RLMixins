package rlmixins.mixin.forge;

import net.minecraftforge.oredict.OreDictionary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(OreDictionary.class)
public abstract class OreDictionaryMixin {

    @Redirect(
            method = "registerOreImpl",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/FMLLog;bigWarning(Ljava/lang/String;[Ljava/lang/Object;)V"),
            remap = false
    )
    private static void rlmixins_forgeOreDictionary_registerOreImpl(String i, Object[] format) {
        //noop
    }
}