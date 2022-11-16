package rlmixins.entity.flare;

import cursedflames.bountifulbaubles.client.model.ModelFlare;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFlareNonAlbedo extends Render<EntityFlareNonAlbedo> {
    private final ModelFlare flare = new ModelFlare();
    private final ResourceLocation flareTexture = new ResourceLocation("bountifulbaubles", "textures/entity/flare.png");

    public RenderFlareNonAlbedo(RenderManager renderManager) {
        super(renderManager);
    }

    protected ResourceLocation getEntityTexture(EntityFlareNonAlbedo entity) {
        return this.flareTexture;
    }

    private float getRenderYaw(float prevYaw, float yaw, float partialTicks) {
        float f;
        for(f = yaw - prevYaw; f < -180.0F; f += 360.0F) {
        }

        while(f >= 180.0F) {
            f -= 360.0F;
        }

        return prevYaw + partialTicks * f;
    }

    public void doRender(EntityFlareNonAlbedo entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        float yaw = -this.getRenderYaw(entity.prevRotationYaw, entity.rotationYaw, partialTicks);
        float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.enableAlpha();
        this.bindEntityTexture(entity);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.flare.render(entity, 0.0F, 0.0F, 0.0F, yaw, pitch, 0.0625F);
        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}