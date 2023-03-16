package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_4_0.Enchantmentadvancedmending;
import com.Shultrea.Rin.Enchantment_Base_Sector.EnchantmentBase;
import com.Shultrea.Rin.Enchantments_Sector.Smc_040;
import com.google.common.collect.Lists;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.List;

@Mixin(Enchantmentadvancedmending.class)
public abstract class EnchantmentAdvancedMendingMixin extends EnchantmentBase {

    public EnchantmentAdvancedMendingMixin(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }

    /**
     * @author fonnymunkey
     * @reason fix advanced mending applying with infinity
     */
    @Overwrite
    public boolean canApplyTogether(Enchantment fTest) {
        return fTest != Enchantments.MENDING && (!ForgeConfigHandler.server.advMendingInfinityRestrict || fTest != Enchantments.INFINITY) && super.canApplyTogether(fTest);
    }
    @Redirect(
            method = "onXP",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getEnchantedItem(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/entity/EntityLivingBase;)Lnet/minecraft/item/ItemStack;")
    )
    public ItemStack rlmixins_soManyEnchantmentsEnchantmentAdvancedMending_onXP(Enchantment enchant, EntityLivingBase player) {
        return ForgeConfigHandler.server.advMendingDamagedPriority ? getDamagedItemForMending(player) : EnchantmentHelper.getEnchantedItem(enchant, player);
    }

    /**
     * Add check to only return damaged items for mending
     */
    private static ItemStack getDamagedItemForMending(EntityLivingBase entity) {
        List<ItemStack> list = Smc_040.advancedmending.getEntityEquipment(entity);

        if(!list.isEmpty()) {
            List<ItemStack> list1 = Lists.<ItemStack>newArrayList();

            for(ItemStack itemstack : list) {
                if(!itemstack.isEmpty() && EnchantmentHelper.getEnchantmentLevel(Smc_040.advancedmending, itemstack) > 0 && itemstack.isItemDamaged()) {
                    list1.add(itemstack);
                }
            }

            return list1.isEmpty() ? ItemStack.EMPTY : (ItemStack)list1.get(entity.getRNG().nextInt(list1.size()));
        }
        return ItemStack.EMPTY;
    }
}