package thishasmixins.handlers.quark;

import baubles.api.BaublesApi;
import vazkii.quark.world.feature.PirateShips;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PirateHatHandler {

    @SubscribeEvent
    public static void onLootingLevelEvent(LootingLevelEvent event) {
        EntityLivingBase attacker = (EntityLivingBase)event.getDamageSource().getTrueSource();
        if(!(attacker instanceof EntityPlayer)) return;

        if(attacker.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == PirateShips.pirate_hat ||
                BaublesApi.isBaubleEquipped((EntityPlayer)attacker, PirateShips.pirate_hat) != -1)
            event.setLootingLevel(event.getLootingLevel() + 1);
    }
}
