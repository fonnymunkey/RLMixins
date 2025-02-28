package rlmixins.handlers.artifacts;

import artifacts.common.ModConfig;
import artifacts.common.init.ModItems;
import artifacts.common.util.BaubleHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

public class BaubleAntidoteHandler {

    /*
     *  Instead of checking every hurt event
     *  Only does amplifier because duration gonna be reduced by player tick handling
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void antidoteOnApply(PotionEvent.PotionApplicableEvent event){
        if(!(event.getEntityLiving() instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        if(player.getEntityWorld().isRemote) return;

        int antidotesEquipped = BaubleHelper.getAmountBaubleEquipped(player, ModItems.ANTIDOTE_VESSEL);
        if (antidotesEquipped <= 0) return;

        PotionEffect effect = event.getPotionEffect();
        if (effect.getPotion().isBadEffect() &&
                !Arrays.asList(ModConfig.general.vesselBlacklist).contains(effect.getPotion().getRegistryName().toString()) &&
                (effect.getAmplifier() > ModConfig.general.vesselMaxAmplifier)) {
            event.setResult(Event.Result.DENY);
            player.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration(), Math.min(effect.getAmplifier(), ModConfig.general.vesselMaxAmplifier), effect.getIsAmbient(), effect.doesShowParticles()));
        }
    }
}