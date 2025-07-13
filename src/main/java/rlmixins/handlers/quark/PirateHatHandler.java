package rlmixins.handlers.quark;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.util.ModLoadedUtil;
import rlmixins.wrapper.BaublesWrapper;
import vazkii.quark.world.feature.PirateShips;

/**
 * By cdstk
 */
public class PirateHatHandler {

    @SubscribeEvent
    public static void onLootingLevelEvent(LootingLevelEvent event) {
        if(!(event.getDamageSource().getTrueSource() instanceof EntityPlayer)) return;

        EntityPlayer attacker = (EntityPlayer)event.getDamageSource().getTrueSource();

        if(attacker.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == PirateShips.pirate_hat ||
                (ModLoadedUtil.isBaublesLoaded() && BaublesWrapper.isBaubleEquipped(attacker, PirateShips.pirate_hat))) {
            event.setLootingLevel(event.getLootingLevel() + 1);
        }
    }
}