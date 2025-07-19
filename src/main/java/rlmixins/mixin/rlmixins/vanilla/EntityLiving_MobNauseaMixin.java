package rlmixins.mixin.rlmixins.vanilla;

import net.minecraft.entity.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import rlmixins.wrapper.IEntityLiving;

@Mixin(EntityLiving.class)
public abstract class EntityLiving_MobNauseaMixin implements IEntityLiving {
	
	@Unique
	private float rlmixins$forwardRemaining = 0.0F;
	
	@Unique
	private float rlmixins$strafeRemaining = 0.0F;
	
	@Unique
	@Override
	public float rlmixins$getForwardRemaining() {
		return this.rlmixins$forwardRemaining;
	}
	
	@Unique
	@Override
	public float rlmixins$getStrafeRemaining() {
		return this.rlmixins$strafeRemaining;
	}
	
	@Unique
	@Override
	public void rlmixins$setForwardRemaining(float val) {
		this.rlmixins$forwardRemaining = val;
	}
	
	@Unique
	@Override
	public void rlmixins$setStrafeRemaining(float val) {
		this.rlmixins$strafeRemaining = val;
	}
}