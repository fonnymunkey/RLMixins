package rlmixins.mixin.vanilla;

import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameSettings.Options.class)
public interface GameSettingsOptionsMixin {

    @Accessor(value = "valueMin")
    void setValueMin(float valueMin);
}