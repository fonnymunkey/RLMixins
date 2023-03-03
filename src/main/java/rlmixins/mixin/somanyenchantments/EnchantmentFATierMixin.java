package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_4_0.EnchantmentFAtier;
import com.Shultrea.Rin.Enchantment_Base_Sector.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EnchantmentFAtier.class)
public abstract class EnchantmentFATierMixin extends EnchantmentBase {

    public EnchantmentFATierMixin(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }

    /**
     * @author fonnymunkey
     * @reason Make fire aspect tier not applyable with vanilla fire aspect
     */
    @Overwrite
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && !(ench instanceof EnchantmentFAtier) && ench != Enchantments.FIRE_ASPECT;
    }
}