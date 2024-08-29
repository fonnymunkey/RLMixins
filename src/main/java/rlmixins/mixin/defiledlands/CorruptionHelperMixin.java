package rlmixins.mixin.defiledlands;

import lykrast.defiledlands.common.util.Config;
import lykrast.defiledlands.common.util.CorruptionHelper;
import lykrast.defiledlands.common.world.biome.BiomeDefiled;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.Random;

@Mixin(CorruptionHelper.class)
public abstract class CorruptionHelperMixin {
	
	/**
	 * @author fonnymunkey
	 * @reason improve corruption performance
	 */
	@Overwrite(remap = false)
	public static void spread(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if(worldIn.isRemote) return;
		if(rand.nextFloat() > ForgeConfigHandler.server.defiledCorruptionChance) return;
		if(!worldIn.isAreaLoaded(pos, 5)) return;
		
		if(CorruptionHelper.canSpread(worldIn, pos, rand)) {
			for(int i = 0; i < 4; ++i) {
				BlockPos blockpos = pos.add(rand.nextInt(5) - 2, rand.nextInt(5) - 2, rand.nextInt(5) - 2);
				if(blockpos.getY() < 1 || blockpos.getY() > 255 || blockpos.equals(pos)) continue;
				
				IBlockState iblockstate = worldIn.getBlockState(blockpos);
				if(iblockstate.getMaterial() == Material.AIR) continue;
				CorruptionHelper.corrupt(worldIn, blockpos, iblockstate);
			}
		}
	}
	
	/**
	 * @author fonnymunkey
	 * @reason improve corruption performance
	 */
	@Overwrite(remap = false)
	public static boolean canSpread(World worldIn, BlockPos pos, Random rand) {
		if(!Config.confinedSpread) return true;
		return worldIn.getBiome(pos) instanceof BiomeDefiled;
	}
}