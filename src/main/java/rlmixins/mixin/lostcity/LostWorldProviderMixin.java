package rlmixins.mixin.lostcity;

import mcjty.lostcities.dimensions.world.LostWorldProvider;
import net.minecraft.world.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LostWorldProvider.class)
public abstract class LostWorldProviderMixin extends WorldProvider {

    @Override
    public boolean canRespawnHere() {
        return false;
    }
}