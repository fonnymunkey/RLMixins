package rlmixins.handlers.somanyenchantments;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.Shultrea.Rin.Enchantments_Sector.Smc_030;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AshDestroyerHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void modifyAttackDamagePost(RLCombatModifyDamageEvent.Post event) {
        if(event.getEntityPlayer() == null || event.getStack().isEmpty() || event.getTarget() == null || !(event.getTarget() instanceof EntityLivingBase)) return;

        EntityLivingBase target = (EntityLivingBase)event.getTarget();
        int level = EnchantmentHelper.getEnchantmentLevel(Smc_030.AshDestroyer, event.getStack());
        if(level > 0) {
            if(target.isBurning()) {
                float damage = (event.getBaseDamage() + event.getDamageModifier()) * (float)level * 0.1F;//10% per level, 1.5x max
                event.setDamageModifier(event.getDamageModifier() + damage);
            }
        }
    }
}