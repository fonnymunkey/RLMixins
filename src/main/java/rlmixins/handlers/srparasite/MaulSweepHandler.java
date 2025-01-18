package rlmixins.handlers.srparasite;

import bettercombat.mod.event.RLCombatSweepEvent;
import com.dhanantry.scapeandrunparasites.init.SRPItems;
import com.dhanantry.scapeandrunparasites.item.tool.WeaponMeleeMaul;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MaulSweepHandler {

    @SubscribeEvent
    public static void onSweepAttack(RLCombatSweepEvent event) {
        if(event.getItemStack().getItem() instanceof WeaponMeleeMaul) {
            boolean sentient = event.getItemStack().getItem() == SRPItems.weapon_maulSentient;
            event.setDoSweep(true);
            if(event.getSweepModifier() < 1.0F) event.setSweepModifier(1.0F);
            event.setSweepingAABB(event.getSweepingAABB().grow(sentient ? 3 : 2));
        }
    }
}
