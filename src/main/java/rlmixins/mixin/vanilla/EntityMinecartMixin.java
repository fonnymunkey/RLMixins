package rlmixins.mixin.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.wrapper.BlightInfernalChampionWrapper;

@Mixin(EntityMinecart.class)
public abstract class EntityMinecartMixin {

    /**
     * Fix infernal/blight mobs getting stuck in minecarts
     */
    @Redirect(
            method = "onUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;startRiding(Lnet/minecraft/entity/Entity;)Z")
    )
    public boolean rlmixins_vanillaEntityMinecart_onUpdate(Entity instance, Entity entityIn) {
        if(instance instanceof EntityLivingBase &&
                (BlightInfernalChampionWrapper.isEntityBlight((EntityLivingBase)instance) ||
                        BlightInfernalChampionWrapper.isEntityInfernal((EntityLivingBase) instance))) entityIn.applyEntityCollision(instance);
        else instance.startRiding(entityIn);
        return true;
    }
}
