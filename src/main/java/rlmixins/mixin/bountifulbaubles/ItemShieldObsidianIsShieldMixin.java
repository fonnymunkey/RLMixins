package rlmixins.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.item.ItemShieldCobalt;
import cursedflames.bountifulbaubles.item.ItemShieldObsidian;
import cursedflames.bountifulbaubles.item.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nullable;

@Mixin(ItemShieldObsidian.class)
public abstract class ItemShieldObsidianIsShieldMixin extends ItemShieldCobalt {

    public ItemShieldObsidianIsShieldMixin(String name) {
        super(name);
    }

    @Override
    public boolean isShield(ItemStack stack, @Nullable EntityLivingBase entity) {
        return stack.getItem() == ModItems.shieldObsidian;
    }
}
