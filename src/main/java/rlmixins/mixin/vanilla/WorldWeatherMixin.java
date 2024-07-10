package rlmixins.mixin.vanilla;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import rlmixins.handlers.ForgeConfigHandler;

@Mixin(World.class)
public abstract class WorldWeatherMixin {

    @ModifyConstant(
            method = "updateWeatherBody",
            constant = @Constant(intValue = 12000, ordinal = 0),
            remap = false
    )
    public int rlmixins_vanillaWorld_updateWeatherBody_thunderActiveRange(int constant) {
        return ForgeConfigHandler.server.thunderActiveRange;
    }

    @ModifyConstant(
            method = "updateWeatherBody",
            constant = @Constant(intValue = 3600),
            remap = false
    )
    public int rlmixins_vanillaWorld_updateWeatherBody_thunderActiveMin(int constant) {
        return ForgeConfigHandler.server.thunderActiveMinimum;
    }

    @ModifyConstant(
            method = "updateWeatherBody",
            constant = @Constant(intValue = 168000, ordinal = 0),
            remap = false
    )
    public int rlmixins_vanillaWorld_updateWeatherBody_thunderInactiveRange(int constant) {
        return ForgeConfigHandler.server.thunderInactiveRange;
    }

    @ModifyConstant(
            method = "updateWeatherBody",
            constant = @Constant(intValue = 12000, ordinal = 1),
            remap = false
    )
    public int rlmixins_vanillaWorld_updateWeatherBody_thunderInactiveMin(int constant) {
        return ForgeConfigHandler.server.thunderInactiveMinimum;
    }

    @ModifyConstant(
            method = "updateWeatherBody",
            constant = @Constant(intValue = 12000, ordinal = 2),
            remap = false
    )
    public int rlmixins_vanillaWorld_updateWeatherBody_rainActiveRange(int constant) {
        return ForgeConfigHandler.server.rainActiveRange;
    }

    @ModifyConstant(
            method = "updateWeatherBody",
            constant = @Constant(intValue = 12000, ordinal = 3),
            remap = false
    )
    public int rlmixins_vanillaWorld_updateWeatherBody_rainActiveMin(int constant) {
        return ForgeConfigHandler.server.rainActiveMinimum;
    }

    @ModifyConstant(
            method = "updateWeatherBody",
            constant = @Constant(intValue = 168000, ordinal = 1),
            remap = false
    )
    public int rlmixins_vanillaWorld_updateWeatherBody_rainInactiveRange(int constant) {
        return ForgeConfigHandler.server.rainInactiveRange;
    }

    @ModifyConstant(
            method = "updateWeatherBody",
            constant = @Constant(intValue = 12000, ordinal = 4),
            remap = false
    )
    public int rlmixins_vanillaWorld_updateWeatherBody_rainInactiveMin(int constant) {
        return ForgeConfigHandler.server.rainInactiveMinimum;
    }
}