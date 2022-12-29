package rlmixins.handlers.somanyenchantments;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.Shultrea.Rin.Enchantments_Sector.Smc_010;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.RLMixins;

public class WaterAspectHandler {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void modifyAttackDamagePre(RLCombatModifyDamageEvent.Pre event) {
        if(event.getEntityPlayer() == null || event.getStack().isEmpty() || event.getTarget() == null || !(event.getTarget() instanceof EntityLivingBase)) return;

        EntityLivingBase target = (EntityLivingBase)event.getTarget();
        int level = EnchantmentHelper.getEnchantmentLevel(Smc_010.WaterAspect, event.getStack());
        if(level > 0) {
            if(target instanceof EntityEnderman || target instanceof EntityBlaze || target instanceof EntityMagmaCube) {
                event.setDamageModifier(event.getDamageModifier() + (float)level*1.5F);
            }
            if(target.isInWater() || target.isWet()) {
                event.setDamageModifier(event.getDamageModifier() + (float)level*0.75F);
            }
            if(event.getEntityPlayer().isInWater() || event.getEntityPlayer().isWet()) {
                event.setDamageModifier(event.getDamageModifier() + (float)level*0.75F);
            }
        }
    }
}