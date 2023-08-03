package rlmixins.wrapper;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.oblivioussp.spartanweaponry.util.EntityDamageSourceArmorPiercing;
import net.minecraft.entity.player.EntityPlayer;

public abstract class SpartanWeaponryWrapper {

    public static void setEventPenetration(RLCombatModifyDamageEvent.Post event, EntityPlayer player, float percent) {
        if(event.getDamageSource() instanceof EntityDamageSourceArmorPiercing) {
            percent += ((EntityDamageSourceArmorPiercing)event.getDamageSource()).getPercentage();
        }
        event.setDamageSource(new EntityDamageSourceArmorPiercing("player", player, Math.min(percent, 1.0F)));
    }
}