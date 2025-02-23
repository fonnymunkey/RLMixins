package rlmixins.mixin.vanilla;

import net.minecraft.entity.monster.EntityGuardian;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EntityGuardian.class)
public abstract class EntityGuardianNoIdleSinkMixin {

    @ModifyConstant(
            method = "travel",
            constant = @Constant(doubleValue = 0.005D)
    )
    public double rlmixins_vanillaEntityGuardian_travel(double constant) {
        return 0.0D;
    }
}