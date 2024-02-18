package rlmixins.handlers.somanyenchantments;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.Shultrea.Rin.Enchantments_Sector.Smc_010;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CursedEdgeHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void modifyAttackDamagePost(RLCombatModifyDamageEvent.Post event) {
        if(event.getEntityPlayer() == null || event.getStack().isEmpty() || event.getTarget() == null || !(event.getTarget() instanceof EntityLivingBase)) return;

        EntityPlayer player = event.getEntityPlayer();
        int level = EnchantmentHelper.getEnchantmentLevel(Smc_010.CursedEdge, event.getStack());
        if(level > 0) {
            float damage = (event.getBaseDamage() + event.getDamageModifier()) * (float)level * (0.1F + 0.1F*player.world.rand.nextFloat());//10%-20% per level, 1.5x-2x max
            if(damage > 0) {
                event.setDamageModifier(event.getDamageModifier() + damage);
                player.attackEntityFrom(DamageSource.MAGIC, (event.getBaseDamage() + event.getDamageModifier()) * (((float)level)/40.0F));//2.5% self per level, 12.5% max
            }
        }
    }
}