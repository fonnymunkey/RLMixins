package rlmixins.mixin.vanilla;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.gen.MapGenCaves;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.handlers.ForgeConfigHandler;

@Mixin(MapGenCaves.class)
public abstract class MapGenCavesMixin {
	
	@Inject(
			method = "canReplaceBlock",
			at = @At("RETURN"),
			cancellable = true
	)
	public void rlmixins_vanillaMapGenCaves_canReplaceBlocks(IBlockState p_175793_1_, IBlockState p_175793_2_, CallbackInfoReturnable<Boolean> cir) {
		if(!cir.getReturnValue()) {
			cir.setReturnValue(ForgeConfigHandler.isBlockCarvable(p_175793_1_.getBlock()));
		}
	}
}