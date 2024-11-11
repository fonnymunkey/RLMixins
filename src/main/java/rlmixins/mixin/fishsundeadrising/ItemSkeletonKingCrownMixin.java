package rlmixins.mixin.fishsundeadrising;

import com.Fishmod.mod_LavaCow.client.model.armor.ModelCrown;
import com.Fishmod.mod_LavaCow.item.ItemSkeletonKingCrown;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemSkeletonKingCrown.class)
public abstract class ItemSkeletonKingCrownMixin {
	
	@Unique
	private ModelCrown rlmixins$modelCrown;
	
	@Inject(
			method = "getArmorModel",
			at = @At("HEAD"),
			cancellable = true,
			remap = false
	)
	public void rlmixins_fishsUndeadRisingItemSkeletonKingCrown_getArmorModel(EntityLivingBase player, ItemStack stack, EntityEquipmentSlot armorSlot, ModelBiped modelBiped, CallbackInfoReturnable<ModelBiped> cir) {
		if(this.rlmixins$modelCrown == null) this.rlmixins$modelCrown = new ModelCrown(1.0F);
		cir.setReturnValue(this.rlmixins$modelCrown);
	}
}