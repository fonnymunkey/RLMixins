package rlmixins.mixin.betternether;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import paulevs.betternether.items.ItemSoupStandart;
import paulevs.betternether.items.ItemsRegister;

@Mixin(ItemSoupStandart.class)
public abstract class ItemSoupStandartMixin extends ItemSoup {

    public ItemSoupStandartMixin(int healAmount) {
        super(healAmount);
    }

    /**
     * @author fonnymunkey
     * @reason fix Stalagnate bowls deleting the stack when eaten if the stacksize is over 1
     */
    @Overwrite
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        super.onItemUseFinish(stack, worldIn, entityLiving);

        if(entityLiving instanceof EntityPlayer) {
            ((EntityPlayer)entityLiving).inventory.addItemStackToInventory(new ItemStack(ItemsRegister.STALAGNATE_BOWL, 1));
        }
        else entityLiving.dropItem(ItemsRegister.STALAGNATE_BOWL, 1);

        return stack;
    }
}