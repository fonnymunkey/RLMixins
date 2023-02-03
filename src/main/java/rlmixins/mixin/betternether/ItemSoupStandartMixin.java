package rlmixins.mixin.betternether;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
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
        if(entityLiving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            entityplayer.getFoodStats().addStats(this, stack);
            worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            this.onFoodEaten(stack, worldIn, entityplayer);
            entityplayer.addStat(StatList.getObjectUseStats(this));

            if(entityplayer instanceof EntityPlayerMP) CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)entityplayer, stack);

            ((EntityPlayer)entityLiving).inventory.addItemStackToInventory(new ItemStack(ItemsRegister.STALAGNATE_BOWL, 1));
        }
        else entityLiving.dropItem(ItemsRegister.STALAGNATE_BOWL, 1);

        stack.shrink(1);
        return stack;
    }
}