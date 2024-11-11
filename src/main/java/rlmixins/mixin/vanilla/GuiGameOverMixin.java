package rlmixins.mixin.vanilla;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.RLMixins;

@Mixin(GuiGameOver.class)
public abstract class GuiGameOverMixin extends GuiScreen {
	
	@Inject(
			method = "actionPerformed",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V", ordinal = 1, shift = At.Shift.BEFORE)
	)
	public void rlmixins_vanillaGuiGameOver_actionPerformed(GuiButton button, CallbackInfo ci) {
		RLMixins.LOGGER.log(Level.INFO, "Hardcore fixed");
		if(this.mc.world != null) {
			this.mc.world.sendQuittingDisconnectingPacket();
		}
		this.mc.loadWorld(null);
	}
}