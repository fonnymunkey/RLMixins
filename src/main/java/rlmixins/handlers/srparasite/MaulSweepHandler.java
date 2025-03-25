package rlmixins.handlers.srparasite;

import bettercombat.mod.event.RLCombatSweepEvent;
import com.dhanantry.scapeandrunparasites.init.SRPItems;
import com.dhanantry.scapeandrunparasites.item.tool.WeaponMeleeMaul;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MaulSweepHandler {

    // Strong Sweep on main target kill instead of Scythe clone
    // AABB Grow: 6(13) -> 3(4.5)
    // Sweep Damage Ratio: 2(5) -> 2.5(3.25) with Sweeping Edge III, Scaled to Sweeping Mod min 1.0F

    @SubscribeEvent
    public static void onSweepAttack(RLCombatSweepEvent event) {
        if(event.getItemStack().getItem() instanceof WeaponMeleeMaul) {
            boolean sentient = event.getItemStack().getItem() == SRPItems.weapon_maulSentient;
            if(event.getTargetEntity() instanceof EntityLivingBase &&
                ((EntityLivingBase) event.getTargetEntity()).getHealth() <= 0.0F) {
                event.setDoSweep(true);
                event.setSweepModifier(Math.max(event.getSweepModifier(), 1.0F + (sentient ? 3.0F : 2.0F) * Math.min(1.0F,event.getSweepModifier())));
                event.setSweepingAABB(event.getSweepingAABB().grow(sentient ? 4.5D : 3.0D));
                // Plays alongside regular sweep sfx
                event.getEntityPlayer().world.playSound(null, event.getEntityPlayer().posX, event.getEntityPlayer().posY, event.getEntityPlayer().posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, event.getEntityPlayer().getSoundCategory(), 1.0F, 0F);
            }
        }
    }
}
