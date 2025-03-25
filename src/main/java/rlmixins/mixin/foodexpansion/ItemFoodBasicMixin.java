package rlmixins.mixin.foodexpansion;

import lellson.foodexpansion.items.ItemFoodBasic;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import rlmixins.RLMixins;

import javax.annotation.Nonnull;

@Mixin(ItemFoodBasic.class)
public abstract class ItemFoodBasicMixin extends ItemFood {
    @Shadow(remap = false) private PotionEffect[] effects;
    @Shadow(remap = false) private int probability;
    @Shadow(remap = false) private ItemStack returnItem;

    public ItemFoodBasicMixin(int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
    }

    /**
     * @author Nischhelm
     * @reason mainly stop eating the whole stack and not giving bowls back correctly, but also make it use ItemFood.onItemUseFinish instead of copying the code. also move potion effects to onFoodEaten where vanilla food effects happen
     */
    @Overwrite
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull EntityLivingBase entity) {
        //Give return item back (default: bowl)
        if(!entity.world.isRemote && this.returnItem != null)
            if(entity instanceof EntityPlayer)
                ((EntityPlayer) entity).inventory.addItemStackToInventory(returnItem.copy()); //copy so there can be multiple bowls returned
            else
                entity.dropItem(returnItem.getItem(), 1);

        return super.onItemUseFinish(stack, world, entity);
    }

    @Override
    protected void onFoodEaten(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull EntityPlayer player) {
        if(!player.world.isRemote) //don't apply potion effects on clientside
            for (PotionEffect effect : this.effects)
                if (world.rand.nextInt(this.probability) == 0)
                    player.addPotionEffect(new PotionEffect(effect));
    }
}