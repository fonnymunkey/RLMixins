package rlmixins.handlers.srparasite;

import com.dhanantry.scapeandrunparasites.init.SRPPotions;
import com.dhanantry.scapeandrunparasites.item.tool.WeaponToolArmorBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import rlmixins.handlers.ForgeConfigHandler;

public class ArmorEffectHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.player == null || event.phase != TickEvent.Phase.END) return;
        EntityPlayer player = event.player;
        PotionEffect viral = player.getActivePotionEffect(SRPPotions.VIRA_E);
        PotionEffect fear = player.getActivePotionEffect(SRPPotions.FEAR_E);
        if(viral != null && ForgeConfigHandler.server.parasiteArmorViralCuring || fear != null && ForgeConfigHandler.server.parasiteArmorFearCuring) {
            for(ItemStack stack : player.getArmorInventoryList())  {
                if(!(stack.getItem() instanceof WeaponToolArmorBase)) return;
            }
            if(viral != null && ForgeConfigHandler.server.parasiteArmorViralCuring) removeAndLimit(player, viral, ForgeConfigHandler.server.parasiteArmorViralMax);
            if(fear != null && ForgeConfigHandler.server.parasiteArmorFearCuring) removeAndLimit(player, fear, ForgeConfigHandler.server.parasiteArmorFearMax);
        }
    }

    private static void removeAndLimit(EntityPlayer player, PotionEffect previous, int maxAmp) {
        if(maxAmp >= previous.getAmplifier()) return;
        player.removeActivePotionEffect(previous.getPotion());
        if(maxAmp != -1) player.addPotionEffect(new PotionEffect(previous.getPotion(), previous.getDuration(), maxAmp, previous.getIsAmbient(), previous.doesShowParticles()));
    }
}
