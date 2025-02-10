package rlmixins.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.item.ItemShieldCobalt;
import net.minecraft.item.ItemShield;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemShieldCobalt.class)
public abstract class ItemShieldCobaltOnHitDuraMixin extends ItemShield {

    /**
     * Cancels Bountiful Bauble's custom Bauble Shield Durability handling
     * To be rehandled in ShieldBreak
     *
     * Plus when isShield() is true, also passed to vanilla taking extra durability damage
     * And losses keep shield on 0 durability feature of Bountiful Bauble
     */
    @Inject(
            method = "onLivingAttack",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void mixin(LivingAttackEvent event, CallbackInfo ci){
        ci.cancel();
    }
}