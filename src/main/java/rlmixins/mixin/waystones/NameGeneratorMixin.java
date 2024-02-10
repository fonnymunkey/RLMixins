package rlmixins.mixin.waystones;

import net.blay09.mods.waystones.worldgen.NameGenerator;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(NameGenerator.class)
public abstract class NameGeneratorMixin {

    @Inject(
            method = "readFromNBT",
            at = @At("HEAD"),
            cancellable = true
    )
    public void rlmixins_waystonesNameGenerator_readFromNBT(NBTTagCompound compound, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "writeToNBT",
            at = @At("HEAD"),
            cancellable = true
    )
    public void rlmixins_waystonesNameGenerator_writeToNBT(NBTTagCompound compound, CallbackInfoReturnable<NBTTagCompound> cir) {
        cir.setReturnValue(compound);
    }

    @Redirect(
            method = "getName(Lnet/minecraft/util/math/BlockPos;ILnet/minecraft/world/biome/Biome;Ljava/util/Random;)Ljava/lang/String;",
            at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z"),
            remap = false
    )
    public boolean rlmixins_waystonesNameGenerator_getName(Set<String> instance, Object e) {
        return false;
    }
}