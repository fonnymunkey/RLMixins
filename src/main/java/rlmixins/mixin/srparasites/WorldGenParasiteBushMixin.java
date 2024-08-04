package rlmixins.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.world.gen.feature.WorldGenParasiteBush;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(WorldGenParasiteBush.class)
public abstract class WorldGenParasiteBushMixin {
	
	@Inject(
			method = "func_180709_b",
			at = @At("HEAD"),
			remap = false,
			cancellable = true
	)
	public void rlmixins_srparasitesWorldGenParasiteBush_generate(World worldIn, Random rand, BlockPos position, CallbackInfoReturnable<Boolean> cir) {
		if(!worldIn.isAreaLoaded(position, 10)) cir.setReturnValue(false);
	}
	
	@Inject(
			method = "VinGen",
			at = @At("HEAD"),
			remap = false,
			cancellable = true
	)
	public void rlmixins_srparasitesWorldGenParasiteBush_vinGen(World worldIn, Random rand, BlockPos position, CallbackInfoReturnable<Boolean> cir) {
		if(!worldIn.isAreaLoaded(position, 10)) cir.setReturnValue(false);
	}
}