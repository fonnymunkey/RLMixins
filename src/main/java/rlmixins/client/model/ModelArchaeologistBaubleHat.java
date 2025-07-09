package rlmixins.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Modified from bipedHeadwear to bipedHead for Mo'Bends Support
 * https://github.com/VazkiiMods/Quark/blob/1.12/src/main/java/vazkii/quark/world/client/model/ModelArchaeologistHat.java
 */
public class ModelArchaeologistBaubleHat extends ModelBiped {
    
    private static final ResourceLocation TEXTURE = new ResourceLocation("quark", "textures/entity/archaeologist_hat.png");
    
    private final ModelRenderer hat;

    public ModelArchaeologistBaubleHat() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.hat = new ModelRenderer(this, 0, 0);
        this.hat.cubeList.add(new ModelBox(this.hat, 0, 0, -4.0F, -10.0F, -4.0F, 8, 10, 8, 0.6F, false));
        this.hat.cubeList.add(new ModelBox(this.hat, 0, 18, -6.0F, -6.0F, -6.0F, 12, 1, 12, 0.0F, false));
    }

    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        this.hat.render(scale);
    }
}