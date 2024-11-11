package rlmixins.mixin.classyhats;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import wiresegal.classyhats.block.BlockHatContainer;

@Mixin(BlockHatContainer.class)
public abstract class BlockHatContainerMixin {
	
	@Redirect(
			method = "getPickBlock(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/RayTraceResult;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;",
			at = @At(value = "INVOKE", target = "Lkotlin/jvm/internal/Intrinsics;checkParameterIsNotNull(Ljava/lang/Object;Ljava/lang/String;)V", ordinal = 3),
			remap = false
	)
	public void rlmixins_classyHatsBlockHatContainer_getPickBlock(Object o, String s) {
		//noop
	}
}