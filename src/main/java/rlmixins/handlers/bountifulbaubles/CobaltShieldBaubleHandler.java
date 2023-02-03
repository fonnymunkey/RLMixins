package rlmixins.handlers.bountifulbaubles;

import baubles.api.BaublesApi;
import cursedflames.bountifulbaubles.item.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CobaltShieldBaubleHandler {

    @SubscribeEvent
    public static void onLivingKnockback(LivingKnockBackEvent event) {
        if(event.getEntity() instanceof EntityPlayer) {
            if(BaublesApi.isBaubleEquipped((EntityPlayer)event.getEntity(), ModItems.shieldCobalt) != -1 ||
                    ((EntityPlayer)event.getEntity()).getHeldItemMainhand().getItem() == ModItems.shieldCobalt ||
                    ((EntityPlayer)event.getEntity()).getHeldItemOffhand().getItem() == ModItems.shieldCobalt) {
                event.setCanceled(true);
            }
        }
    }
}