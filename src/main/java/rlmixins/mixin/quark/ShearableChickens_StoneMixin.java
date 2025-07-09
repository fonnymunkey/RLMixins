package rlmixins.mixin.quark;

import com.github.alexthe666.iceandfire.api.IEntityEffectCapability;
import com.github.alexthe666.iceandfire.api.InFCapabilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.quark.tweaks.feature.ShearableChickens;

@Mixin(ShearableChickens.class)
public abstract class ShearableChickens_StoneMixin {

    @Inject(
            method = "onEntityInteract",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;getHeldItemMainhand()Lnet/minecraft/item/ItemStack;"),
            cancellable = true
    )
    private void rlmixins_quarkShearableChickens_onEntityInteract(PlayerInteractEvent.EntityInteract event, CallbackInfo ci) {
        if(event.getTarget() instanceof EntityLivingBase) {
            IEntityEffectCapability capability = InFCapabilities.getEntityEffectCapability((EntityLivingBase)event.getTarget());
            if(capability != null && capability.isStoned()) ci.cancel();
        }
    }
}