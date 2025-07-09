package rlmixins.wrapper;

import net.minecraft.entity.EntityLivingBase;
import net.silentchaos512.scalinghealth.event.BlightHandler;

public abstract class ScalingHealthWrapper {
	
	public static boolean isEntityBlight(EntityLivingBase entity) {
		return BlightHandler.isBlight(entity);
	}
}