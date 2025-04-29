package rlmixins.mixin.vanilla;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import rlmixins.wrapper.SpawnerControlWrapper;

@Mixin(EntityPig.class)
public abstract class EntityPigMixin extends EntityAnimal {
    public EntityPigMixin(World worldIn) {
        super(worldIn);
    }

    @WrapWithCondition(
            method = "onStruckByLightning",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z")
    )
    public boolean rlmixins_vanillaEntityPig_onStruckByLightning(World instance, Entity entity) {
        return SpawnerControlWrapper.increaseSpawnerCount(this);
    }
}