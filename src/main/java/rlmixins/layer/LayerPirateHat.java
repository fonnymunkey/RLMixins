package rlmixins.layer;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.common.Config;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import rlmixins.models.ModelPirateHat;
import vazkii.quark.world.feature.PirateShips;

import javax.annotation.Nonnull;

public final class LayerPirateHat implements LayerRenderer<EntityPlayer> {
    private static final ModelPirateHat pirateHat = new ModelPirateHat();
    private RenderPlayer renderPlayer;
    private ModelPlayer modelPlayer;
    private boolean slim;

    public LayerPirateHat(RenderPlayer renderPlayer) {
        this(renderPlayer, false);
    }

    public LayerPirateHat(RenderPlayer renderPlayer, boolean slim) {
        this.renderPlayer = renderPlayer;
        this.modelPlayer = renderPlayer.getMainModel();
        this.slim = slim;
    }

    private void renderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (BaublesApi.isBaubleEquipped(player, PirateShips.pirate_hat) != -1) {
            ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleType.HEAD.getValidSlots()[0]);
            if (stack.getItem() == PirateShips.pirate_hat) {
                if (player.isSneaking()) {
                    GlStateManager.translate(0.0F, 0.2F, 0.0F);
                }

                this.modelPlayer.bipedHead.postRender(scale);
                pirateHat.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            }
        }
    }

    public void doRenderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (Config.renderBaubles && player.getActivePotionEffect(MobEffects.INVISIBILITY) == null) {
            GlStateManager.enableLighting();
            GlStateManager.enableRescaleNormal();
            GlStateManager.pushMatrix();
            this.renderLayer(player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.popMatrix();
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}
