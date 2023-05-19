package rlmixins.handlers.charm;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import svenhjol.charm.enchanting.feature.Magnetic;

public class MagneticHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockHarvest(BlockEvent.HarvestDropsEvent event) {
        EntityPlayer player = event.getHarvester();
        if(player != null && EnchantmentHelper.getEnchantmentLevel(Magnetic.enchantment, player.getHeldItemMainhand()) > 0) {
            //Manually handle drop chance so drops dont get duped, may cause issues if something expects to ignore drop chance?
            event.getDrops().removeIf(s -> event.getWorld().rand.nextFloat() > event.getDropChance() || player.addItemStackToInventory(s));
            event.setDropChance(1.0F);//If anything passed the check, but couldn't be added, make sure it drops
        }
    }
}