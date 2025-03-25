package rlmixins.mixin.switchbow;

import de.Whitedraco.switchbow.entity.arrow.EntityArrowLove;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityArrowLove.class)
public abstract class EntityArrowLoveSelfDropMixin {

    // Removes the arrow refund that can be abused with multishot/splitshot enchants
    @Redirect(
            method = "arrowHit",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"),
            remap = true
    )
    private boolean rlmixins_switchbowEntityArrowLove_arrowHit(World instance, Entity entity){
        // no op
        return false;
    }
}
