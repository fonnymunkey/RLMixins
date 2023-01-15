package rlmixins.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;

public class ModelSentientArmor extends ModelBiped {
    public ModelRenderer RightLeg;
    public ModelRenderer LeftLeg;
    public ModelRenderer RightFoot;
    public ModelRenderer LeftFoot;

    public ModelSentientArmor(float scale) {
        super(scale, 0, 128, 128);
        textureWidth = 128;
        textureHeight = 128;

        bipedHead.cubeList.add(new ModelBox(bipedHead, 0, 64, -5.0F, -9.25F, -5.0F, 10, 9, 10, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 27, 87, -4.0F, -10.25F, -4.0F, 2, 1, 1, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 18, 97, -5.5F, -11.25F, -3.0F, 3, 3, 2, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 20, 116, -7.0F, -12.25F, -1.0F, 4, 4, 5, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 56, 101, -7.0F, -11.25F, 4.0F, 4, 4, 3, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 93, 64, -7.0F, -9.25F, 7.0F, 3, 3, 1, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 0, 71, -7.0F, -7.25F, 8.0F, 2, 2, 1, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 59, 96, -6.5F, -6.25F, -2.0F, 2, 3, 2, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 0, 97, -6.0F, -6.25F, -3.0F, 1, 2, 1, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 6, 71, 5.0F, -6.25F, -3.0F, 1, 2, 1, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 56, 64, -7.0F, -6.25F, 0.0F, 2, 4, 2, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 0, 64, -7.0F, -5.25F, 2.0F, 2, 4, 3, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 33, 116, -7.0F, -4.25F, 5.0F, 3, 4, 1, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 0, 83, -7.0F, -2.25F, 6.0F, 2, 3, 1, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 30, 71, 2.0F, -10.25F, -4.0F, 2, 1, 1, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 34, 104, 2.5F, -11.25F, -3.0F, 3, 3, 2, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 38, 116, 3.0F, -12.25F, -1.0F, 4, 4, 5, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 56, 116, 3.0F, -11.25F, 4.0F, 4, 4, 3, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 101, 64, 4.0F, -9.25F, 7.0F, 3, 3, 1, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 0, 87, 5.0F, -7.25F, 8.0F, 2, 2, 1, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 60, 108, 4.5F, -6.25F, -2.0F, 2, 3, 2, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 58, 83, 5.0F, -6.25F, 0.0F, 2, 4, 2, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 30, 64, 5.0F, -5.25F, 2.0F, 2, 4, 3, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 110, 64, 4.0F, -4.25F, 5.0F, 3, 4, 1, 0.0F, false));
        bipedHead.cubeList.add(new ModelBox(bipedHead, 27, 83, 5.0F, -2.25F, 6.0F, 2, 3, 1, 0.0F, false));

        bipedBody.cubeList.add(new ModelBox(bipedBody, 0, 83, -5.0F, 0.1F, -3.5F, 10, 7, 7, 0.0F, false));
        bipedBody.cubeList.add(new ModelBox(bipedBody, 70, 69, 3.0F, 0.0F, 2.5F, 0, 7, 6, 0.0F, false));
        bipedBody.cubeList.add(new ModelBox(bipedBody, 54, 84, 2.0F, 7.0F, 2.5F, 0, 7, 5, 0.0F, false));
        bipedBody.cubeList.add(new ModelBox(bipedBody, 54, 84, -2.0F, 7.0F, 2.5F, 0, 7, 5, 0.0F, false));
        bipedBody.cubeList.add(new ModelBox(bipedBody, 70, 69, -3.0F, 0.0F, 2.5F, 0, 7, 6, 0.0F, false));
        bipedBody.cubeList.add(new ModelBox(bipedBody, 71, 112, -5.0F, 7.0F, -3.0F, 10, 7, 6, 0.0F, false));
        bipedBody.cubeList.add(new ModelBox(bipedBody, 76, 108, -5.25F, 4.75F, -4.0F, 4, 1, 2, 0.0F, false));
        bipedBody.cubeList.add(new ModelBox(bipedBody, 76, 108, -5.25F, 2.75F, -4.0F, 4, 1, 2, 0.0F, false));
        bipedBody.cubeList.add(new ModelBox(bipedBody, 76, 108, -5.25F, 0.75F, -4.0F, 4, 1, 2, 0.0F, false));
        bipedBody.cubeList.add(new ModelBox(bipedBody, 76, 108, 1.25F, 0.75F, -4.0F, 4, 1, 2, 0.0F, true));
        bipedBody.cubeList.add(new ModelBox(bipedBody, 76, 108, 1.25F, 2.75F, -4.0F, 4, 1, 2, 0.0F, true));
        bipedBody.cubeList.add(new ModelBox(bipedBody, 76, 108, 1.25F, 4.75F, -4.0F, 4, 1, 2, 0.0F, true));

        bipedRightArm.cubeList.add(new ModelBox(bipedRightArm, 34, 77, -4.5F, -2.75F, -3.0F, 6, 6, 6, 0.0F, false));
        bipedRightArm.cubeList.add(new ModelBox(bipedRightArm, 0, 111, -3.65F, 2.25F, -2.5F, 5, 7, 5, 0.0F, false));

        bipedLeftArm.cubeList.add(new ModelBox(bipedLeftArm, 40, 104, -1.35F, 2.25F, -2.5F, 5, 7, 5, 0.0F, true));
        bipedLeftArm.cubeList.add(new ModelBox(bipedLeftArm, 0, 97, -1.5F, -2.75F, -3.0F, 6, 6, 6, 0.0F, true));

        RightLeg = bipedRightLeg;
        LeftLeg = bipedLeftLeg;
        RightFoot = bipedRightLeg;
        LeftFoot = bipedLeftLeg;

        RightLeg.cubeList.add(new ModelBox(RightLeg, 108, 114, -2.7F, -0.9F, -2.5F, 5, 9, 5, 0.0F, false));

        LeftLeg.cubeList.add(new ModelBox(LeftLeg, 108, 100, -2.3F, -0.9F, -2.5F, 5, 9, 5, 0.0F, true));

        RightFoot.cubeList.add(new ModelBox(RightFoot, 40, 64, -2.71F, 6.25F, -3.0F, 5, 6, 6, 0.0F, false));

        LeftFoot.cubeList.add(new ModelBox(LeftFoot, 18, 104, -2.29F, 6.25F, -3.0F, 5, 6, 6, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
                                  float headPitch, float scaleFactor, Entity entityIn) {
        if(entityIn instanceof EntityArmorStand) {
            EntityArmorStand entityarmorstand = (EntityArmorStand) entityIn;
            this.bipedHead.rotateAngleX = 0.017453292F * entityarmorstand.getHeadRotation().getX();
            this.bipedHead.rotateAngleY = 0.017453292F * entityarmorstand.getHeadRotation().getY();
            this.bipedHead.rotateAngleZ = 0.017453292F * entityarmorstand.getHeadRotation().getZ();
            this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
            this.bipedBody.rotateAngleX = 0.017453292F * entityarmorstand.getBodyRotation().getX();
            this.bipedBody.rotateAngleY = 0.017453292F * entityarmorstand.getBodyRotation().getY();
            this.bipedBody.rotateAngleZ = 0.017453292F * entityarmorstand.getBodyRotation().getZ();
            this.bipedLeftArm.rotateAngleX = 0.017453292F * entityarmorstand.getLeftArmRotation().getX();
            this.bipedLeftArm.rotateAngleY = 0.017453292F * entityarmorstand.getLeftArmRotation().getY();
            this.bipedLeftArm.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftArmRotation().getZ();
            this.bipedRightArm.rotateAngleX = 0.017453292F * entityarmorstand.getRightArmRotation().getX();
            this.bipedRightArm.rotateAngleY = 0.017453292F * entityarmorstand.getRightArmRotation().getY();
            this.bipedRightArm.rotateAngleZ = 0.017453292F * entityarmorstand.getRightArmRotation().getZ();
            this.bipedLeftLeg.rotateAngleX = 0.017453292F * entityarmorstand.getLeftLegRotation().getX();
            this.bipedLeftLeg.rotateAngleY = 0.017453292F * entityarmorstand.getLeftLegRotation().getY();
            this.bipedLeftLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftLegRotation().getZ();
            this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
            this.bipedRightLeg.rotateAngleX = 0.017453292F * entityarmorstand.getRightLegRotation().getX();
            this.bipedRightLeg.rotateAngleY = 0.017453292F * entityarmorstand.getRightLegRotation().getY();
            this.bipedRightLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getRightLegRotation().getZ();
            this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
            copyModelAngles(this.bipedHead, this.bipedHeadwear);
        } else
            super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor,
                    entityIn);
    }
}