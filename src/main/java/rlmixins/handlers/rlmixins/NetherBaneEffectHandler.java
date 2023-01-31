package rlmixins.handlers.rlmixins;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.handlers.ForgeConfigHandler;

public class NetherBaneEffectHandler {

    @SubscribeEvent
    public static void modifyAttackDamagePre(RLCombatModifyDamageEvent.Pre event) {
        if(ForgeConfigHandler.server.netherBaneMobs.length <= 0 || ForgeConfigHandler.server.netherBaneWeapons.length <= 0) return;

        EntityPlayer player = event.getEntityPlayer();
        Entity target = event.getTarget();
        if(player == null || target == null || event.getStack().isEmpty()) return;

        ResourceLocation weaponResource = event.getStack().getItem().getRegistryName();
        ResourceLocation mobResource = EntityList.getKey(target);
        if(weaponResource != null && mobResource != null && ForgeConfigHandler.getNetherBaneWeapons().contains(weaponResource.toString()) && ForgeConfigHandler.getNetherBaneMobs().contains(mobResource.toString())) {
            if(ForgeConfigHandler.server.netherBaneMultiply) event.setDamageModifier(event.getDamageModifier() * (float)ForgeConfigHandler.server.netherBaneValue);
            else event.setDamageModifier(event.getDamageModifier() + (float)ForgeConfigHandler.server.netherBaneValue);
        }
    }
}