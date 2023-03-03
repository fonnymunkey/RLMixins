package rlmixins.handlers.somanyenchantments;

import bettercombat.mod.event.RLCombatSweepEvent;
import com.Shultrea.Rin.Enchantments_Sector.Smc_040;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.RLMixins;

public class ArcSlashHandler {

    /**
     * Rehandle Arc Slash enchant
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onSweepAttack(RLCombatSweepEvent event) {
        int level = EnchantmentHelper.getEnchantmentLevel(Smc_040.Swiper, event.getItemStack());
        if(level > 0 && event.getEntityPlayer() != null && event.getTargetEntity() != null) {
            event.setDoSweep(true);
            event.setSweepModifier(Math.max(event.getSweepModifier(), level * 0.20F));
        }
    }
}