package rlmixins.handlers.vanilla;

import net.minecraft.block.BlockSlime;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.wrapper.IEntity;

public class SlimeProjectileBounceHandler {
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onProjectileImpactEvent(ProjectileImpactEvent event) {
		Entity entity = event.getEntity();
		RayTraceResult rayTraceResult = event.getRayTraceResult();
		if(entity.isEntityAlive() && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
			boolean xHit = rayTraceResult.sideHit == EnumFacing.EAST || rayTraceResult.sideHit == EnumFacing.WEST;
			boolean zHit = rayTraceResult.sideHit == EnumFacing.NORTH || rayTraceResult.sideHit == EnumFacing.SOUTH;
			if(xHit || zHit) {
				if(entity.world.getBlockState(rayTraceResult.getBlockPos()).getBlock() instanceof BlockSlime) {
					if(((IEntity)entity).rlmixins$getLastBounceTick() >= entity.ticksExisted - 1) return;
					boolean bounced = false;
					if(xHit) {
						if(entity.motionX > 0.1D || entity.motionX < -0.1D) {
							entity.motionX = -0.8D * entity.motionX;
							bounced = true;
						}
					}
					if(zHit) {
						if(entity.motionZ > 0.1D || entity.motionZ < -0.1D) {
							entity.motionZ = -0.8D * entity.motionZ;
							bounced = true;
						}
					}
					if(bounced) {
						((IEntity)entity).rlmixins$setLastBounceTick(entity.ticksExisted);
						event.setCanceled(true);
					}
				}
			}
		}
	}
}