package rlmixins.mixin.lycanitesmobs;

import com.lycanitesmobs.core.item.ItemBase;
import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import com.lycanitesmobs.core.item.equipment.ItemEquipmentPart;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import rlmixins.handlers.ConfigHandler;

import javax.annotation.Nonnull;

/**
 * By cdstk
 */
@Mixin(ItemEquipment.class)
public abstract class ItemEquipment_EnchantMixin extends ItemBase {

    @Shadow(remap = false) public abstract NonNullList<ItemStack> getEquipmentPartStacks(ItemStack itemStack);
    
    @Shadow(remap = false) public abstract ItemEquipmentPart getEquipmentPart(ItemStack itemStack);

    @Override
    public boolean isEnchantable(@Nonnull ItemStack stack) {
        return true;
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }

    //used by both Enchantment Table and Anvil
    //super call is important for SME
    @Override
    public boolean canApplyAtEnchantingTable(@Nonnull ItemStack stack, @Nonnull Enchantment enchantment) {
        if(!rlmixins$arePartLevelsValid(stack)) return false;
        if(ConfigHandler.LYCANITESMOBS_CONFIG.getEquipmentEnchantmentBlacklist().contains(enchantment)) return false;

        if(enchantment != Enchantments.SWEEPING && ((enchantment.type == EnumEnchantmentType.WEAPON) || (enchantment == Enchantments.EFFICIENCY))) {
            return true;
        }
        else return super.canApplyAtEnchantingTable(stack, enchantment);
    }
    
    @Unique
    private boolean rlmixins$arePartLevelsValid(ItemStack equipment) {
        int lowest = ConfigHandler.LYCANITESMOBS_CONFIG.minimumPartLevelForEnchants;
        if(lowest <= 0) return true;
        
        for(ItemStack equipmentPartStack : this.getEquipmentPartStacks(equipment)) {
            ItemEquipmentPart equipmentPart = this.getEquipmentPart(equipmentPartStack);
            if(equipmentPart != null) {
                int partLevel = equipmentPart.getLevel(equipmentPartStack);
                if(partLevel < lowest) {
                    return false;
                }
            }
        }
        return true;
    }
}