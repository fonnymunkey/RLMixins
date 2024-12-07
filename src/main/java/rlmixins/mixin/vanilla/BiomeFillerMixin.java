package rlmixins.mixin.vanilla;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.Random;

@Mixin(Biome.class)
public abstract class BiomeFillerMixin {
	
	@Unique
	private IBlockState rlmixins$fillerState;
	
	@Unique
	private boolean rlmixins$useFiller = false;
	
	@Inject(
			method = "generateBiomeTerrain",
			at = @At("HEAD")
	)
	public void rlmixins_vanillaBiome_generateBiomeTerrain_head(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal, CallbackInfo ci) {
		if(worldIn == null || worldIn.provider == null) return;
		this.rlmixins$fillerState = ForgeConfigHandler.getDimensionFillerBlock(worldIn.provider.getDimension());
		this.rlmixins$useFiller = this.rlmixins$fillerState != null && this.rlmixins$fillerState.getBlock() != Blocks.AIR;
	}
	
	@Inject(
			method = "generateBiomeTerrain",
			at = @At("RETURN")
	)
	public void rlmixins_vanillaBiome_generateBiomeTerrain_return(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal, CallbackInfo ci) {
		if(this.rlmixins$useFiller && this.rlmixins$fillerState != null) {
			int l = x & 15;
			int i1 = z & 15;
			for(int j1 = 255; j1 >= 0; --j1) {
				IBlockState iblockstate = chunkPrimerIn.getBlockState(i1, j1, l);
				if(iblockstate.getBlock() == Blocks.STONE) {
					chunkPrimerIn.setBlockState(i1, j1, l, this.rlmixins$fillerState);
				}
			}
		}
	}
}