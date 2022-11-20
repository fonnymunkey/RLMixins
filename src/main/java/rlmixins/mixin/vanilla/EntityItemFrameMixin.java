package rlmixins.mixin.vanilla;

import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityItemFrame.class)
public abstract class EntityItemFrameMixin {

    /**
     * Don't try to place and item in an item frame and remove the item from the player inventory if the item frame is already dead
     * Fixes vanilla bug where right + left clicking on a frame at the same time can void items
     */
    @Inject(
            method = "processInitialInteract",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityItemFrame;setDisplayedItem(Lnet/minecraft/item/ItemStack;)V"),
            cancellable = true
    )
    public void rlmixins_vanillaEntityItemFrame_processInitialInteract(EntityPlayer player, EnumHand hand, CallbackInfoReturnable<Boolean> cir) {
        if(((EntityItemFrame)(Object)this).isDead) cir.setReturnValue(false);
    }
}