package rlmixins.mixin.quark;

import com.github.alexthe666.iceandfire.entity.StoneEntityProperties;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.quark.tweaks.feature.ShearableChickens;

@Mixin(ShearableChickens.class)
public abstract class ShearableChickensMixin {

    @Inject(
            method = "onEntityInteract",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;getHeldItemMainhand()Lnet/minecraft/item/ItemStack;"),
            cancellable = true
    )
    public void rlmixins_quarkShearableChickens_onEntityInteract(PlayerInteractEvent.EntityInteract event, CallbackInfo ci) {
        StoneEntityProperties properties = (StoneEntityProperties) EntityPropertiesHandler.INSTANCE.getProperties(event.getTarget(), StoneEntityProperties.class);
        if(properties != null && properties.isStone) ci.cancel();
    }
}