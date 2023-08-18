package rlmixins.mixin.vanilla;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import rlmixins.handlers.ForgeConfigHandler;

@Mixin(WorldProvider.class)
public abstract class WorldProviderMixin {

    @Shadow protected World world;

    @Inject(
            method = "getRandomizedSpawnPoint",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getTopSolidOrLiquidBlock(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/BlockPos;", shift = At.Shift.BEFORE),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    public void rlmixins_vanillaWorldProvider_getRandomizedSpawnPoint_inject(CallbackInfoReturnable<BlockPos> cir, BlockPos ret, boolean isAdventure, int spawnFuzz, int border, int spawnFuzzHalf) {
        for(int i = 0; i < ForgeConfigHandler.server.randomRespawnOceanProt; i++) {
            BlockPos attempt = ret.add(spawnFuzzHalf - world.rand.nextInt(spawnFuzz), 0, spawnFuzzHalf - world.rand.nextInt(spawnFuzz));
            if(!(world.getBiome(attempt) instanceof BiomeOcean)) {
                cir.setReturnValue(getOrAttemptTopSolidBlock(world, attempt));
                return;
            }
        }
    }

    @Redirect(
            method = "getRandomizedSpawnPoint",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getTopSolidOrLiquidBlock(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/BlockPos;")
    )
    public BlockPos rlmixins_vanillaWorldProvider_getRandomizedSpawnPoint_redirect(World instance, BlockPos blockpos1) {
        return getOrAttemptTopSolidBlock(instance, blockpos1);
    }

    private static BlockPos getOrAttemptTopSolidBlock(World world, BlockPos pos) {
        Chunk chunk = world.getChunk(pos);
        BlockPos blockpos;
        BlockPos blockpos1;

        for(blockpos = new BlockPos(pos.getX(), Math.max(chunk.getTopFilledSegment() + 16, world.getSeaLevel() + 1), pos.getZ()); blockpos.getY() > 0; blockpos = blockpos1) {
            blockpos1 = blockpos.down();
            IBlockState state = chunk.getBlockState(blockpos1);

            if(state.getMaterial().isLiquid() || (state.getMaterial().blocksMovement() && !state.getBlock().isLeaves(state, world, blockpos1) && !state.getBlock().isFoliage(world, blockpos1))) {
                break;
            }
        }

        return blockpos;
    }
}