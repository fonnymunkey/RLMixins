package rlmixins.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.item.ItemTrinketBrokenHeart;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//TODO: Finish Broken Heart compat
@Mixin(ItemTrinketBrokenHeart.class)
public abstract class ItemTrinketBrokenHeartMixin {

    /**
     * Cancel's Bountiful Bauble's Broken Heart handling so it can be subscribed to First Aid's event instead
     */
    @Inject(
            method = "onDamage",
            at = @At(value = "HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_bountifulBaublesEventHandler_onDamageLivingDamage(LivingDamageEvent event, CallbackInfo ci) {
        ci.cancel();
    }

    /**
     * Cancel's Bountiful Bauble's Broken Heart healing so it can heal First Aid limbs instead
     */
    @Inject(
            method = "onPlayerWake",
            at = @At(value = "HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_bountifulBaublesEventHandler_onDamageLivingDamage(PlayerWakeUpEvent event, CallbackInfo ci) {
        ci.cancel();
    }
}