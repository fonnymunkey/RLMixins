package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_4_0.EnchantmentFieryShield;
import com.Shultrea.Rin.Enchantments_Sector.Smc_040;
import com.Shultrea.Rin.Utility_Sector.EnchantmentsUtility;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EnchantmentFieryShield.class)
public abstract class EnchantmentFieryShieldMixin {

    /**
     * @author fonnymunkey
     * @reason tweak fiery shield to work properly
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    @Overwrite(remap = false)
    public void shieldBurn(LivingAttackEvent event) {
        if(event.getEntity() instanceof EntityLivingBase) {
            EntityLivingBase victim = (EntityLivingBase)event.getEntity();
            ItemStack shield = victim.getHeldItemMainhand();
            if(shield.isEmpty() || !shield.getItem().isShield(shield, victim)) {
                shield = victim.getHeldItemOffhand();
                if(shield.isEmpty()) return;
            }

            if(EnchantmentHelper.getEnchantmentLevel(Smc_040.fieryshield, shield) > 0) {
                int levelfs = EnchantmentHelper.getEnchantmentLevel(Smc_040.fieryshield, shield);
                Entity attacker = event.getSource().getImmediateSource();
                if(shield.getItem().isShield(shield, victim)
                        && attacker != null
                        && event.getEntityLiving().world.rand.nextInt(100) < 40 + levelfs * 10
                        && EnchantmentsUtility.canBlockDamageSource(event.getSource(), victim)) {
                    attacker.attackEntityFrom(new EntityDamageSource("player", victim).setFireDamage(), event.getAmount() * (float)levelfs * 0.1F);
                    attacker.setFire(4 + levelfs * 2);
                }
            }
        }
    }
}