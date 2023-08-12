package rlmixins.mixin.qualitytools;

import com.tmtravlr.qualitytools.CommonEventHandler;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommonEventHandler.class)
public abstract class CommonEventHandlerMixin {

    @Inject(
            method = "onLivingUpdate",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_qualityToolsCommonEventHandler_onLivingUpdate(LivingEvent.LivingUpdateEvent event, CallbackInfo ci) {
        if(!(event.getEntityLiving() instanceof EntityPlayer) && !(event.getEntityLiving() instanceof EntityHorse && ((EntityHorse)event.getEntityLiving()).isTame())) ci.cancel();
    }
}