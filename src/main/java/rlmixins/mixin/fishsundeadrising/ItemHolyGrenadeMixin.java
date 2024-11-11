package rlmixins.mixin.fishsundeadrising;

import com.Fishmod.mod_LavaCow.entities.projectiles.EntityGhostBomb;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityHolyGrenade;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntitySonicBomb;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.item.ItemFishCustom;
import com.Fishmod.mod_LavaCow.item.ItemHolyGrenade;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ItemHolyGrenade.class)
public abstract class ItemHolyGrenadeMixin extends ItemFishCustom {
	
	public ItemHolyGrenadeMixin(String registryName, Item afteruse, CreativeTabs tab, boolean hasTooltip) {
		super(registryName, afteruse, tab, hasTooltip);
	}
	
	/**
	 * @author fonnymunkey
	 * @reason fix grenades not being consumed
	 */
	@Overwrite
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if(!worldIn.isRemote) {
			worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			
			if(itemstack.getItem().equals(FishItems.HOLY_GRENADE)) {
				EntityHolyGrenade entitysnowball = new EntityHolyGrenade(worldIn, playerIn);
				entitysnowball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -20.0F, 1.5F, 1.0F);
				worldIn.spawnEntity(entitysnowball);
			}
			else if(itemstack.getItem().equals(FishItems.GHOSTBOMB)) {
				EntityGhostBomb entitysnowball = new EntityGhostBomb(worldIn, playerIn);
				entitysnowball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -20.0F, 0.5F, 1.0F);
				worldIn.spawnEntity(entitysnowball);
			}
			else if(itemstack.getItem().equals(FishItems.SONICBOMB)) {
				EntitySonicBomb entitysnowball = new EntitySonicBomb(worldIn, playerIn);
				entitysnowball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -20.0F, 1.5F, 1.0F);
				worldIn.spawnEntity(entitysnowball);
			}
			
			if(!playerIn.capabilities.isCreativeMode) {
				itemstack.shrink(1);
				if(itemstack.getCount() <= 0) {
					playerIn.inventory.deleteStack(itemstack);
				}
			}
		}
		return new ActionResult(EnumActionResult.SUCCESS, itemstack);
	}
}