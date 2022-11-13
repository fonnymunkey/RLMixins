package rlmixins.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.item.ItemTrinketObsidianSkull;
import cursedflames.bountifulbaubles.item.base.AGenericItemBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemTrinketObsidianSkull.class)
public abstract class ItemTrinketObsidianSkullMixin extends AGenericItemBauble{

    public ItemTrinketObsidianSkullMixin(String name) {
        super(name);
    }

    /**
     * Makes Obsidian Skull give fire resistance, since the custom damage handling is broken with First Aid
     */
    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        if(player.ticksExisted % 39 == 0) {
            player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 40, 0, true, false));
        }
    }
}
