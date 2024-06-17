package rlmixins.mixin.vanilla;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererDeathShakeMixin {

    @Shadow @Final private Minecraft mc;

    @ModifyVariable(
            method = "rotateArm",
            at = @At(value = "STORE"),
            index = 3
    )
    public float rlmixins_vanillaItemRenderer_rotateArm_store3(float value) {
        return Minecraft.getMinecraft().currentScreen instanceof GuiGameOver ? this.mc.player.renderArmPitch : value;
    }

    @ModifyVariable(
            method = "rotateArm",
            at = @At(value = "STORE"),
            index = 4
    )
    public float rlmixins_vanillaItemRenderer_rotateArm_store4(float value) {
        return Minecraft.getMinecraft().currentScreen instanceof GuiGameOver ? this.mc.player.renderArmYaw : value;
    }
}