package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_2_0.EnchantmentParry;
import com.Shultrea.Rin.Enchantments_Sector.Smc_020;
import com.Shultrea.Rin.Utility_Sector.EnchantmentsUtility;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EnchantmentParry.class)
public abstract class EnchantmentParryMixin {

    @SubscribeEvent(priority = EventPriority.LOW)
    public void HandleEnchant(LivingAttackEvent e) {
        if(e.getEntity() instanceof EntityLivingBase) {
            if(e.getSource().getImmediateSource() instanceof EntityLivingBase) {
                EntityLivingBase victim = e.getEntityLiving();
                if(victim != null) {
                    EntityLivingBase attacker = (EntityLivingBase)e.getSource().getImmediateSource();
                    if(attacker != null) {
                        int level = EnchantmentHelper.getEnchantmentLevel(Smc_020.Parry, victim.getHeldItemMainhand());
                        if(level <= 0) level = EnchantmentHelper.getEnchantmentLevel(Smc_020.Parry, victim.getHeldItemOffhand());
                        if(level > 0) {
                            if(victim.world.rand.nextInt(100) < 16 + level*8) {
                                if(!victim.world.isRemote) {
                                    EnchantmentsUtility.ImprovedKnockBack(
                                            attacker, 0.3F + 0.15F*(float)level, victim.posX - attacker.posX, victim.posZ - attacker.posZ
                                    );
                                }

                                victim.world.playSound(
                                        null,
                                        attacker.posX,
                                        attacker.posY,
                                        attacker.posZ,
                                        SoundEvents.BLOCK_ANVIL_PLACE,
                                        SoundCategory.PLAYERS,
                                        0.3F,
                                        3.0F
                                );
                                e.setCanceled(true);
                                victim.hurtResistantTime = victim.maxHurtResistantTime - 5;
                            }
                        }
                    }
                }
            }
        }
    }
}