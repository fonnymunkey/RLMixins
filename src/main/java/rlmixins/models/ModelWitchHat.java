package rlmixins.models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

// Modified from bipedHeadwear to bipedHead for Mo'Bends Support
// https://github.com/VazkiiMods/Quark/blob/1.12/src/main/java/vazkii/quark/vanity/client/model/ModelWitchHat.java
public class ModelWitchHat extends ModelBiped {
    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/entity/witch.png");
    private final ModelRenderer witchHat;
    private final ModelRenderer mainBox;
    private final ModelRenderer towerBox;
    private final ModelRenderer smallerBox;

    public ModelWitchHat() {
        this.textureWidth = 64;
        this.textureHeight = 128;
        this.witchHat = new ModelRenderer(this, 0, 64);
        this.witchHat.addBox(-5.0F,  -10.03125F, -5.0F, 10, 2, 10);

        this.mainBox = new ModelRenderer(this, 0, 76);
        this.mainBox.addBox(-3.25F,  -13.83125F, -3.0F, 7, 4, 7);
        this.mainBox.rotateAngleX = -0.05235988F;
        this.mainBox.rotateAngleZ = 0.02617994F;
        this.witchHat.addChild(this.mainBox);

        this.towerBox = new ModelRenderer(this, 0, 87);
        this.towerBox.addBox(-1.5F,  -17.83125F, -1.0F, 4, 4, 4);
        this.towerBox.rotateAngleX = -0.10471976F;
        this.towerBox.rotateAngleZ = 0.05235988F;
        this.witchHat.addChild(this.towerBox);

        this.smallerBox = new ModelRenderer(this, 0, 95);
        this.smallerBox.addBox(-0.5F,  -19.83125F, -1.0F, 1, 2, 1, 0.25F);
        this.smallerBox.rotateAngleX = -0.20943952F;
        this.smallerBox.rotateAngleZ = 0.10471976F;
        this.witchHat.addChild(this.smallerBox);
    }

    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        this.witchHat.render(scale);
    }
}
