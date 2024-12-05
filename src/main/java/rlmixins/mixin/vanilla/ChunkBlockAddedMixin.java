package rlmixins.mixin.vanilla;

import net.minecraft.block.Block;
import net.minecraft.block.BlockConcretePowder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Chunk.class)
public abstract class ChunkBlockAddedMixin {
	
	@Redirect(
			method = "setBlockState",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onBlockAdded(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)V")
	)
	public void rlmixins_vanillaChunk_setBlockState_onBlockAdded(Block instance, World worldIn, BlockPos pos, IBlockState state) {
		if(instance instanceof BlockConcretePowder) return;
		instance.onBlockAdded(worldIn, pos, state);
	}
}