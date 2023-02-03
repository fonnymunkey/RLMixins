package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_2_0.EnchantmentCulling;
import com.Shultrea.Rin.Enchantment_Base_Sector.EnchantmentBase;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EnchantmentCulling.class)
public abstract class EnchantmentCullingMixin extends EnchantmentBase {


    public EnchantmentCullingMixin(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public boolean canApply(ItemStack fTest) {
        return super.canApply(fTest);
    }
}