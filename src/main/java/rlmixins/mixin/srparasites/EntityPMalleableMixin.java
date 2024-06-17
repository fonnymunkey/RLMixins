package rlmixins.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPMalleable;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityPMalleable.class)
public abstract class EntityPMalleableMixin {

    @Redirect(
            method = "func_70097_a",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/DamageSource;func_76364_f()Lnet/minecraft/entity/Entity;"),
            remap = false
    )
    private Entity rlmixins_srparasitesEntityPMalleable_func_76364_f(DamageSource source) {
        if (source instanceof EntityDamageSourceIndirect) {
            return null;
        }
        return source.getImmediateSource();
    }

    @Redirect(
            method = "func_70097_a",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/DamageSource;func_76346_g()Lnet/minecraft/entity/Entity;"),
            remap = false
    )
    private Entity rlmixins_srparasitesEntityPMalleable_func_76346_g(DamageSource source) {
        if (source instanceof EntityDamageSourceIndirect) {
            return null;
        }
        return source.getTrueSource();
    }
}