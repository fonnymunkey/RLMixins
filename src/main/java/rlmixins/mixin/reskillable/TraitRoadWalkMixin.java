package rlmixins.mixin.reskillable;

import codersafterdark.reskillable.skill.agility.TraitRoadWalk;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TraitRoadWalk.class)
public abstract class TraitRoadWalkMixin {

    /*
    *   Changes check for Path Block into a check if Block Pos can see sky
     */
    @Inject(
            method = "onPlayerTick",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void rlmixins_reskillableTraitRoadWalk_onPlayerTick(TickEvent.PlayerTickEvent event, CallbackInfo ci){
        if (event.player.world.canSeeSky(event.player.getPosition()) && event.player.onGround && event.player.moveForward > 0.0F) {
            event.player.moveRelative(0.0F, 0.0F, 1.0F, 0.05F);
        }
        ci.cancel();
    }
}
