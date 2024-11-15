package rlmixins.mixin.cosmeticarmorreworked;

import lain.mods.cos.inventory.ContainerCosArmor;
import lain.mods.cos.inventory.InventoryCosArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.wrapper.SlotCosArmor;

@Mixin(ContainerCosArmor.class)
public abstract class ContainerCosArmorMixin extends ContainerPlayer {
	
	@Shadow(remap = false)
	@Final
	private static EntityEquipmentSlot[] VALID_EQUIPMENT_SLOTS;
	
	public ContainerCosArmorMixin(InventoryPlayer playerInventory, boolean localWorld, EntityPlayer playerIn) {
		super(playerInventory, localWorld, playerIn);
	}
	
	@Inject(
			method = "<init>",
			at = @At("RETURN")
	)
	public void rlmixins_cosmeticArmorReworkedContainerCosArmor_init(InventoryPlayer invPlayer, InventoryCosArmor invCosArmor, EntityPlayer player, CallbackInfo ci) {
		for(int i = 0; i < 4; i++) {
			this.inventoryItemStacks.remove(this.inventoryItemStacks.size() - 1);
			this.inventorySlots.remove(this.inventorySlots.size() - 1);
		}
		
		for(int i = 0; i < 4; i++) {
			String texture = ItemArmor.EMPTY_SLOT_NAMES[VALID_EQUIPMENT_SLOTS[i].getIndex()];
			EntityEquipmentSlot equipmentSlot = VALID_EQUIPMENT_SLOTS[i];
			this.addSlotToContainer(new SlotCosArmor(invCosArmor, 3 - i, 98 + i * 18, 62, texture, player, equipmentSlot));
		}
	}
}