package rlmixins.mixin.quark;

import com.github.alexthe666.iceandfire.entity.StoneEntityProperties;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.quark.tweaks.feature.ChickensShedFeathers;

@Mixin(ChickensShedFeathers.class)
public abstract class ChickenShedFeathersMixin {

    @Inject(
            method = "onLivingUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/EntityChicken;dropItem(Lnet/minecraft/item/Item;I)Lnet/minecraft/entity/item/EntityItem;"),
            cancellable = true
    )
    public void rlmixins_quarkChickenShedFeathers_onLivingUpdate(LivingEvent.LivingUpdateEvent event, CallbackInfo ci) {
        StoneEntityProperties properties = (StoneEntityProperties) EntityPropertiesHandler.INSTANCE.getProperties(event.getEntity(), StoneEntityProperties.class);
        if(properties != null && properties.isStone) ci.cancel();
    }
}