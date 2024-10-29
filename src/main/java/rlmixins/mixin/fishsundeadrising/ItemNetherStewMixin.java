package rlmixins.mixin.fishsundeadrising;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.item.ItemFishCustomFood;
import com.Fishmod.mod_LavaCow.item.ItemNetherStew;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import rlmixins.wrapper.ClientWrapper;

@Mixin(ItemNetherStew.class)
public abstract class ItemNetherStewMixin extends ItemFishCustomFood {
	
	public ItemNetherStewMixin(String registryName, int amount, float saturation, boolean isWolfFood, int duration, boolean hasTooltip) {
		super(registryName, amount, saturation, isWolfFood, duration, hasTooltip);
	}
	
	/**
	 * @author fonnymunkey
	 * @reason fix server crash and remove crazy resistance
	 */
	@Overwrite
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		super.onItemUseFinish(stack, worldIn, entityLiving);
		if (this.equals(FishItems.GHOSTJELLY)) {
			if(worldIn.isRemote) {
				ClientWrapper.setVelocity(entityLiving, 0.0, 2.0, 0.0);
			}
			else {
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 60, 2));
				entityLiving.playSound(SoundEvents.ENTITY_FIREWORK_LAUNCH, 1.0F, 1.0F);
			}
		}
		
		if (!worldIn.isRemote && (Modconfig.Bowls_Stack || stack.getCount() > stack.getMaxStackSize()) && entityLiving instanceof EntityPlayer && !((EntityPlayer)entityLiving).isCreative()) {
			((EntityPlayer)entityLiving).inventory.addItemStackToInventory(new ItemStack(Items.BOWL));
		}
		
		return !Modconfig.Bowls_Stack && stack.getCount() <= stack.getMaxStackSize() ? new ItemStack(Items.BOWL) : stack;
	}
}