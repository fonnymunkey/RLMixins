package rlmixins.wrapper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.Arrays;

public class SlotCosArmor extends Slot {
	
	private final String texture;
	private final EntityPlayer player;
	private final EntityEquipmentSlot equipmentSlot;
	
	public SlotCosArmor(IInventory inventoryIn, int index, int xPosition, int yPosition, String texture, EntityPlayer player, EntityEquipmentSlot equipmentSlot) {
		super(inventoryIn, index, xPosition, yPosition);
		this.texture = texture;
		this.player = player;
		this.equipmentSlot = equipmentSlot;
	}
	
	@Override
	public int getSlotStackLimit() {
		return 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getSlotTexture() {
		return texture;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		if(stack != null && !stack.isEmpty()) {
			if(stack.getItem().isValidArmor(stack, equipmentSlot, player)) {
				return Arrays.asList(ForgeConfigHandler.server.cosmeticArmorItemBlacklist).contains(stack.getItem().getRegistryName().toString()) == ForgeConfigHandler.server.cosmeticArmorItemBlacklistIsWhitelist;
			}
		}
		return false;
	}
}