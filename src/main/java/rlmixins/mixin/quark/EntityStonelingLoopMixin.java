package rlmixins.mixin.quark;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vazkii.quark.world.entity.EntityStoneling;

@Mixin(EntityStoneling.class)
public abstract class EntityStonelingLoopMixin {

    @Redirect(
            method = "canEntityBeSeen",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEyeHeight()F")
    )
    public float rlmixins_quarkEntityStoneling_canEntityBeSeen(Entity instance) {
        return Math.max(0.1F, instance.getEyeHeight());
    }
}