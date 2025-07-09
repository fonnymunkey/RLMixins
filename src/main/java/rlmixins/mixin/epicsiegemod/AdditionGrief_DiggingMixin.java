package rlmixins.mixin.epicsiegemod;

import funwayguy.epicsiegemod.ai.additions.AdditionGrief;
import net.minecraft.entity.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Fix by Kotlin
 */
@Mixin(AdditionGrief.class)
public abstract class AdditionGrief_DiggingMixin {
	
	/**
	 * @author kotlin
	 * @reason disable griefing AI for performance
	 */
	@Overwrite(remap = false)
	public boolean isValid(EntityLiving entityLiving) {
		return false;
	}
}