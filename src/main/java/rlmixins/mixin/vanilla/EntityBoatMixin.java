package rlmixins.mixin.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.BlightInfernalHandler;

@Mixin(EntityBoat.class)
public abstract class EntityBoatMixin {

    /**
     * Fix infernal/blight mobs getting stuck in boats
     */
    @Redirect(
            method = "onUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;startRiding(Lnet/minecraft/entity/Entity;)Z")
    )
    public boolean rlmixins_vanillaEntityBoat_onUpdate(Entity instance, Entity entityIn) {
        if(instance instanceof EntityLivingBase &&
                (BlightInfernalHandler.isEntityBlight((EntityLivingBase)instance) ||
                        BlightInfernalHandler.isEntityInfernal((EntityLivingBase) instance))) entityIn.applyEntityCollision(instance);
        else instance.startRiding(entityIn);
        return true;
    }
}
