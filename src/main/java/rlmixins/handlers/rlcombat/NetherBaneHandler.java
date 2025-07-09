package rlmixins.handlers.rlcombat;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.handlers.ConfigHandler;

public class NetherBaneHandler {
	
	@SubscribeEvent
	public static void modifyAttackDamagePre(RLCombatModifyDamageEvent.Pre event) {
		if(ConfigHandler.RLCOMBAT_CONFIG.netherBaneMobs.length == 0 || ConfigHandler.RLCOMBAT_CONFIG.netherBaneWeapons.length == 0) return;
		
		EntityPlayer player = event.getEntityPlayer();
		Entity target = event.getTarget();
		if(player == null || target == null || event.getStack().isEmpty()) return;
		
		ResourceLocation weaponResource = event.getStack().getItem().getRegistryName();
		ResourceLocation mobResource = EntityList.getKey(target);
		if(weaponResource != null && mobResource != null && ConfigHandler.RLCOMBAT_CONFIG.getNetherBaneWeapons().contains(weaponResource) && ConfigHandler.RLCOMBAT_CONFIG.getNetherBaneMobs().contains(mobResource)) {
			if(ConfigHandler.RLCOMBAT_CONFIG.netherBaneMultiply) event.setDamageModifier((event.getBaseDamage() + event.getDamageModifier()) * (float)ConfigHandler.RLCOMBAT_CONFIG.netherBaneValue);
			else event.setDamageModifier(event.getDamageModifier() + (float)ConfigHandler.RLCOMBAT_CONFIG.netherBaneValue);
		}
	}
}