package rlmixins.mixin.serene;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import sereneseasons.api.season.BiomeHooks;
import sereneseasons.api.season.Season;

import java.lang.reflect.Method;

@Mixin(BiomeHooks.class)
public abstract class BiomeHooksMixin {

    private static Method worldTempMethod;
    private static Method seasonTempMethod;

    /**
     * @author fonnymunkey
     * @reason rewrite for performance
     */
    @Overwrite(remap = false)
    public static float getFloatTemperature(World world, Biome biome, BlockPos pos) {
        try {
            if(worldTempMethod == null) worldTempMethod =
                    Class.forName("sereneseasons.season.SeasonASMHelper")
                    .getMethod("getFloatTemperature", World.class, Biome.class, BlockPos.class);
            return (float)worldTempMethod.invoke(null, world, biome, pos);
        }
        catch (Exception var4) {
            throw new RuntimeException("An error occurred calling getFloatTemperature", var4);
        }
    }

    /**
     * @author fonnymunkey
     * @reason rewrite for performance
     */
    @Overwrite(remap = false)
    public static float getFloatTemperature(Season.SubSeason subSeason, Biome biome, BlockPos pos) {
        try {
            if(seasonTempMethod == null) seasonTempMethod =
                    Class.forName("sereneseasons.season.SeasonASMHelper")
                    .getMethod("getFloatTemperature", Season.SubSeason.class, Biome.class, BlockPos.class);
            return (float)seasonTempMethod.invoke(null, subSeason, biome, pos);
        } catch (Exception var4) {
            throw new RuntimeException("An error occurred calling getFloatTemperature", var4);
        }
    }
}