package rlmixins.handlers.somanyenchantments;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.Shultrea.Rin.Enchantments_Sector.Smc_010;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.RLMixins;

public class ReviledBladeHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void modifyAttackDamagePost(RLCombatModifyDamageEvent.Post event) {
        if(event.getEntityPlayer() == null || event.getStack().isEmpty() || event.getTarget() == null || !(event.getTarget() instanceof EntityLivingBase)) return;

        EntityLivingBase target = (EntityLivingBase)event.getTarget();
        int level = EnchantmentHelper.getEnchantmentLevel(Smc_010.ReviledBlade, event.getStack());
        if(level > 0 && target.getMaxHealth() > 0) {
            float percent = 1.0F - Math.min(1.0F, Math.max(0.0F, target.getHealth() / target.getMaxHealth()));
            float mod = Math.max(0.0F, event.getBaseDamage() + event.getDamageModifier()) * percent * ((float)level/8.0F);
            event.setDamageModifier(event.getDamageModifier() + mod);
        }
    }
}