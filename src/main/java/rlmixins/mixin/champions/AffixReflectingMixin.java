package rlmixins.mixin.champions;

import c4.champions.common.affix.affix.AffixReflecting;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AffixReflecting.class)
public abstract class AffixReflectingMixin {
    @ModifyConstant(
            method = "onDamaged",
            constant = @Constant(stringValue = "reflecting"),
            remap = false
    )
    private String rlmixins_championsAffixReflecting_onDamaged(String constant, @Local(argsOnly = true) DamageSource source){
        return source.damageType; //don't run source.damageType = "reflecting" on the original damage source
    }

    @WrapOperation(
            method = "onDamaged",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EntityDamageSource;setIsThornsDamage()Lnet/minecraft/util/EntityDamageSource;")
    )
    private EntityDamageSource rlmixins_championsAffixReflecting_onDamaged(EntityDamageSource instance, Operation<EntityDamageSource> original){
        return instance; //don't run setIsThornsDamage on the original damage source
    }

    @WrapOperation(
            method = "onDamaged",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z")
    )
    private boolean rlmixins_championsAffixReflecting_onDamaged(EntityLivingBase instance, DamageSource source, float amount, Operation<Boolean> original, @Local(argsOnly = true) EntityLiving champion){
        //Use a new DamageSource instead of the current one
        DamageSource newSource = new EntityDamageSource("reflecting", champion).setIsThornsDamage(); //for some reason this is not magic damage, so not actual thorns
        return original.call(instance, newSource, amount);
    }
}