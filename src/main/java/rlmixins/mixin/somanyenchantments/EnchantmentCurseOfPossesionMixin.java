package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_4_0.EnchantmentCurseofPossession;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.RLMixins;

@Mixin(EnchantmentCurseofPossession.class)
public abstract class EnchantmentCurseOfPossesionMixin {

    @Inject(
            method = "toss",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityItem;setNoPickupDelay()V"),
            remap = false
    )
    private void rlmixins_soManyEnchantmentsEnchantmentCurseOfPossesion_tossTrue(ItemTossEvent event, CallbackInfo ci) {
        RLMixins.LOGGER.log(Level.INFO, "Toss event true remote: " + event.getPlayer().world.isRemote);
    }

    @Inject(
            method = "toss",
            at = @At(value = "INVOKE", target = "Lcom/Shultrea/Rin/Ench0_4_0/EnchantmentCurseofPossession;damagePlayer(Lnet/minecraft/entity/player/EntityPlayer;)V"),
            remap = false
    )
    private void rlmixins_soManyEnchantmentsEnchantmentCurseOfPossesion_tossFalse(ItemTossEvent event, CallbackInfo ci) {
        RLMixins.LOGGER.log(Level.INFO, "Toss event false remote: " + event.getPlayer().world.isRemote);
    }
}
