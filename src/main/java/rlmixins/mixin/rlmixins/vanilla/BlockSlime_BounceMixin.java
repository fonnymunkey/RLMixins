package rlmixins.mixin.rlmixins.vanilla;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlime;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import rlmixins.wrapper.IEntity;

@Mixin(BlockSlime.class)
public abstract class BlockSlime_BounceMixin extends Block {
	
	@Unique
	private static final AxisAlignedBB rlmixins$SLIME_COLLISION_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.9375D, 0.9375D);
	
	@Unique
	private static final AxisAlignedBB rlmixins$SLIME_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 1.0D, 0.9375D);
	
	public BlockSlime_BounceMixin(Material blockMaterialIn, MapColor blockMapColorIn) {
		super(blockMaterialIn, blockMapColorIn);
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return rlmixins$SLIME_COLLISION_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		return rlmixins$SLIME_AABB.offset(pos);
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
		if(entity.collidedHorizontally && !entity.isSneaking() && entity.isEntityAlive()) {
			if(entity.world.isRemote && !(entity instanceof EntityPlayer)) return;
			if(((IEntity)entity).rlmixins$getLastBounceTick() >= entity.ticksExisted - 1) return;
			boolean bounced = false;
			if(entity.motionX == 0.0D && entity.motionX != ((IEntity)entity).rlmixins$getPrevMotionX()) {
				if(((IEntity)entity).rlmixins$getPrevMotionX() > 0.1D || ((IEntity)entity).rlmixins$getPrevMotionX() < -0.1D) {
					entity.motionX = -0.8D * ((IEntity)entity).rlmixins$getPrevMotionX();
					bounced = true;
				}
			}
			if(entity.motionZ == 0.0D && entity.motionZ != ((IEntity)entity).rlmixins$getPrevMotionZ()) {
				if(((IEntity)entity).rlmixins$getPrevMotionZ() > 0.1D || ((IEntity)entity).rlmixins$getPrevMotionZ() < -0.1D) {
					entity.motionZ = -0.8D * ((IEntity)entity).rlmixins$getPrevMotionZ();
					bounced = true;
				}
			}
			if(bounced) ((IEntity)entity).rlmixins$setLastBounceTick(entity.ticksExisted);
		}
	}
}