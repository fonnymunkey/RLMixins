package rlmixins.handlers.somanyenchantments;

import com.Shultrea.Rin.Enchantments_Sector.Smc_040;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.RLMixins;

public class CurseOfPossesionHandler {

    /**
     * Reimplement Curse of Possession handler in a non-laggy and buggy way
     */
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if(event.isCanceled() || event.getEntity() == null || !(event.getEntity() instanceof EntityItem)) return;

        EntityItem itemEntity = (EntityItem)event.getEntity();
        ItemStack stack = itemEntity.getItem();

        if(EnchantmentHelper.getEnchantmentLevel(Smc_040.CurseofPossession, stack) > 0) {//Remove setting the item to never despawn, thats stupid, its a Curse
            EntityPlayer thrower = event.getWorld().getClosestPlayerToEntity(itemEntity, 8.0);//32 is way too large of a radius to check
            if(thrower != null && !thrower.isCreative()) {
                if(thrower.isEntityAlive()) {
                    if(thrower.addItemStackToInventory(stack)) {
                        event.setCanceled(true);
                        return;
                    }
                }
                itemEntity.lifespan = 5;//Delete item on drop if player isnt null but dead or cant be added to inventory
                itemEntity.setPickupDelay(10);
            }
        }
    }
}