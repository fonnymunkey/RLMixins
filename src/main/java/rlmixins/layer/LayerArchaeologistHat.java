package rlmixins.layer;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import rlmixins.models.ModelArchaeologistHat;
import vazkii.quark.world.feature.Archaeologist;

import javax.annotation.Nonnull;

public final class LayerArchaeologistHat extends LayerQuarkHat {

    private static final ModelArchaeologistHat archaeologistHat = new ModelArchaeologistHat();

    public LayerArchaeologistHat(RenderPlayer renderPlayer) {
        super(renderPlayer);
    }

    @Override
    protected void renderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if(BaublesApi.isBaubleEquipped(player, Archaeologist.archaeologist_hat) == -1 || !this.shouldRenderInSlot(player, EntityEquipmentSlot.HEAD)) return;
        ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleType.HEAD.getValidSlots()[0]);
        if(stack.getItem() != Archaeologist.archaeologist_hat || !this.shouldItemStackRender(player, stack)) return;

        if(player.isSneaking()) GlStateManager.translate(0, 0.2F, 0);
        modelPlayer.bipedHead.postRender(scale);
        archaeologistHat.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }
}
