package rlmixins.item;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rlmixins.RLMixins;
import rlmixins.client.model.ModelScarliteArmor;

public class ItemScarliteArmor extends ItemArmor {

    public ItemScarliteArmor(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
        this.setRegistryName(RLMixins.MODID, name);
        this.setTranslationKey(name);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entity, ItemStack stack, EntityEquipmentSlot slot, ModelBiped def) {
        if(!stack.isEmpty() && stack.getItem() instanceof ItemArmor) {
            ModelScarliteArmor armorModel = (ModelScarliteArmor)RLMixins.PROXY.getScarliteArmor().get(this);
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

                return armorModel;
            }
        }
        return null;
    }
}