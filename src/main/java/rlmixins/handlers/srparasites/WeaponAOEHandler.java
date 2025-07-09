package rlmixins.handlers.srparasites;

import bettercombat.mod.event.RLCombatSweepEvent;
import com.dhanantry.scapeandrunparasites.init.SRPItems;
import com.dhanantry.scapeandrunparasites.item.tool.WeaponMeleeMaul;
import com.dhanantry.scapeandrunparasites.item.tool.WeaponMeleeScythe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WeaponAOEHandler {
	
	@SubscribeEvent
	public static void onSweepAttack(RLCombatSweepEvent event) {
		if(event.getItemStack().getItem() instanceof WeaponMeleeScythe) {
			boolean sentient = event.getItemStack().getItem() == SRPItems.weapon_scytheSentient;
			event.setDoSweep(true);
			if(event.getSweepModifier() < 1.0F) event.setSweepModifier(1.0F);
			event.setSweepingAABB(event.getSweepingAABB().grow(sentient ? 3 : 2));
		}
		else if(event.getItemStack().getItem() instanceof WeaponMeleeMaul) {
			boolean sentient = event.getItemStack().getItem() == SRPItems.weapon_maulSentient;
			if(event.getTargetEntity() instanceof EntityLivingBase && ((EntityLivingBase)event.getTargetEntity()).getHealth() <= 0.0F) {
				event.setDoSweep(true);
				event.setSweepModifier(Math.max(event.getSweepModifier(), 1.0F + (sentient ? 3.0F : 2.0F) * Math.min(1.0F, event.getSweepModifier())));
				event.setSweepingAABB(event.getSweepingAABB().grow(sentient ? 4.5D : 3.0D));
			}
		}
	}
}