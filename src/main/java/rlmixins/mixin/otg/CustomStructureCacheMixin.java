package rlmixins.mixin.otg;

import com.pg85.otg.customobjects.structures.CustomStructureCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CustomStructureCache.class)
public abstract class CustomStructureCacheMixin {
	
	@ModifyConstant(
			method = "saveToDisk",
			constant = @Constant(intValue = 300),
			remap = false
	)
	public int rlmixins_otgCustomStructureCache(int constant) {
		return 900;
	}
}