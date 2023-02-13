package rlmixins.handlers.srparasite;

import bettercombat.mod.event.RLCombatSweepEvent;
import com.dhanantry.scapeandrunparasites.init.SRPItems;
import com.dhanantry.scapeandrunparasites.item.tool.WeaponMeleeScythe;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ScytheSweepHandler {

    @SubscribeEvent
    public static void onSweepAttack(RLCombatSweepEvent event) {
        if(event.getItemStack().getItem() instanceof WeaponMeleeScythe) {
            boolean sentient = event.getItemStack().getItem() == SRPItems.weapon_scytheSentient;
            event.setDoSweep(true);
            if(event.getSweepModifier() < 1.0F) event.setSweepModifier(1.0F);
            event.setSweepingAABB(event.getSweepingAABB().grow(sentient ? 3 : 2));
        }
    }
}
