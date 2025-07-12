package rlmixins.mixin.lycanitesmobs;

import com.lycanitesmobs.core.item.ItemBase;
import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import com.lycanitesmobs.core.item.equipment.ItemEquipmentPart;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.handlers.ConfigHandler;

import javax.annotation.Nonnull;
import java.util.List;

@Mixin(ItemEquipment.class)
public abstract class ItemEquipmentEnchantmentsMixin extends ItemBase {

    @Shadow(remap = false)
    public abstract NonNullList<ItemStack> getEquipmentPartStacks(ItemStack itemStack);
    @Shadow(remap = false)
    public abstract ItemEquipmentPart getEquipmentPart(ItemStack itemStack);

    @SideOnly(Side.CLIENT)
    @Inject(
            method = "addInformation",
            at = @At("TAIL")
    )
    public void rlmixins_lycanitesMobsItemEquipment_addInformation(ItemStack itemStack, World world, List<String> tooltip, ITooltipFlag tooltipFlag, CallbackInfo ci){
        if(rlmixins$getLowestPartLevel(itemStack) < ConfigHandler.LYCANITESMOBS_CONFIG.equipmentEnchantmentsMinLevelParts){
            if(ConfigHandler.LYCANITESMOBS_CONFIG.equipmentEnchantmentsMinLevelTooltips)
                tooltip.add(I18n.format("item.lycanitesequipment.description.enchrequirement", ConfigHandler.LYCANITESMOBS_CONFIG.equipmentEnchantmentsMinLevelParts));
        }
        else {
            if(itemStack.isItemEnchanted()){
                if(ConfigHandler.LYCANITESMOBS_CONFIG.equipEnchDisassembleLock) tooltip.add(I18n.format("item.lycanitesequipment.description.enchlock"));
                else tooltip.add(I18n.format("item.lycanitesequipment.description.enchremove"));
            }
            else tooltip.add(I18n.format("item.lycanitesequipment.description.enchantable"));
        }
    }

    @Override
    @Unique
    public boolean isEnchantable(@Nonnull ItemStack stack) {
        return true;
    }

    // Enchantment Table
    @Override
    @Unique
    public int getItemEnchantability() {
        return 1;
    }

    // Used by both Enchantment Table and Anvil
    /** Checks part level -> black list -> if Weapon enchantment OR check modded enchantment conditions. Super call is important for SME **/
    @Override
    @Unique
    public boolean canApplyAtEnchantingTable(@Nonnull ItemStack stack, @Nonnull Enchantment enchantment){
        if(rlmixins$getLowestPartLevel(stack) < ConfigHandler.LYCANITESMOBS_CONFIG.equipmentEnchantmentsMinLevelParts) return false;
        if(ConfigHandler.LYCANITESMOBS_CONFIG.getEquipmentEnchantmentBlacklist().contains(enchantment)) return false;

        if(enchantment != Enchantments.SWEEPING &&
                ((enchantment.type == EnumEnchantmentType.WEAPON) ||
                (enchantment == Enchantments.EFFICIENCY))){
            return true;
        }
        else{
            return super.canApplyAtEnchantingTable(stack, enchantment);
        }
    }

    @Unique
    public int rlmixins$getLowestPartLevel(ItemStack equipmentStack) {
        int lowestLevel = ConfigHandler.LYCANITESMOBS_CONFIG.equipmentEnchantmentsMinLevelParts;

        for(ItemStack equipmentPartStack : this.getEquipmentPartStacks(equipmentStack)) {
            ItemEquipmentPart equipmentPart = this.getEquipmentPart(equipmentPartStack);
            if (equipmentPart != null) {
                int partLevel = equipmentPart.getLevel(equipmentPartStack);
                if (partLevel < lowestLevel) {
                    lowestLevel = partLevel;
                }
            }
        }

        return lowestLevel;
    }
}
