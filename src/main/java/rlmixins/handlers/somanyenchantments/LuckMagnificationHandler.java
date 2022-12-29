package rlmixins.handlers.somanyenchantments;

import bettercombat.mod.event.RLCombatCriticalHitEvent;
import com.Shultrea.Rin.Enchantments_Sector.Smc_040;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import rlmixins.RLMixins;

public class LuckMagnificationHandler {
    /**
     * Handle Luck Magnification enchantment in a non-broken and offhand-sensitive way
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onCritical(RLCombatCriticalHitEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if(player == null) return;
        ItemStack stack = event.getOffhand() ? player.getHeldItemOffhand() : player.getHeldItemMainhand();
        //Only trigger on an actual crit, not an attempted crit
        if(!stack.isEmpty() && (event.getResult() == Event.Result.ALLOW || (event.isVanillaCritical() && event.getResult() == Event.Result.DEFAULT))) {
            int lmLevel = EnchantmentHelper.getEnchantmentLevel(Smc_040.LuckMagnification, stack);
            if(lmLevel > 0) {
                IAttributeInstance luck = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.LUCK);
                float amount = (float)luck.getAttributeValue();

                if(amount != 0) {
                    if(player.world.rand.nextInt(100) < Math.abs(amount * (float)lmLevel)) {
                        event.setDamageModifier(event.getDamageModifier() + amount * (float)lmLevel * 0.1F);
                    }
                }
            }
        }
    }

    /**
     * Handle looting modifier from Luck Magnification
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLootingLevel(LootingLevelEvent event) {
        if(event.getDamageSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.getDamageSource().getTrueSource();
            //Not ideal, but event doesn't post the stack
            int level = EnchantmentHelper.getEnchantmentLevel(Smc_040.LuckMagnification, player.getHeldItemMainhand());
            level = Math.min(2, level + EnchantmentHelper.getEnchantmentLevel(Smc_040.LuckMagnification, player.getHeldItemOffhand()));
            if(level > 0) {
                IAttributeInstance luck = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.LUCK);
                int modifier = (int)((double)event.getLootingLevel() + luck.getAttributeValue() * (double)level / 2.0D);
                event.setLootingLevel(modifier);
            }
        }
    }

    /**
     * Handle Luck Magnification applying the luck effect
     */
    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if(event.player != null &&
                event.phase != TickEvent.Phase.START &&
                !event.player.world.isRemote &&
                event.player.ticksExisted%9 == 0) {
            int level = EnchantmentHelper.getEnchantmentLevel(Smc_040.LuckMagnification, event.player.getHeldItemMainhand());
            level = Math.min(2, level + EnchantmentHelper.getEnchantmentLevel(Smc_040.LuckMagnification, event.player.getHeldItemOffhand()));
            if(level > 0) {
                event.player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 10, level - 1, true, false));
            }
        }
    }
}
