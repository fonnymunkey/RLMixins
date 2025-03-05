package rlmixins.mixin.switchbow;

import com.llamalad7.mixinextras.sugar.Local;
import de.Whitedraco.switchbow.event.EventHandler;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EventHandler.class)
public abstract class EventHandlerLootingFixMixin {

    // Fixes looting level setting that overrides other looting bonuses
    @ModifyArg(
            method = "LootingLevelEvent",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/entity/living/LootingLevelEvent;setLootingLevel(I)V"),
            remap = false
    )
    private int rlmixins_switchbowEventHandler_lootingLevelEvent(int lootingLevel, @Local(argsOnly = true) LootingLevelEvent e){
        return e.getLootingLevel() + lootingLevel;
    }
}
