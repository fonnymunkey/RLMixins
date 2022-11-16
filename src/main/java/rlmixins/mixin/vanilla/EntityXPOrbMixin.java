package rlmixins.mixin.vanilla;

import com.google.common.collect.Lists;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(EntityXPOrb.class)
public abstract class EntityXPOrbMixin {

    /**
     * Redirect Mending check to method that doesn't attempt to mend non-damaged items
     */
    @Redirect(
            method = "onCollideWithPlayer",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getEnchantedItem(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/entity/EntityLivingBase;)Lnet/minecraft/item/ItemStack;")
    )
    private ItemStack rlmixins_vanillaEntityXPOrb_onCollideWithPlayer(Enchantment enchant, EntityLivingBase entity) {
        return getDamagedItemForMending(entity);
    }

    /**
     * Add check to only return damaged items for mending
     */
    private static ItemStack getDamagedItemForMending(EntityLivingBase entity) {
        List<ItemStack> list = Enchantments.MENDING.getEntityEquipment(entity);

        if(!list.isEmpty()) {
            List<ItemStack> list1 = Lists.<ItemStack>newArrayList();

            for(ItemStack itemstack : list) {
                if(!itemstack.isEmpty() && EnchantmentHelper.getEnchantmentLevel(Enchantments.MENDING, itemstack) > 0 && itemstack.isItemDamaged()) {
                    list1.add(itemstack);
                }
            }

            return list1.isEmpty() ? ItemStack.EMPTY : (ItemStack)list1.get(entity.getRNG().nextInt(list1.size()));
        }
        return ItemStack.EMPTY;
    }
}