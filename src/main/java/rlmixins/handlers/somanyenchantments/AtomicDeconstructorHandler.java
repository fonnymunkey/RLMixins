package rlmixins.handlers.somanyenchantments;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.Shultrea.Rin.Enchantments_Sector.Smc_040;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.handlers.ForgeConfigHandler;
import rlmixins.handlers.ModRegistry;

public class AtomicDeconstructorHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void modifyAttackDamagePost(RLCombatModifyDamageEvent.Post event) {
        if(event.getEntityPlayer() == null || event.getStack().isEmpty() || event.getTarget() == null || !(event.getTarget() instanceof EntityLivingBase)) return;

        EntityLivingBase target = (EntityLivingBase)event.getTarget();
        int level = EnchantmentHelper.getEnchantmentLevel(Smc_040.AtomicDeconstructor, event.getStack());
        if(level > 0 && event.getCooledStrength() > 0.9F && (target.isNonBoss() || ForgeConfigHandler.server.atomicDeconstructorBosses)) {
            if(target.world.rand.nextFloat() < 0.001F*(float)level) {
                target.hurtResistantTime = 0;
                if(!ForgeConfigHandler.server.atomicDeconstructorMaxDamage || !target.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE)) {
                    event.setDamageModifier(target.getMaxHealth() * 100.0F);
                }
                event.getEntityPlayer().world.playSound(null, event.getEntityPlayer().posX, event.getEntityPlayer().posY, event.getEntityPlayer().posZ, ModRegistry.ATOMIC_DECONSTRUCT, SoundCategory.PLAYERS, 2.0F, 1.0F /(event.getEntityPlayer().world.rand.nextFloat() * 0.4F + 1.2F)* 1.4F);
            }
        }
    }
}