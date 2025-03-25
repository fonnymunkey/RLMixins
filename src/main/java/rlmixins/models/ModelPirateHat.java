package rlmixins.models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

// Modified from bipedHeadwear to bipedHead for Mo'Bends Support
// https://github.com/VazkiiMods/Quark/blob/1.12/src/main/java/vazkii/quark/world/client/model/ModelPirateHat.java
public class ModelPirateHat extends ModelBiped {
    private static final ResourceLocation TEXTURE = new ResourceLocation("quark", "textures/entity/pirate_hat.png");
    private final ModelRenderer hat;
    private final ModelRenderer hatPart1;
    private final ModelRenderer hatPart2;
    private final ModelRenderer hatPart3;
    private final ModelRenderer hatPart4;

    public ModelPirateHat() {
        float modelOff = 2.5F;
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.hat = new ModelRenderer(this, 0, 0);
        this.hat.setRotationPoint(0.0F, -8.53F, 0.0F);
        this.hat.addBox(-5.0F, 0.0F + modelOff, -5.0F, 10, 1, 10, 0.0F);

        this.hatPart1 = new ModelRenderer(this, 0, 11);
        this.hatPart1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hatPart1.addBox(-3.5F, -4.0F + modelOff, -3.5F, 7, 4, 8, 1.0F);
        this.hat.addChild(this.hatPart1);

        this.hatPart2 = new ModelRenderer(this, 0, 30);
        this.hatPart2.mirror = true;
        this.hatPart2.addBox(4.75F, -4.0F + modelOff, -2.75F, 1, 4, 8, 0.0F);
        this.setRotateAngle(this.hatPart2, -0.08726646F, 0.0F, 0.08726646F);
        this.hat.addChild(this.hatPart2);

        this.hatPart3 = new ModelRenderer(this, 0, 30);
        this.hatPart3.addBox(-5.75F, -4.0F + modelOff, -2.75F, 1, 4, 8, 0.0F);
        this.setRotateAngle(this.hatPart3, -0.08726646F, 0.0F, -0.08726646F);
        this.hat.addChild(this.hatPart3);

        this.hatPart4 = new ModelRenderer(this, 0, 23);
        this.hatPart4.addBox(-5.0F, -6.4F + modelOff, -5.25F, 10, 6, 1, 0.0F);
        this.setRotateAngle(this.hatPart4, 0.08726646F, 0.0F, 0.0F);
        this.hat.addChild(this.hatPart4);
    }

    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        this.hat.render(scale);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
