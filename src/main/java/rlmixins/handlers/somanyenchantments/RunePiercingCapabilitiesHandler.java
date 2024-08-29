package rlmixins.handlers.somanyenchantments;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.Shultrea.Rin.Enchantments_Sector.Smc_010;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.util.ModLoadedUtil;
import rlmixins.wrapper.SpartanWeaponryWrapper;

public class RunePiercingCapabilitiesHandler {

    /**
     * Handle Rune Piercing Capabilities properly
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void modifyAttackDamagePost(RLCombatModifyDamageEvent.Post event) {
        EntityPlayer player = event.getEntityPlayer();
        Entity target = event.getTarget();
        if(player == null || target == null || event.getStack().isEmpty()) return;
        if(!ModLoadedUtil.isSpartanWeaponryLoaded()) return;

        int pierceLevel = EnchantmentHelper.getEnchantmentLevel(Smc_010.Rune_PiercingCapabilities, event.getStack());
        if(pierceLevel > 0) {
            float piercePercent = (float)pierceLevel/4.0F;
            SpartanWeaponryWrapper.setEventPenetration(event, player, piercePercent);
        }
    }
}