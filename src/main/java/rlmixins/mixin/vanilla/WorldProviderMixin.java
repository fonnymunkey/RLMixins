package rlmixins.mixin.vanilla;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.Objects;

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
        for(int i = 0; i < ForgeConfigHandler.server.randomRespawnProt; i++) {
            BlockPos attempt = ret.add(spawnFuzzHalf - world.rand.nextInt(spawnFuzz), 0, spawnFuzzHalf - world.rand.nextInt(spawnFuzz));
            BlockPos returnable = rlmixins$getOrAttemptTopSolidBlock(world, attempt, false);

            if(returnable != null) {
                cir.setReturnValue(returnable);
                return;
            }
        }
    }

    @Redirect(
            method = "getRandomizedSpawnPoint",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getTopSolidOrLiquidBlock(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/BlockPos;")
    )
    public BlockPos rlmixins_vanillaWorldProvider_getRandomizedSpawnPoint_redirect(World instance, BlockPos blockpos1) {
        return rlmixins$getOrAttemptTopSolidBlock(instance, blockpos1, true);
    }

    @Unique
    private static BlockPos rlmixins$getOrAttemptTopSolidBlock(World world, BlockPos pos, boolean force) {
        Chunk chunk = world.getChunk(pos);
        BlockPos blockpos;
        BlockPos blockpos1;
        boolean goodPos = false;

        // Gets Biome ID & references this with ForgeConfigHandler randomRespawnAvoidList biomes. If blacklisted player respawn = null
        Biome biome = world.getBiome(pos);
        String biomeID = ForgeRegistries.BIOMES.containsValue(biome)
                ? Objects.requireNonNull(ForgeRegistries.BIOMES.getKey(biome)).toString()
                : "Unknown, Could not find biome id for respawn biomes in WorldProviderMixin";

        if(!force && ForgeConfigHandler.server.randomRespawnAvoidList) {
            for (String blockedBiomeID : ForgeConfigHandler.server.randomRespawnAvoidBiomeList) {
                if (biomeID.equals(blockedBiomeID)) {
                    return null;
                }
            }
        }

        if(!force && ForgeConfigHandler.server.randomRespawnAvoidOcean && world.getBiome(pos) instanceof BiomeOcean) {
            return null;
        }

        for(blockpos = new BlockPos(pos.getX(), Math.max(chunk.getTopFilledSegment() + 16, world.getSeaLevel() + 1), pos.getZ()); blockpos.getY() > 8; blockpos = blockpos1) {
            blockpos1 = blockpos.down();
            IBlockState state = chunk.getBlockState(blockpos1);

            if(state.getMaterial().isLiquid() && state.getMaterial() != Material.WATER) {
                break;
            }

            if(state.getMaterial() == Material.WATER || (state.getMaterial().blocksMovement() && !state.getBlock().isLeaves(state, world, blockpos1) && !state.getBlock().isFoliage(world, blockpos1))) {
                if(chunk.getBlockState(blockpos).causesSuffocation() || chunk.getBlockState(blockpos.up()).causesSuffocation()) {
                    break;
                }
                goodPos = true;
                break;
            }
        }
        return (force || goodPos) ? blockpos : null;
    }
}