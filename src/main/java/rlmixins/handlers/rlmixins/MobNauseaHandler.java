package rlmixins.handlers.rlmixins;

import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.handlers.ConfigHandler;
import rlmixins.wrapper.IEntityLiving;

public class MobNauseaHandler {
	
	private static final float releaseSpeed = 0.05F;
	private static final float movementMult = 0.4F;
	private static final float chance = 0.05F;
	
	@SubscribeEvent
	public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
		if(!(event.getEntityLiving() instanceof EntityLiving)) return;
		EntityLiving entity = (EntityLiving)event.getEntityLiving();
		if(entity.world.isRemote || entity.isDead || entity.isRiding() || !entity.isNonBoss()) return;
		
		float forwardRemaining = ((IEntityLiving)entity).rlmixins$getForwardRemaining();
		float strafeRemaining = ((IEntityLiving)entity).rlmixins$getStrafeRemaining();
		
		if(forwardRemaining == 0.0F && strafeRemaining == 0.0F) {
			if((entity.moveForward != 0.0F || entity.moveStrafing != 0.0F) && entity.getRNG().nextFloat() < chance) {
				boolean effect = false;
				for(Potion potion : ConfigHandler.RLMIXINS_CONFIG.getNauseaMovementPotions()) {
					effect = entity.getActivePotionEffect(potion) != null;
					if(effect) break;
				}
				if(effect) {
					forwardRemaining = entity.getRNG().nextFloat() *  movementMult - movementMult / 2.0F;
					strafeRemaining = entity.getRNG().nextFloat() *  movementMult - movementMult / 2.0F;
				}
			}
		}
		if(forwardRemaining != 0.0F) {
			entity.setMoveForward(entity.moveForward + forwardRemaining);
			if(forwardRemaining > 0.0F) {
				forwardRemaining -= releaseSpeed;
				if(forwardRemaining < 0.0F) forwardRemaining = 0.0F;
			}
			else if(forwardRemaining < 0.0F) {
				forwardRemaining += releaseSpeed;
				if(forwardRemaining > 0.0F) forwardRemaining = 0.0F;
			}
			((IEntityLiving)entity).rlmixins$setForwardRemaining(forwardRemaining);
		}
		if(strafeRemaining != 0.0F) {
			entity.setMoveStrafing(entity.moveStrafing + strafeRemaining);
			if(strafeRemaining > 0.0F) {
				strafeRemaining -= releaseSpeed;
				if(strafeRemaining < 0.0F) strafeRemaining = 0.0F;
			}
			else if(strafeRemaining < 0.0F) {
				strafeRemaining += releaseSpeed;
				if(strafeRemaining > 0.0F) strafeRemaining = 0.0F;
			}
			((IEntityLiving)entity).rlmixins$setStrafeRemaining(strafeRemaining);
		}
	}
}