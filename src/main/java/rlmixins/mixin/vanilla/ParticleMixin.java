package rlmixins.mixin.vanilla;

import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.HashMap;

@Mixin(Particle.class)
public abstract class ParticleMixin {

    //Cache to Class to avoid excessive string manipulation
    @Unique
    private static final HashMap<Class<? extends Particle>, Boolean> rlmixins$particleCollisionMap = new HashMap<>();

    @ModifyConstant(
            method = "<init>(Lnet/minecraft/world/World;DDD)V",
            constant = @Constant(intValue = 1)
    )
    public int rlmixins_vanillaParticle_init(int constant) {
        Class<? extends Particle> clazz = ((Particle)(Object)(this)).getClass();
        Boolean returnable = rlmixins$particleCollisionMap.get(clazz);
        if(returnable == null) {
            returnable = ForgeConfigHandler.getParticleCollisionClasses().contains(clazz.getName());
            rlmixins$particleCollisionMap.put(clazz, returnable);
        }
        return returnable ? 1 : 0;
    }
}