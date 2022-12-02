package rlmixins.mixin.lycanites.boundingbox;

import com.lycanitesmobs.core.entity.creature.EntityEpion;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityEpion.class)
public abstract class EntityEpionRenderMixin {

    /**
     * Fix oversized render bounding box
     */
    @Inject(
            method = "getRenderBoundingBox",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    public void rlmixins_entityEpion_getRenderBoundingBox(CallbackInfoReturnable<AxisAlignedBB> cir) {
        //if(ForgeConfigHandler.client.targetEntity.equals("epion")) cir.setReturnValue(((EntityEpion)(Object)this).getEntityBoundingBox().grow(ForgeConfigHandler.client.growX, ForgeConfigHandler.client.growY, ForgeConfigHandler.client.growZ).offset(0.0, ForgeConfigHandler.client.offsetY, 0.0));
        cir.setReturnValue(((EntityEpion)(Object)this).getEntityBoundingBox().grow(3.0, 3.0, 3.0).offset(0.0, 0.5, 0.0));
    }
}