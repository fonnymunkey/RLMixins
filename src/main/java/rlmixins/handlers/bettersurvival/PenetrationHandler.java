package rlmixins.handlers.bettersurvival;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.wrapper.SpartanWeaponryWrapper;

public class PenetrationHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void modifyAttackDamagePost(RLCombatModifyDamageEvent.Post event) {
        EntityPlayer player = event.getEntityPlayer();
        Entity target = event.getTarget();
        if(player == null || target == null || event.getStack().isEmpty()) return;

        int penLevel = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.penetration, event.getStack());
        if(penLevel > 0) {
            float penPercent = Math.min((float)penLevel / 10.0F, 1.0F);
            SpartanWeaponryWrapper.setEventPenetration(event, player, penPercent);
        }
    }
}