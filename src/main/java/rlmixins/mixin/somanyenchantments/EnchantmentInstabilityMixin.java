package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_4_5.EnchantmentInstability;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentInstability.class)
public abstract class EnchantmentInstabilityMixin {

    @Inject(
            method = "onAttack",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_soManyEnchantmentsEnchantmentInstability_onAttack(LivingDamageEvent e, CallbackInfo ci) {
        if (e.getSource() != null && e.getSource().getTrueSource() instanceof EntityLivingBase) {
            EntityLivingBase attacker = (EntityLivingBase) e.getSource().getTrueSource();
            if (!attacker.getHeldItemMainhand().isEmpty()) {
                ItemStack stack = attacker.getHeldItemMainhand();
                if (stack.getMaxDamage() <= 0) {
                    ci.cancel();
                }
            }
        }
    }
}