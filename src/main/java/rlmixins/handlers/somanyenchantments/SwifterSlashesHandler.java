package rlmixins.handlers.somanyenchantments;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.Shultrea.Rin.Enchantments_Sector.Smc_010;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.RLMixins;

public class SwifterSlashesHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void modifyAttackDamagePost(RLCombatModifyDamageEvent.Post event) {
        if(event.getEntityPlayer() == null || event.getStack().isEmpty() || event.getTarget() == null || !(event.getTarget() instanceof EntityLivingBase)) return;

        EntityLivingBase target = (EntityLivingBase)event.getTarget();
        int level = EnchantmentHelper.getEnchantmentLevel(Smc_010.SwifterSlashes, event.getStack());
        if(level > 0 && event.getCooledStrength() > 0.9F && ((float)target.hurtResistantTime > (float)target.maxHurtResistantTime / 2.0F)) {
            if(target.world.rand.nextFloat() < 0.02F*(float)(level)) {
                target.hurtResistantTime = 0;
                event.setDamageModifier(event.getDamageModifier() - Math.max(((event.getBaseDamage() + event.getDamageModifier()) / 2.0F), 0));
            }
        }
    }
}