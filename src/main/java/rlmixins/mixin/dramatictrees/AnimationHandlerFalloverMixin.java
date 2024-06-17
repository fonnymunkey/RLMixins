package rlmixins.mixin.dramatictrees;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.entities.EntityFallingTree;
import com.ferreusveritas.dynamictrees.entities.animation.AnimationHandlerFallover;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import rlmixins.RLMixins;
import rlmixins.handlers.ForgeConfigHandler;

@Mixin(AnimationHandlerFallover.class)
public abstract class AnimationHandlerFalloverMixin {

    @Unique
    private float rlmixins$fallSpeed;
    @Unique
    private int rlmixins$trunkHeight;
    @Unique
    private Block rlmixins$activeBlock;

    @Redirect(
            method = "testCollision",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;")
    )
    public Block rlmixins_dramaticTreesAnimationHandlerFallover_testCollision_destroyBlockInject(IBlockState instance) {
        this.rlmixins$activeBlock = instance.getBlock();
        return this.rlmixins$activeBlock;
    }

    @Redirect(
            method = "testCollision",
            at = @At(value = "INVOKE", target = "Lcom/ferreusveritas/dynamictrees/api/TreeHelper;isLeaves(Lnet/minecraft/block/Block;)Z"),
            remap = false
    )
    public boolean rlmixins_dramaticTreesAnimationHandlerFallover_testCollision_isLeaves(Block block) {
        if(TreeHelper.isLeaves(block)) return true;
        for(Class<?> clazz : ForgeConfigHandler.getDramaticTreeNonSolidList()) {
            if(clazz.isInstance(block)) return true;
        }
        if(ForgeConfigHandler.server.dramaticTreesCollisionNameDebug) {
            RLMixins.LOGGER.log(Level.INFO, "DramaticTrees Collision Name: " + block.getClass().getCanonicalName());
        }
        return false;
    }

    @Redirect(
            method = "testCollision",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;destroyBlock(Lnet/minecraft/util/math/BlockPos;Z)Z")
    )
    public boolean rlmixins_dramaticTreesAnimationHandlerFallover_testCollision_destroyBlock(World instance, BlockPos pos, boolean dropBlock) {
        if(this.rlmixins$activeBlock == null) return false;
        for(Class<?> clazz : ForgeConfigHandler.getDramaticTreeBreakableList()) {
            if(clazz.isInstance(this.rlmixins$activeBlock)) {
                instance.destroyBlock(pos, dropBlock);
                this.rlmixins$activeBlock = null;
                return true;
            }
        }
        this.rlmixins$activeBlock = null;
        return false;
    }

    @Inject(
            method = "handleMotion",
            at = @At(value = "INVOKE", target = "Lcom/ferreusveritas/dynamictrees/entities/animation/AnimationHandlerFallover;testCollision(Lcom/ferreusveritas/dynamictrees/entities/EntityFallingTree;)Z"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            remap = false
    )
    public void rlmixins_dramaticTreesAnimationHandlerFallover_handleMotion_inject(EntityFallingTree entity, CallbackInfo ci, float fallSpeed) {
        this.rlmixins$fallSpeed = fallSpeed;
    }

    @Redirect(
            method = "handleMotion",
            at = @At(value = "FIELD", target = "Lcom/ferreusveritas/dynamictrees/entities/animation/AnimationHandlerFallover;fallSound:Z", ordinal = 0),
            remap = false
    )
    public boolean rlmixins_dramaticTreesAnimationHandlerFallover_handleMotion_getField(AnimationHandlerFallover instance) {
        return false;
    }

    @Redirect(
            method = "handleMotion",
            at= @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FF)V")
    )
    public void rlmixins_dramaticTreesAnimationHandlerFallover_handleMotion(World instance, EntityPlayer player, BlockPos pos, SoundEvent soundIn, SoundCategory category, float volume, float pitch) {
        if(this.rlmixins$fallSpeed > 0.1F && !instance.isRemote) instance.playSound(player, pos, soundIn, category, (Math.min(1.5F, this.rlmixins$fallSpeed) / 1.5F) * 0.6F + 0.1F, pitch * 0.2F + 0.6F);
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