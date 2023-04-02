package rlmixins.mixin.potioncore;

import com.tmtravlr.potioncore.PotionCoreEventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotionCoreEventHandler.class)
public abstract class PotionCoreEventHandlerRevivalMixin {

    @Inject(
            method = "onLivingDeath",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_potionCoreEventHandler_onLivingDeath(LivingDeathEvent event, CallbackInfo ci) {
        if(!(event.getEntityLiving() instanceof EntityPlayer)) ci.cancel();
    }
}