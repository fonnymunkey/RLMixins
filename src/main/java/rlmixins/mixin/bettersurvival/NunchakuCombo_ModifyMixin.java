package rlmixins.mixin.bettersurvival;

import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.NunchakuCombo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import rlmixins.handlers.ConfigHandler;

@Mixin(NunchakuCombo.class)
public abstract class NunchakuCombo_ModifyMixin {
	
	@ModifyConstant(
			method = "setComboPower",
			constant = @Constant(floatValue = 1.0F),
			remap = false
	)
	private float rlmixins_betterSurvivalNunchakuCombo_setComboPower(float constant) {
		return ConfigHandler.BETTERSURVIVAL_CONFIG.nunchakuComboMaxModifier;
	}
}