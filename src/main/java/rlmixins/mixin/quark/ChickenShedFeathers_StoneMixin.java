package rlmixins.mixin.quark;

import com.github.alexthe666.iceandfire.api.IEntityEffectCapability;
import com.github.alexthe666.iceandfire.api.InFCapabilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.quark.tweaks.feature.ChickensShedFeathers;

@Mixin(ChickensShedFeathers.class)
public abstract class ChickenShedFeathers_StoneMixin {

    @Inject(
            method = "onLivingUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/EntityChicken;dropItem(Lnet/minecraft/item/Item;I)Lnet/minecraft/entity/item/EntityItem;"),
            cancellable = true
    )
    private void rlmixins_quarkChickenShedFeathers_onLivingUpdate(LivingEvent.LivingUpdateEvent event, CallbackInfo ci) {
        if(event.getEntity() instanceof EntityLivingBase) {
            IEntityEffectCapability capability = InFCapabilities.getEntityEffectCapability((EntityLivingBase)event.getEntity());
            if(capability != null && capability.isStoned()) ci.cancel();
        }
    }
}