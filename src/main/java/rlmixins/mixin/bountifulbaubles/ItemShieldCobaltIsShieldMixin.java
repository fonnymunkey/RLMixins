package rlmixins.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.item.ItemShieldCobalt;
import cursedflames.bountifulbaubles.item.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nullable;

@Mixin(ItemShieldCobalt.class)
public abstract class ItemShieldCobaltIsShieldMixin extends ItemShield {

    @Override
    public boolean isShield(ItemStack stack, @Nullable EntityLivingBase entity) {
        return stack.getItem() == ModItems.shieldCobalt;
    }
}