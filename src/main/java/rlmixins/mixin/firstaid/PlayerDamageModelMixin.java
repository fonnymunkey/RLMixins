package rlmixins.mixin.firstaid;

import ichttt.mods.firstaid.api.damagesystem.AbstractDamageablePart;
import ichttt.mods.firstaid.common.damagesystem.PlayerDamageModel;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerDamageModel.class)
public abstract class PlayerDamageModelMixin {
/*
    @Redirect(
            method = "runScaleLogic",
            at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lichttt/mods/firstaid/api/damagesystem/AbstractDamageablePart;initialMaxHealth:I"),
            remap = false
    )
    public int rlmixins_playerDamageModel_runScaleLogic(AbstractDamageablePart instance) {
        return instance.getMaxHealth();
    }
 */
}
