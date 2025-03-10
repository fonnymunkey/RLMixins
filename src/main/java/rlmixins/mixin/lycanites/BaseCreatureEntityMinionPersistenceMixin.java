package rlmixins.mixin.lycanites;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntityMinionPersistenceMixin {

    /*
    *   To catch every case as not every is a Summon Minion Goal
    *   Mostly solves Asmodeus Invincible scenarios when onMinionDeath is not fired by despawning
    *   Affects Rahovart and Amalgalich minion too
     */
    @Inject(
            method = "summonMinion",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"),
            remap = true
    )
    protected void rlmixins_lycanitesMobsSummonMinionsGoal_summonMinion(EntityLivingBase minion, double angle, double distance, CallbackInfo ci){
        if(minion instanceof BaseCreatureEntity) ((BaseCreatureEntity)minion).enablePersistence();
        else if(minion instanceof EntityLiving) ((EntityLiving)minion).enablePersistence();
    }
}
