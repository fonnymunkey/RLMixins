package rlmixins.wrapper;

import net.minecraft.client.settings.GameSettings;
import rlmixins.mixin.vanilla.GameSettingsOptionsMixin;

public abstract class GameSettingsWrapper {

    public static void setMinGamma(float min) {
        try {
            ((GameSettingsOptionsMixin)(Object)GameSettings.Options.GAMMA).setValueMin(min);
        }
        catch(Exception ignored) {}
    }

    public static void setMaxGamma(float max) {
        GameSettings.Options.GAMMA.setValueMax(max);
    }
}