package rlmixins.mixin.vanilla;

import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import rlmixins.wrapper.IWorldProvderSleepMixin;

@Mixin(WorldProvider.class)
public abstract class WorldProviderSleepMixin implements IWorldProvderSleepMixin {

    @Shadow
    protected World world;

    @Override
    public void rlmixins$resetWeatherConditional() {
        if(world.getWorldInfo().isRaining()) {
            world.getWorldInfo().setRainTime(0);
            world.getWorldInfo().setRaining(false);
        }
        if(world.getWorldInfo().isThundering()) {
            world.getWorldInfo().setThunderTime(0);
            world.getWorldInfo().setThundering(false);
        }
    }
}