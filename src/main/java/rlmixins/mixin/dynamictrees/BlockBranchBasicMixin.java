package rlmixins.mixin.dynamictrees;

import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.blocks.BlockBranchBasic;
import com.ferreusveritas.dynamictrees.entities.EntityFallingTree;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(BlockBranchBasic.class)
public abstract class BlockBranchBasicMixin extends BlockBranch {

    @Shadow(remap = false) protected abstract int getSideConnectionRadius(IBlockAccess blockAccess, BlockPos pos, int radius, EnumFacing side);

    @Unique
    private static final AxisAlignedBB[][] rlmixins$bbAABBarray = new AxisAlignedBB[8][64];

    @Unique
    private static final AxisAlignedBB[][] rlmixins$cbAABBarray = new AxisAlignedBB[8][7];

    static {
        for(int r = 0; r < 8; r++) {//Technically thick branches have a bigger radius, but anything over 8 is handled separately
            double radius = (double)(r+1) / 16.0;
            double gap = 0.5 - radius;

            //bb array
            for(int i = 0; i < 64; i++) {
                boolean[] bool = new boolean[6];
                for(int j = 0; j < 6; j++) {
                    bool[j] = (i >> j & 1) != 0;//me when permutation
                }
                AxisAlignedBB aabb = new AxisAlignedBB(0.5 - radius, 0.5 - radius, 0.5 - radius, 0.5 + radius, 0.5 + radius, 0.5 + radius);
                for(EnumFacing dir : EnumFacing.VALUES) {
                    if(bool[dir.getIndex()]) {
                        aabb = aabb.expand((double) dir.getXOffset() * gap, (double) dir.getYOffset() * gap, (double) dir.getZOffset() * gap);
                    }
                }
                rlmixins$bbAABBarray[r][i] = aabb;
            }

            //cb array
            for(EnumFacing dir : EnumFacing.VALUES) {
                AxisAlignedBB aabb = new AxisAlignedBB(0.5 - radius, 0.5 - radius, 0.5 - radius, 0.5 + radius, 0.5 + radius, 0.5 + radius);
                rlmixins$cbAABBarray[r][dir.getIndex()] = aabb.expand((double)dir.getXOffset() * gap, (double)dir.getYOffset() * gap, (double)dir.getZOffset() * gap);
            }
            //No connections
            rlmixins$cbAABBarray[r][6] = new AxisAlignedBB(0.5 - radius, 0.5 - radius, 0.5 - radius, 0.5 + radius, 0.5 + radius, 0.5 + radius);
        }
    }

    public BlockBranchBasicMixin(Material material, String name) {
        super(material, name);
    }

    /**
     * @author fonnymunkey
     * @reason cache possible AABB results to reduce wasted memory allocation
     */
    @Overwrite
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess blockAccess, BlockPos pos) {
        if (state.getBlock() != this) {
            return NULL_AABB;
        } else {
            int thisRadius = this.getRadius(state);

            boolean[] bool = new boolean[6];
            for(EnumFacing dir : EnumFacing.VALUES) {
                bool[dir.getIndex()] = this.getSideConnectionRadius(blockAccess, pos, thisRadius, dir) > 0;
            }
            int index = (bool[0] ? 1 : 0) |
                    (bool[1] ? 2 : 0) |
                    (bool[2] ? 4 : 0) |
                    (bool[3] ? 8 : 0) |
                    (bool[4] ? 16 : 0) |
                    (bool[5] ? 32 : 0);

            return rlmixins$bbAABBarray[thisRadius-1][index];
        }
    }

    /**
     * @author fonnymunkey
     * @reason cache possible AABB to reduce wasted memory allocation
     */
    @Overwrite
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
        if(!(entityIn instanceof EntityFallingTree)) {
            int thisRadius = this.getRadius(state);
            boolean hasCon = false;

            for(EnumFacing dir : EnumFacing.VALUES) {
                int connRadius = this.getSideConnectionRadius(world, pos, thisRadius, dir);
                if(connRadius > 0) {
                    hasCon = true;
                    connRadius = MathHelper.clamp(connRadius, 1, thisRadius);
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, rlmixins$cbAABBarray[connRadius-1][dir.getIndex()]);
                }
            }
            if(!hasCon) {
                addCollisionBoxToList(pos, entityBox, collidingBoxes, rlmixins$cbAABBarray[thisRadius-1][6]);
            }
        }
    }
}