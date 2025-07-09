package rlmixins.mixin.bettersurvival.vanilla;

import net.minecraft.entity.projectile.EntityArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityArrow.class)
public interface IEntityArrowAccessor {
	
	@Accessor(value = "inGround")
	boolean getInGround();
}