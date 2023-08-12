package rlmixins.mixin.vanilla;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EnchantmentProtection.class)
public abstract class EnchantmentProtectionMixin {

    /**
     * @author fonnymunkey
     * @reason fix blast protection flooring reduction making it not effective
     */
    @Overwrite
    public static double getBlastDamageReduction(EntityLivingBase entityLivingBaseIn, double damage) {
        int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.BLAST_PROTECTION, entityLivingBaseIn);

        if(i > 0) damage -= damage * Math.min(1D, (double)((float)i * 0.15F));

        return damage;
    }
}