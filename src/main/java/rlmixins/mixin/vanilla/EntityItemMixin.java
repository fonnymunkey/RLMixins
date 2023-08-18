package rlmixins.mixin.vanilla;

import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityItem.class)
public abstract class EntityItemMixin {

    @Redirect(
            method = "onUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityItem;move(Lnet/minecraft/entity/MoverType;DDD)V")
    )
    public void rlmixins_vanillaEntityItem_onUpdate(EntityItem instance, MoverType moverType, double dx, double dy, double dz) {
        //Run on odd ticks to not skip %25 check
        if(instance.ticksExisted%2!=0) instance.move(moverType, dx, dy, dz);
    }
}