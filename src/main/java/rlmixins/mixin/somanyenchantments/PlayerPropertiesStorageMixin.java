package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Prop_Sector.IPlayerProperties;
import com.Shultrea.Rin.Prop_Sector.PlayerPropertiesStorage;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerPropertiesStorage.class)
public abstract class PlayerPropertiesStorageMixin {

    @Inject(
            method = "writeNBT(Lnet/minecraftforge/common/capabilities/Capability;Lcom/Shultrea/Rin/Prop_Sector/IPlayerProperties;Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/nbt/NBTBase;",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_soManyEnchantmentsPlayerPropertiesStorage_writeNBT(Capability<IPlayerProperties> capability, IPlayerProperties instance, EnumFacing side, CallbackInfoReturnable<NBTBase> cir) {
        cir.setReturnValue(new NBTTagCompound());
    }

    @Inject(
            method = "readNBT(Lnet/minecraftforge/common/capabilities/Capability;Lcom/Shultrea/Rin/Prop_Sector/IPlayerProperties;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/nbt/NBTBase;)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_soManyEnchantmentsPlayerPropertiesStorage_readNBT(Capability<IPlayerProperties> capability, IPlayerProperties instance, EnumFacing side, NBTBase nbt, CallbackInfo ci) {
        ci.cancel();
    }
}