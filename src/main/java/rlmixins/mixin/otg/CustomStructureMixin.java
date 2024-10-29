package rlmixins.mixin.otg;

import com.pg85.otg.customobjects.structures.CustomStructure;
import com.pg85.otg.customobjects.structures.CustomStructureCoordinate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;

@Mixin(CustomStructure.class)
public abstract class CustomStructureMixin {
	
	@Shadow(remap = false)
	public CustomStructureCoordinate start;
	
	/**
	 * @author Meldexun
	 * @reason fix conflicting hash code performance
	 */
	@Overwrite(remap = false)
	public int hashCode() {
		int hash = 1;
		hash = 31 * hash + Objects.hashCode(this.start.bo3Name);
		hash = 31 * hash + this.start.getX();
		hash = 31 * hash + this.start.getY();
		hash = 31 * hash + this.start.getZ();
		return hash;
	}
}