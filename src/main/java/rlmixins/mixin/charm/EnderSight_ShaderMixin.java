package rlmixins.mixin.charm;

import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import svenhjol.charm.brewing.feature.EnderSight;

@Mixin(EnderSight.class)
public abstract class EnderSight_ShaderMixin {
	
	@Unique
	private static final ResourceLocation rlmixins$ENDER_INVERT = new ResourceLocation("shaders/post/invert.json");
	
	@Redirect(
			method = "onClientTick",
			at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;")
	)
	private ResourceLocation rlmixins_charmEnderSight_onClientTick(String namespaceIn, String pathIn) {
		return rlmixins$ENDER_INVERT;
	}
}