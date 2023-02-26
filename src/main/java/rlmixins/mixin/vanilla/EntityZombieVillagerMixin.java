package rlmixins.mixin.vanilla;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.wrapper.SpawnerControlWrapper;

@Mixin(EntityZombieVillager.class)
public abstract class EntityZombieVillagerMixin extends EntityZombie {

    public EntityZombieVillagerMixin(World worldIn) {
        super(worldIn);
    }

    @Inject(
            method = "finishConversion",
            at = @At("HEAD")
    )
    public void rlmixins_vanillaEntityZombieVillager_finishConversion(CallbackInfo ci) {
        if(!this.world.isRemote) SpawnerControlWrapper.increaseSpawnerCount(this);
    }
}