package rlmixins.mixin.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.wrapper.InFWrapper;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerDismountMixin {

    @Redirect(
            method = "updateRidden",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;isRiding()Z")
    )
    public boolean rlmixins_vanillaEntityPlayer_updateRidden(EntityPlayer player) {
        if(player.isRiding()) {
            Entity mount = player.getRidingEntity();
            if(mount != null && !mount.isDead && mount instanceof EntityLivingBase && ((EntityLivingBase)mount).getHealth() > 0.0F) {
                if(InFWrapper.isDragon(mount)) {
                    return InFWrapper.canDismountDragon(mount);
                }
                else if(InFWrapper.isCyclops(mount)) {
                    return InFWrapper.canDismountCyclops(mount);
                }
            }
            return true;
        }
        return false;
    }
}
