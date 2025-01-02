package rlmixins.handlers.artifacts;

import artifacts.common.init.ModItems;
import artifacts.common.util.BaubleHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BaubleAntidoteHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingHurt(LivingHurtEvent event){
        EntityLivingBase victim = event.getEntityLiving();
        if(!(victim instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) victim;

        int antidotesEquipped = BaubleHelper.getAmountBaubleEquipped(player, ModItems.ANTIDOTE_VESSEL);
        if (antidotesEquipped <= 0) return;

        ModItems.ANTIDOTE_VESSEL.onWornTick(ItemStack.EMPTY, player);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingDamage(LivingDamageEvent event){
        EntityLivingBase victim = event.getEntityLiving();
        if(!(victim instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) victim;

        int antidotesEquipped = BaubleHelper.getAmountBaubleEquipped(player, ModItems.ANTIDOTE_VESSEL);
        if (antidotesEquipped <= 0) return;

        ModItems.ANTIDOTE_VESSEL.onWornTick(ItemStack.EMPTY, player);
    }
}