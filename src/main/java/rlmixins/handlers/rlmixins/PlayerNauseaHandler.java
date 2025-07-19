package rlmixins.handlers.rlmixins;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.handlers.ConfigHandler;

public class PlayerNauseaHandler {
	
	private static float forwardRemaining = 0.0F;
	private static float strafeRemaining = 0.0F;
	private static final float releaseSpeed = 0.1F;
	private static final float movementMult = 4.0F;
	private static final float chance = 0.1F;
	
	//Minor feature so not worth the packets to ensure from serverside instead
	@SubscribeEvent
	public static void onInputUpdateEvent(InputUpdateEvent event) {
		if(!(event.getEntityPlayer() instanceof EntityPlayerSP)) return;
		EntityPlayerSP playerSP = (EntityPlayerSP)event.getEntityPlayer();
		if(playerSP.isRiding()) return;
		MovementInput input = event.getMovementInput();
		if(input == null) return;
		
		if(forwardRemaining == 0.0F && strafeRemaining == 0.0F) {
			if((input.moveForward != 0.0F || input.moveStrafe != 0.0F) && playerSP.getRNG().nextFloat() < chance) {
				boolean effect = false;
				for(Potion potion : ConfigHandler.RLMIXINS_CONFIG.getNauseaMovementPotions()) {
					effect = playerSP.getActivePotionEffect(potion) != null;
					if(effect) break;
				}
				if(effect) {
					forwardRemaining = playerSP.getRNG().nextFloat() *  movementMult - movementMult / 2.0F;
					strafeRemaining = playerSP.getRNG().nextFloat() *  movementMult - movementMult / 2.0F;
				}
			}
		}
		if(forwardRemaining != 0.0F) {
			input.moveForward += forwardRemaining;
			if(forwardRemaining > 0.0F) {
				forwardRemaining -= releaseSpeed;
				if(forwardRemaining < 0.0F) forwardRemaining = 0.0F;
			}
			else if(forwardRemaining < 0.0F) {
				forwardRemaining += releaseSpeed;
				if(forwardRemaining > 0.0F) forwardRemaining = 0.0F;
			}
		}
		if(strafeRemaining != 0.0F) {
			input.moveStrafe += strafeRemaining;
			if(strafeRemaining > 0.0F) {
				strafeRemaining -= releaseSpeed;
				if(strafeRemaining < 0.0F) strafeRemaining = 0.0F;
			}
			else if(strafeRemaining < 0.0F) {
				strafeRemaining += releaseSpeed;
				if(strafeRemaining > 0.0F) strafeRemaining = 0.0F;
			}
		}
	}
}