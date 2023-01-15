package rlmixins.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.item.tool.WeaponToolArmorBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.RLMixins;
import rlmixins.models.ModelLivingArmor;
import rlmixins.models.ModelSentientArmor;

@Mixin(WeaponToolArmorBase.class)
public abstract class WeaponToolArmorBaseMixin {

    @Shadow(remap = false) protected boolean calling;

    @Inject(
            method = "getArmorModel",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_srparasitesWeaponToolArmorBase(EntityLivingBase entity, ItemStack stack, EntityEquipmentSlot slot, ModelBiped def, CallbackInfoReturnable<ModelBiped> cir) {
        if(!stack.isEmpty() && stack.getItem() instanceof ItemArmor) {
            if(this.calling) {
                ModelSentientArmor armorModel = (ModelSentientArmor) RLMixins.PROXY.getSentientArmor().get(stack.getItem());
                if(armorModel != null) {
                    armorModel.bipedHead.showModel = slot == EntityEquipmentSlot.HEAD;
                    armorModel.bipedHeadwear.showModel = slot == EntityEquipmentSlot.HEAD;
                    armorModel.bipedBody.showModel = slot == EntityEquipmentSlot.CHEST;
                    armorModel.bipedRightArm.showModel = slot == EntityEquipmentSlot.CHEST;
                    armorModel.bipedLeftArm.showModel = slot == EntityEquipmentSlot.CHEST;
                    armorModel.LeftLeg.showModel = slot == EntityEquipmentSlot.LEGS;
                    armorModel.RightLeg.showModel = slot == EntityEquipmentSlot.LEGS;
                    armorModel.LeftFoot.showModel = slot == EntityEquipmentSlot.FEET;
                    armorModel.RightFoot.showModel = slot == EntityEquipmentSlot.FEET;

                    armorModel.isSneak = def.isSneak;
                    armorModel.isRiding = def.isRiding;
                    armorModel.isChild = def.isChild;

                    if(entity instanceof EntityArmorStand) armorModel.swingProgress = 0;

                    armorModel.rightArmPose = def.rightArmPose;
                    armorModel.leftArmPose = def.leftArmPose;

                    cir.setReturnValue(armorModel);
                }
            }
            else {
                ModelLivingArmor armorModel = (ModelLivingArmor) RLMixins.PROXY.getLivingArmor().get(stack.getItem());
                if(armorModel != null) {
                    armorModel.bipedHead.showModel = slot == EntityEquipmentSlot.HEAD;
                    armorModel.bipedHeadwear.showModel = slot == EntityEquipmentSlot.HEAD;
                    armorModel.bipedBody.showModel = slot == EntityEquipmentSlot.CHEST;
                    armorModel.bipedRightArm.showModel = slot == EntityEquipmentSlot.CHEST;
                    armorModel.bipedLeftArm.showModel = slot == EntityEquipmentSlot.CHEST;
                    armorModel.LeftLeg.showModel = slot == EntityEquipmentSlot.LEGS;
                    armorModel.RightLeg.showModel = slot == EntityEquipmentSlot.LEGS;
                    armorModel.LeftFoot.showModel = slot == EntityEquipmentSlot.FEET;
                    armorModel.RightFoot.showModel = slot == EntityEquipmentSlot.FEET;

                    armorModel.isSneak = def.isSneak;
                    armorModel.isRiding = def.isRiding;
                    armorModel.isChild = def.isChild;

                    if(entity instanceof EntityArmorStand) armorModel.swingProgress = 0;

                    armorModel.rightArmPose = def.rightArmPose;
                    armorModel.leftArmPose = def.leftArmPose;

                    cir.setReturnValue(armorModel);
                }
            }
        }
    }
}