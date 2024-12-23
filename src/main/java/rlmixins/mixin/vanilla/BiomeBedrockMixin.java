package rlmixins.mixin.vanilla;

import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.Random;

@Mixin(Biome.class)
public abstract class BiomeBedrockMixin {
	
	@Redirect(
			method = "generateBiomeTerrain",
			at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 0)
	)
	public int rlmixins_vanillaBiome_generateBiomeTerrain_nextInt(Random instance, int i) {
		if(i == 5) return instance.nextInt(ForgeConfigHandler.server.bedrockMaxRange);
		else return instance.nextInt(i);
	}
}