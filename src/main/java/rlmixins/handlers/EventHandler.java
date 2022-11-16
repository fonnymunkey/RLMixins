package rlmixins.handlers;

import com.Shultrea.Rin.Ench0_3_0.EnchantmentCriticalStrike;
import cursedflames.bountifulbaubles.item.ModItems;
import ichttt.mods.firstaid.FirstAid;
import ichttt.mods.firstaid.api.CapabilityExtendedHealthSystem;
import ichttt.mods.firstaid.api.damagesystem.AbstractDamageablePart;
import ichttt.mods.firstaid.api.damagesystem.AbstractPlayerDamageModel;
import ichttt.mods.firstaid.api.event.FirstAidLivingDamageEvent;
import ichttt.mods.firstaid.common.network.MessageUpdatePart;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.RLMixins;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = RLMixins.MODID)
public class EventHandler {

/*
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onFirstAidLivingDamage(FirstAidLivingDamageEvent event) {
        if(event.getEntityPlayer()==null || event.isCanceled() || event.getEntityPlayer().world.isRemote) return;
        if(baubles.api.BaublesApi.isBaubleEquipped(event.getEntityPlayer(), ModItems.trinketBrokenHeart)==-1) return;

        boolean saved = false;
        for(AbstractDamageablePart part : event.getAfterDamage()) {//Iterate parts
            if(part.canCauseDeath && part.currentHealth <= 0) {//Only care about crucial parts, and only if it would kill the player
                if((int)(part.getMaxHealth() - (Math.max(2, event.getUndistributedDamage()))) < 2) return;//Let the player die if they don't have enough heart containers to save them

                part.setMaxHealth((int)(part.getMaxHealth() - Math.max(2, event.getUndistributedDamage())));//Reduce max health by atleast 1, plus undistributed (undistributed doesn't really work properly)
                part.heal(2, null, false);//Heal the dead part to a single heart

                if(event.getEntityPlayer() instanceof EntityPlayerMP) FirstAid.NETWORKING.sendTo(new MessageUpdatePart(part), (EntityPlayerMP)event.getEntityPlayer());
                saved = true;
            }
        }
        if(saved) {
            event.getEntityPlayer().world.playSound(null, event.getEntityPlayer().posX, event.getEntityPlayer().posY, event.getEntityPlayer().posZ,
                    SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 0.7F, (event.getEntityPlayer().world.rand.nextFloat()-event.getEntityPlayer().world.rand.nextFloat())*0.1F+0.8F);
        }
    }

    @SubscribeEvent
    public static void onPlayerWake(PlayerWakeUpEvent event) {
        if(event.getEntityPlayer()==null || event.getEntityPlayer().world.isRemote || event.getEntityPlayer() instanceof FakePlayer) return;
        AbstractPlayerDamageModel damageModel = Objects.requireNonNull(event.getEntityPlayer().getCapability(CapabilityExtendedHealthSystem.INSTANCE, null));

        for(AbstractDamageablePart part : damageModel) {
            if(part.canCauseDeath && part.getMaxHealth()<part.initialMaxHealth) {
                part.setMaxHealth(part.initialMaxHealth);
                if(event.getEntityPlayer() instanceof EntityPlayerMP) FirstAid.NETWORKING.sendTo(new MessageUpdatePart(part), (EntityPlayerMP)event.getEntityPlayer());
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onFirstAidLivingDamage(FirstAidLivingDamageEvent event) {
        if (event.getEntityPlayer() == null || event.isCanceled() || event.getEntityPlayer().world.isRemote) return;
        if (baubles.api.BaublesApi.isBaubleEquipped(event.getEntityPlayer(), ModItems.trinketBrokenHeart) == -1) return;

        for(AbstractDamageablePart part : event.getAfterDamage()) {//Iterate parts
            if(part.canCauseDeath && part.currentHealth <= 0) {//Only care about crucial parts, and only if it would kill the player
                if((int)(part.getMaxHealth() - (Math.max(2, event.getUndistributedDamage()))) < 2) return;//Let the player die if they don't have enough heart containers to save them

                part.setMaxHealth((int)(part.getMaxHealth() - Math.max(2, event.getUndistributedDamage())));//Reduce max health by atleast 1, plus undistributed (undistributed doesn't really work properly)
                part.heal(2, null, false);//Heal the dead part to a single heart

                if(event.getEntityPlayer() instanceof EntityPlayerMP) FirstAid.NETWORKING.sendTo(new MessageUpdatePart(part), (EntityPlayerMP)event.getEntityPlayer());
                saved = true;
            }
        }
    }

     */
}