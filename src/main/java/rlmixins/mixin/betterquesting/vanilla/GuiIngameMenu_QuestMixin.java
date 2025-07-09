package rlmixins.mixin.betterquesting.vanilla;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.wrapper.BetterQuestingWrapper;

import java.util.List;

@Mixin(GuiIngameMenu.class)
public abstract class GuiIngameMenu_QuestMixin extends GuiScreen {
	
	@Redirect(
			method = "initGui",
			at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 4)
	)
	private boolean rlmixins_vanillaGuiIngameMenu_initGui(List<GuiButton> instance, Object button) {
		if(((GuiButton)button).id == 5) {
			return instance.add(new GuiButton(((GuiButton)button).id, ((GuiButton)button).x, ((GuiButton)button).y, ((GuiButton)button).width, ((GuiButton)button).height, I18n.format("advancementTab.questMenu")));
		}
		else return instance.add((GuiButton)button);
	}
	
	@Inject(
			method = "actionPerformed",
			at = @At("HEAD"),
			cancellable = true
	)
	private void rlmixins_vanillaGuiIngameMenu_actionPerformed(GuiButton button, CallbackInfo ci) {
		if(button.id == 5) {
			BetterQuestingWrapper.displayQuestScreen(this.mc);
			ci.cancel();
		}
	}
}