package rlmixins.mixin.vanilla;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import rlmixins.handlers.ForgeConfigHandler;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public float fallDistance;

    /**
     * Make water reducing fall damage configurable instead of always 0
     */
    @ModifyConstant(
            method = "handleWaterMovement",
            constant = @Constant(floatValue = 0.0F)
    )
    public float rlmixins_vanillaEntity_handleWaterMovement(float constant) {
        if(ForgeConfigHandler.server.fallDistanceReduction < 0.0F) return 0.0F;
        return Math.max(this.fallDistance - (float)ForgeConfigHandler.server.fallDistanceReduction, 0.0F);
    }
}