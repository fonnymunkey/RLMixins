package rlmixins.mixin.otg;

import com.pg85.otg.forge.dimensions.OTGWorldProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldProviderSurface;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(OTGWorldProvider.class)
public abstract class OTGWorldProviderMixin extends WorldProviderSurface {
	
	@Override
	public BlockPos getRandomizedSpawnPoint() {
		return super.getRandomizedSpawnPoint();
	}
}