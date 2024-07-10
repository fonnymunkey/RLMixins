package rlmixins.mixin.dramatictrees;

import com.ferreusveritas.dynamictrees.ModConfigs;
import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.network.MapSignal;
import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.blocks.BlockTrunkShell;
import com.ferreusveritas.dynamictrees.entities.EntityFallingTree;
import com.ferreusveritas.dynamictrees.entities.animation.AnimationHandlerFallover;
import com.ferreusveritas.dynamictrees.systems.nodemappers.NodeExtState;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.RLMixins;
import rlmixins.handlers.ForgeConfigHandler;

@Mixin(AnimationHandlerFallover.class)
public abstract class AnimationHandlerFalloverMixin {

    @Unique
    private int rlmixins$trunkHeight;

    /**
     * @author fonnymunkey
     * @reason improve tree collision
     */
    @Overwrite(remap = false)
    private boolean testCollision(EntityFallingTree entity) {
        BlockPos.PooledMutableBlockPos pooledPos = BlockPos.PooledMutableBlockPos.retain();
        try {
            EnumFacing toolDir = entity.getDestroyData().toolDir;
            float actingAngle = toolDir.getAxis() == EnumFacing.Axis.X ? entity.rotationYaw : entity.rotationPitch;
            int offsetX = toolDir.getXOffset();
            int offsetZ = toolDir.getZOffset();
            float h = MathHelper.sin((float)Math.toRadians((double)actingAngle)) * (float)(offsetX | offsetZ);
            float v = MathHelper.cos((float)Math.toRadians((double)actingAngle));
            float xbase = (float)(entity.posX + (double)((float)offsetX * (-0.5F + v * 0.5F + h * 0.5F)));
            float ybase = (float)(entity.posY - (double)(h * 0.5F) + (double)(v * 0.5F));
            float zbase = (float)(entity.posZ + (double)((float)offsetZ * (-0.5F + v * 0.5F + h * 0.5F)));
            int trunkHeight = entity.getDestroyData().trunkHeight;
            float maxRadius = (float)entity.getDestroyData().getBranchRadius(0) / 16.0F;
            int segmentHeight = Math.min(trunkHeight, 24);

            //Iterate segments upwards
            for(int segment = 0; segment < segmentHeight; segment++) {
                int solidBlock = 0;

                float segX = xbase + h * (float)segment * (float)offsetX;
                float segY = ybase + v * (float)segment;
                float segZ = zbase + h * (float)segment * (float)offsetZ;
                float tex = 0.0625F;
                float half = MathHelper.clamp(tex * (float)(segment + 1) * 2.0F, tex, maxRadius);

                //Use default handling
                if(!ModConfigs.enableFallingTreeDomino) {
                    AxisAlignedBB testBB = new AxisAlignedBB((double)(segX - half), (double)(segY - half), (double)(segZ - half), (double)(segX + half), (double)(segY + half), (double)(segZ + half));
                    return !entity.world.getCollisionBoxes(entity, testBB).isEmpty();
                }

                int j2 = MathHelper.floor(segX - half);
                int k2 = MathHelper.ceil(segX + half);
                int l2 = MathHelper.floor(segY - half);
                int i3 = MathHelper.ceil(segY + half);
                int j3 = MathHelper.floor(segZ - half);
                int k3 = MathHelper.ceil(segZ + half);

                //Iterate possible colliding blocks
                for(int l3 = j2; l3 < k2; ++l3) {
                    for(int i4 = l2; i4 < i3; ++i4) {
                        for(int j4 = j3; j4 < k3; ++j4) {
                            IBlockState collBlockState = entity.world.getBlockState(pooledPos.setPos(l3, i4, j4));
                            if(collBlockState.getMaterial() != Material.AIR) {
                                Block collBlock = collBlockState.getBlock();
                                //Ignore BlockTrunkShell as they are not removed before the tree is fully gone
                                if(collBlock instanceof BlockTrunkShell) {
                                    continue;
                                }

                                //Handle branches separately
                                if(TreeHelper.isBranch(collBlock)) {
                                    //If branch is thin, break through it
                                    if(((BlockBranch)collBlock).getRadius(collBlockState) < 3) {
                                        entity.world.destroyBlock(pooledPos.toImmutable(), false);
                                        continue;
                                    }
                                    //If branch isn't thin and tree is tall enough, check for domino
                                    else if(trunkHeight > 4) {
                                        BlockPos dominoPos = ModConfigs.treeStumping ? TreeHelper.findRootNode(entity.world, pooledPos).up(2) : TreeHelper.findRootNode(entity.world, pooledPos).up();

                                        if(!entity.world.isRemote) {
                                            entity.world.spawnParticle(EnumParticleTypes.BARRIER, pooledPos.getX(), pooledPos.getY(), pooledPos.getZ(), 0, 0, 0);
                                            entity.world.spawnParticle(EnumParticleTypes.BARRIER, dominoPos.getX(), dominoPos.getY(), dominoPos.getZ(), 0, 0, 0);
                                        }

                                        NodeExtState extStateMapper = new NodeExtState(dominoPos);
                                        ((BlockBranch)collBlock).analyse(collBlockState, entity.world, dominoPos, null, new MapSignal(extStateMapper));
                                        int dominoTrunkHeight = 1;
                                        for(BlockPos iter = new BlockPos(0, 1, 0); extStateMapper.getExtStateMap().containsKey(iter); iter = iter.up()) {
                                            dominoTrunkHeight++;
                                        }

                                        //If falling tree height is greater than or equal to hit tree, domino, otherwise solid
                                        if(trunkHeight >= dominoTrunkHeight) {
                                            if(!entity.world.isRemote) ((BlockBranch)collBlock).dominoBreak(entity.world, dominoPos, toolDir);
                                        }
                                    }
                                    solidBlock++;
                                    continue;
                                }

                                if(ForgeConfigHandler.getDramaticTreeNonSolidList().contains(collBlock)) {
                                    if(!entity.world.isRemote && ForgeConfigHandler.getDramaticTreeNonSolidBreakableList().contains(collBlock)) {
                                        entity.world.destroyBlock(pooledPos.toImmutable(), false);
                                    }
                                }
                                else {
                                    if(!entity.world.isRemote && ForgeConfigHandler.getDramaticTreeSolidBreakableList().contains(collBlock)) {
                                        entity.world.destroyBlock(pooledPos.toImmutable(), false);
                                    }
                                    if(ForgeConfigHandler.server.dramaticTreesCollisionNameDebug) {
                                        RLMixins.LOGGER.log(Level.INFO, "DramaticTrees Collision Name: " + collBlock.getRegistryName());
                                    }
                                    solidBlock++;
                                }
                            }
                        }
                    }
                }
                if(solidBlock > 0) {
                    return true;
                }
            }
            return false;
        }
        catch(Exception ignored) { }
        finally {
            pooledPos.release();
        }
        return false;
    }

    @Inject(
            method = "initMotion",
            at = @At(value = "INVOKE", target = "Lcom/ferreusveritas/dynamictrees/entities/EntityFallingTree;getDestroyData()Lcom/ferreusveritas/dynamictrees/util/BranchDestructionData;", ordinal = 2),
            remap = false
    )
    public void rlmixins_dramaticTreesAnimationHandlerFallover_initMotion_0(EntityFallingTree entity, CallbackInfo ci) {
        this.rlmixins$trunkHeight = entity.getDestroyData().trunkHeight;
    }

    @Redirect(
            method = "initMotion",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FF)V", ordinal = 0)
    )
    public void rlmixins_dramaticTreesAnimationHandlerFallover_initMotion_1(World instance, EntityPlayer player, BlockPos pos, SoundEvent soundIn, SoundCategory category, float volume, float pitch) {
        if(!instance.isRemote) instance.playSound(player, pos, soundIn, category, ((float)this.rlmixins$trunkHeight / 7.0F) * 0.4F + 0.1F, instance.rand.nextFloat() * 0.2F + 0.8F);
    }

    @Redirect(
            method = "initMotion",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FF)V", ordinal = 1)
    )
    public void rlmixins_dramaticTreesAnimationHandlerFallover_initMotion_2(World instance, EntityPlayer player, BlockPos pos, SoundEvent soundIn, SoundCategory category, float volume, float pitch) {
        if(!instance.isRemote) instance.playSound(player, pos, soundIn, category, Math.min(1.0F, ((float)this.rlmixins$trunkHeight / 40.0F)) * 0.6F + 0.2F, instance.rand.nextFloat() * 0.2F + 0.8F);
    }
}