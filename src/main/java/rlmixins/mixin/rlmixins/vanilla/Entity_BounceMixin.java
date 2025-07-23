package rlmixins.mixin.rlmixins.vanilla;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.wrapper.IEntity;

@Mixin(Entity.class)
public abstract class Entity_BounceMixin implements IEntity {
	
	@Shadow public double motionX;
	
	@Shadow public double motionZ;
	
	@Unique
	private double rlmixins$prevMotionX = 0.0D;
	
	@Unique
	private double rlmixins$prevMotionZ = 0.0D;
	
	@Unique
	private int rlmixins$lastBounceTick = 0;
	
	@Unique
	@Override
	public double rlmixins$getPrevMotionX() {
		return this.rlmixins$prevMotionX;
	}
	
	@Unique
	@Override
	public double rlmixins$getPrevMotionZ() {
		return this.rlmixins$prevMotionZ;
	}
	
	@Unique
	@Override
	public int rlmixins$getLastBounceTick() {
		return this.rlmixins$lastBounceTick;
	}
	
	@Unique
	@Override
	public void rlmixins$setLastBounceTick(int val) {
		this.rlmixins$lastBounceTick = val;
	}
	
	@Inject(
			method = "onUpdate",
			at = @At("HEAD")
	)
	private void rlmixins_vanillaEntity_onUpdate(CallbackInfo ci) {
		this.rlmixins$prevMotionX = this.motionX;
		this.rlmixins$prevMotionZ = this.motionZ;
	}
}