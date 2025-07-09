package rlmixins.client.layer;

import baubles.common.Config;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * By cdstk
 */
public abstract class LayerQuarkBaubleHat implements LayerRenderer<EntityPlayer> {
    
    protected RenderPlayer renderPlayer;
    protected ModelPlayer modelPlayer;
    
    public LayerQuarkBaubleHat(RenderPlayer renderPlayer) {
        this.renderPlayer = renderPlayer;
        this.modelPlayer = renderPlayer.getMainModel();
    }

    @Override
    public final void doRenderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if(!Config.renderBaubles || player.getActivePotionEffect(MobEffects.INVISIBILITY) != null) return;
        
        GlStateManager.pushMatrix();
        GlStateManager.enableLighting();
        GlStateManager.enableRescaleNormal();
        renderLayer(player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.popMatrix();
    }

    protected abstract void renderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale);

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

    public boolean shouldRenderInSlot(EntityPlayer player, EntityEquipmentSlot slot) {
        ItemStack stack = player.getItemStackFromSlot(slot);
        return stack.isEmpty() ||
                (stack.getTagCompound() != null &&
                        stack.getTagCompound().getBoolean("classy_hat_invisible") &&
                        stack.getTagCompound().getCompoundTag("classy_hat_disguise").isEmpty());
    }

    public boolean shouldItemStackRender(EntityPlayer player, ItemStack stack) {
        return stack.getTagCompound() == null || !stack.getTagCompound().getBoolean("phantom_thread_invisible");
    }
}