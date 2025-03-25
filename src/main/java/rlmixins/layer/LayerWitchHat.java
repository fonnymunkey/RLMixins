package rlmixins.layer;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import rlmixins.models.ModelWitchHat;
import vazkii.quark.vanity.feature.WitchHat;

import javax.annotation.Nonnull;

public final class LayerWitchHat extends LayerQuarkHat {

    private static final ModelWitchHat witchHat = new ModelWitchHat();

    public LayerWitchHat(RenderPlayer renderPlayer) {
        super(renderPlayer);
    }

    @Override
    protected void renderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if(BaublesApi.isBaubleEquipped(player, WitchHat.witch_hat) == -1 || !this.shouldRenderInSlot(player, EntityEquipmentSlot.HEAD)) return;
        ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleType.HEAD.getValidSlots()[0]);
        if(stack.getItem() != WitchHat.witch_hat || !this.shouldItemStackRender(player, stack)) return;

        if(player.isSneaking()) GlStateManager.translate(0, 0.2F, 0);
        modelPlayer.bipedHead.postRender(scale);
        witchHat.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }
}
