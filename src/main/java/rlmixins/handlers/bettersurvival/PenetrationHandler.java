package rlmixins.handlers.bettersurvival;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import com.oblivioussp.spartanweaponry.util.EntityDamageSourceArmorPiercing;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PenetrationHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void modifyAttackDamagePost(RLCombatModifyDamageEvent.Post event) {
        EntityPlayer player = event.getEntityPlayer();
        Entity target = event.getTarget();
        if(player == null || target == null || event.getStack().isEmpty()) return;

        int penLevel = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.penetration, event.getStack());
        if(penLevel > 0) {
            float penPercent = Math.min((float)penLevel / 10.0F, 1.0F);
            if(event.getDamageSource() instanceof EntityDamageSourceArmorPiercing) {
                penPercent += ((EntityDamageSourceArmorPiercing)event.getDamageSource()).getPercentage();
            }
            event.setDamageSource(new EntityDamageSourceArmorPiercing("player", player, Math.min(penPercent, 1.0F)));
        }
    }
}