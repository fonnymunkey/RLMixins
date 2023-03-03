package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_4_0.Enchantmentflametier;
import com.Shultrea.Rin.Enchantment_Base_Sector.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Enchantmentflametier.class)
public abstract class EnchantmentFlameTierMixin extends EnchantmentBase {

    public EnchantmentFlameTierMixin(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }

    /**
     * @author fonnymunkey
     * @reason Make flame tier not applyable with vanilla flame
     */
    @Overwrite
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && !(ench instanceof Enchantmentflametier) && ench != Enchantments.FLAME;
    }
}