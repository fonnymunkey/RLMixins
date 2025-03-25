package rlmixins.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.util.ParasiteEventEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.wrapper.CallableHorsesWrapper;

@Mixin(ParasiteEventEntity.class)
public abstract class ParasiteEventEntityConvertHorseMixin {

    /*
     *   Consider these cases as callable horse kill to prevent duping
     */
    @Inject(
            method = "convertEntity",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;removeEntity(Lnet/minecraft/entity/Entity;)V"),
            remap = true
    )
    private static void rlmixins_srparasitesParasiteEventEntity_convertEntity(EntityLivingBase entityin, NBTTagCompound tags, boolean ignoreKey, String[] list, CallbackInfo ci){
        CallableHorsesWrapper.applyCallableHorseDeath(entityin);
    }

    @Inject(
            method = "spawnInsider",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;removeEntity(Lnet/minecraft/entity/Entity;)V"),
            remap = true
    )
    private static void rlmixins_srparasitesParasiteEventEntity_spawnInsider(EntityLivingBase entity, World world, NBTTagCompound tags, CallbackInfo ci){
        CallableHorsesWrapper.applyCallableHorseDeath(entity);
    }

    @Inject(
            method = "convertEntityFeral",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;removeEntity(Lnet/minecraft/entity/Entity;)V"),
            remap = true
    )
    private static void rlmixins_srparasitesParasiteEventEntity_convertEntityFeral(EntityLivingBase entityin, NBTTagCompound tags, boolean ignoreKey, String[] list, CallbackInfoReturnable<Boolean> cir){
        CallableHorsesWrapper.applyCallableHorseDeath(entityin);
    }

    @Inject(
            method = "hijackEntity",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;removeEntity(Lnet/minecraft/entity/Entity;)V"),
            remap = true
    )
    private static void rlmixins_srparasitesParasiteEventEntity_hijackEntity(EntityLivingBase entityin, String[] list, CallbackInfoReturnable<Boolean> cir){
        CallableHorsesWrapper.applyCallableHorseDeath(entityin);
    }
}
