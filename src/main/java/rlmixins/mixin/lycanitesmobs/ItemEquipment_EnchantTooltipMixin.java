package rlmixins.mixin.lycanitesmobs;

import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import com.lycanitesmobs.core.item.equipment.ItemEquipmentPart;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.handlers.ConfigHandler;

import java.util.List;

/**
 * By cdstk
 */
@Mixin(ItemEquipment.class)
public abstract class ItemEquipment_EnchantTooltipMixin {
    
    @Shadow(remap = false) public abstract NonNullList<ItemStack> getEquipmentPartStacks(ItemStack itemStack);
    
    @Shadow(remap = false) public abstract ItemEquipmentPart getEquipmentPart(ItemStack itemStack);
    
    @Inject(
            method = "addInformation",
            at = @At("TAIL")
    )
    private void rlmixins_lycanitesMobsItemEquipment_addInformation(ItemStack itemStack, World world, List<String> tooltip, ITooltipFlag tooltipFlag, CallbackInfo ci) {
        if(rlmixins$arePartLevelsValid(itemStack)) {
            if(itemStack.isItemEnchanted()) {
                if(ConfigHandler.LYCANITESMOBS_CONFIG.enchantsPreventDisassembling) tooltip.add(I18n.format("item.lycanitesequipment.description.enchlock"));
                else tooltip.add(I18n.format("item.lycanitesequipment.description.enchremove"));
            }
            else tooltip.add(I18n.format("item.lycanitesequipment.description.enchantable"));
        }
        else {
            if(ConfigHandler.LYCANITESMOBS_CONFIG.minimumPartLevelTooltip) {
                tooltip.add(I18n.format("item.lycanitesequipment.description.enchrequirement", ConfigHandler.LYCANITESMOBS_CONFIG.minimumPartLevelForEnchants));
            }
        }
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