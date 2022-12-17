package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_1_0.EnchantmentSwifterSlashes;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentSwifterSlashes.class)
public abstract class EnchantmentSwifterSlashesMixin {

    @Inject(
            method = "onEntityHit",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_soManyEnchantmentsEnchantmentSwifterSlashes_onEntityHit(AttackEntityEvent event, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "HandleEnchant(Lnet/minecraftforge/event/entity/living/LivingHurtEvent;)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_soManyEnchantmentsEnchantmentSwifterSlashes_handleEnchantment_livingHurt(LivingHurtEvent fEvent, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "HandleEnchant(Lnet/minecraftforge/event/entity/living/LivingEvent$LivingUpdateEvent;)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_soManyEnchantmentsEnchantmentSwifterSlashes_handleEnchantment_livingUpdate(LivingEvent.LivingUpdateEvent fEvent, CallbackInfo ci) {
        ci.cancel();
    }
}