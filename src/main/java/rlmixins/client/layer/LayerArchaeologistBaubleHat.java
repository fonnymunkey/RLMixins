package rlmixins.client.layer;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import rlmixins.client.model.ModelArchaeologistBaubleHat;
import vazkii.quark.world.feature.Archaeologist;

import javax.annotation.Nonnull;

/**
 * By cdstk
 */
public class LayerArchaeologistBaubleHat extends LayerQuarkBaubleHat {

    private static final ModelArchaeologistBaubleHat archaeologistHat = new ModelArchaeologistBaubleHat();

    public LayerArchaeologistBaubleHat(RenderPlayer renderPlayer) {
        super(renderPlayer);
    }

    @Override
    protected void renderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if(!this.shouldRenderInSlot(player, EntityEquipmentSlot.HEAD)) return;
        ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleType.HEAD.getValidSlots()[0]);
        if(stack.getItem() != Archaeologist.archaeologist_hat || !this.shouldItemStackRender(player, stack)) return;

        if(player.isSneaking()) GlStateManager.translate(0, 0.2F, 0);
        modelPlayer.bipedHead.postRender(scale);
        archaeologistHat.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }
}
