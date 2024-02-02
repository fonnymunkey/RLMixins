package rlmixins.mixin.vanilla;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.wrapper.SpawnerControlWrapper;

@Mixin(EntityPig.class)
public abstract class EntityPigMixin extends EntityAnimal {

    public EntityPigMixin(World worldIn) {
        super(worldIn);
    }

    @Inject(
            method = "onStruckByLightning",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z")
    )
    public void rlmixins_vanillaEntityPig_onStruckByLightning(EntityLightningBolt lightningBolt, CallbackInfo ci) {
        SpawnerControlWrapper.increaseSpawnerCount(this);
    }
}