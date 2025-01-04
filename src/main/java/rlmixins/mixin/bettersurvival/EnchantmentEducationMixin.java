package rlmixins.mixin.bettersurvival;

import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentEducation;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import rlmixins.util.ModLoadedUtil;
import rlmixins.wrapper.BlightWrapper;

@Mixin(EnchantmentEducation.class)
public abstract class EnchantmentEducationMixin extends Enchantment {

    protected EnchantmentEducationMixin(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }

    /**
     * @author fonnymunkey
     * @reason make education not work with looting
     */
    @Overwrite
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.LOOTING;
    }

    /**
     * @author fonnymunkey
     * @reason tweak values and blacklist blights
     */
    @Overwrite(remap = false)
    public static float getExpMultiplyer(EntityPlayer killer, EntityLivingBase killed) {
        if(killer != null && killed != null && killed.isNonBoss() && (!ModLoadedUtil.isScalingHealthLoaded() || !BlightWrapper.isEntityBlight(killed))) {
            int level = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.education, killer);
            if(level > 0) return 1.0F + 0.3F*(float)level;
        }
        return 1.0F;
    }
}