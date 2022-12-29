package rlmixins.handlers.bountifulbaubles;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import cursedflames.bountifulbaubles.baubleeffect.IFireResistance;
import cursedflames.bountifulbaubles.item.ItemShieldObsidian;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FireResistanceBaubleHandler {

    /**
     * Reimplement BountifulBaubles fire resistance trinkets to actually work properly
     */
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onLivingHurt(LivingHurtEvent event) {
        if(event.getEntityLiving() instanceof EntityPlayer &&
                event.getSource().isFireDamage() &&
                !event.getEntityLiving().world.isRemote &&
                !event.getSource().equals(DamageSource.LAVA)) {
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();

            float damageMulti = 1.0F;

            IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
            List<UUID> found = new ArrayList<>();

            for(int i = -2; i < baubles.getSlots(); ++i) {
                ItemStack stack = i >= 0 ? baubles.getStackInSlot(i) : (i == -2 ? player.getHeldItemMainhand() : player.getHeldItemOffhand());
                if(stack.getItem() instanceof IFireResistance
                        && !found.contains(((IFireResistance)stack.getItem()).getFireResistID())
                        && (i >= 0 || stack.getItem() instanceof ItemShieldObsidian)) {
                    IFireResistance fireResist = (IFireResistance)stack.getItem();
                    found.add(fireResist.getFireResistID());
                    damageMulti *= Math.max(1.0 - fireResist.getResistance(), 0.0);
                }
            }
            event.setAmount(event.getAmount() * damageMulti);

            if(player.isBurning() && damageMulti != 1.0F) player.extinguish();
        }
    }
}