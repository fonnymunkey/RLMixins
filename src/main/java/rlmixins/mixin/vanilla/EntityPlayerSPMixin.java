package rlmixins.mixin.vanilla;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityPlayerSP.class)
public abstract class EntityPlayerSPMixin extends EntityLivingBase {

    public EntityPlayerSPMixin(World worldIn) {
        super(worldIn);
    }

    /**
     * Recheck getHealth, since it can unexpectedly be capped lower after the setHealth method due to attribute changes and packet shenanigans
     * Hopefully fixes desync issue with death on clientside but not serverside
     */
    @Inject(
            method = "setPlayerSPHealth",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;damageEntity(Lnet/minecraft/util/DamageSource;F)V", shift = At.Shift.BEFORE),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void rlmixins_vanillaEntityPlayerSP_setPlayerSPHealth_inject(float health, CallbackInfo ci, float f) {
        float fNew = Math.max(this.getHealth() - health, 0.1F);
        this.lastDamage = fNew;//I don't know if this is really needed but why not
        this.damageEntity(DamageSource.GENERIC, fNew);
    }

    @Redirect(
            method = "setPlayerSPHealth",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;damageEntity(Lnet/minecraft/util/DamageSource;F)V")
    )
    public void rlmixins_vanillaEntityPlayerSP_setPlayerSPHealth_redirect(EntityPlayerSP instance, DamageSource damageSrc, float damageAmount) {
        //Do nothing, handle it above instead
    }
}
