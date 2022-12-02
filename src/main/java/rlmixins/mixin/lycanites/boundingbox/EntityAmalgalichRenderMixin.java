package rlmixins.mixin.lycanites.boundingbox;

import com.lycanitesmobs.core.entity.creature.EntityAmalgalich;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityAmalgalich.class)
public abstract class EntityAmalgalichRenderMixin {

    /**
     * Fix oversized render bounding box
     */
    @Inject(
            method = "getRenderBoundingBox",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    public void rlmixins_entityAmalgalich_getRenderBoundingBox(CallbackInfoReturnable<AxisAlignedBB> cir) {
        //if(ForgeConfigHandler.client.targetEntity.equals("amalgalich")) cir.setReturnValue(((EntityAmalgalich)(Object)this).getEntityBoundingBox().grow(ForgeConfigHandler.client.growX, ForgeConfigHandler.client.growY, ForgeConfigHandler.client.growZ).offset(0.0, ForgeConfigHandler.client.offsetY, 0.0));
        cir.setReturnValue(((EntityAmalgalich)(Object)this).getEntityBoundingBox().grow(5.0, 1.0, 5.0));
    }
}