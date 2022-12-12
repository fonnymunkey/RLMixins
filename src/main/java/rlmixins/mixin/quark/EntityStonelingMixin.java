package rlmixins.mixin.quark;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.quark.world.entity.EntityStoneling;

@Mixin(EntityStoneling.class)
public abstract class EntityStonelingMixin {

    private boolean dropped;

    /**
     * Fix attempting to take an item from a Stoneling while killing it with offhand duping the item
     */
    @Inject(
            method = "applyPlayerInteraction",
            at = @At("HEAD"),
            cancellable = true
    )
    public void rlmixins_quarkEntityStoneling_applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand, CallbackInfoReturnable<EnumActionResult> cir) {
        if(this.dropped) {
            this.dropped = false;
            cir.setReturnValue(EnumActionResult.PASS);
        }
    }

    @Inject(
            method = "dropEquipment",
            at = @At("HEAD")
    )
    public void rlmixins_quarkEntityStoneling_dropEquipment(boolean wasRecentlyHit, int lootingModifier, CallbackInfo ci) {
        this.dropped = true;
    }
}