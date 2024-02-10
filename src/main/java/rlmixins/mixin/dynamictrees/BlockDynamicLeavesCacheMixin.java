package rlmixins.mixin.dynamictrees;

import com.ferreusveritas.dynamictrees.ModConfigs;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.entities.EntityFallingTree;
import com.ferreusveritas.dynamictrees.items.Seed;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(BlockDynamicLeaves.class)
public abstract class BlockDynamicLeavesCacheMixin extends BlockLeaves {

    @Shadow(remap = false) public static boolean passableLeavesModLoaded;

    @Unique
    private static final AxisAlignedBB rlmixins$LEAFAABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.5, 0.875);

    /**
     * @author fonnymunkey
     * @reason cache AABB to reduce wasted memory allocation
     */
    @Overwrite
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean unknown) {
        if(entityIn instanceof EntityItem) {
            EntityItem item = (EntityItem)entityIn;
            if(!(item.getItem().getItem() instanceof Seed)) {
                super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, unknown);
            }
        }
        else if(entityIn != null && !(entityIn instanceof EntityFallingTree)) {
            if(!passableLeavesModLoaded && !ModConfigs.vanillaLeavesCollision) {
                if(!ModConfigs.isLeavesPassable) {
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, rlmixins$LEAFAABB);
                }
            }
            else {
                super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, unknown);
            }
        }
    }
}