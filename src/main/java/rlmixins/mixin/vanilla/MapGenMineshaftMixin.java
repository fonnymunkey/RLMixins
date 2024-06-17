package rlmixins.mixin.vanilla;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraftforge.common.BiomeDictionary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.Set;

@Mixin(MapGenMineshaft.class)
public abstract class MapGenMineshaftMixin extends MapGenStructure {

    @Inject(
            method = "canSpawnStructureAtCoords",
            at = @At("RETURN"),
            cancellable = true
    )
    public void rlmixins_vanillaMapGenMineshaft_canSpawnStructureAtCoords(int chunkX, int chunkZ, CallbackInfoReturnable<Boolean> cir) {
        if(cir.getReturnValue()) {
            Biome biome = ((MapGenBaseAccessor)this).getWorld().getBiome(new BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8));
            String biomeName =  biome.getRegistryName() != null ? biome.getRegistryName().toString() : "";
            Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(biome);

            if(ForgeConfigHandler.getMineshaftBiomeNameBlacklist().contains(biomeName)) {
                cir.setReturnValue(false);
                return;
            }
            for(BiomeDictionary.Type type : ForgeConfigHandler.getMineshaftBiomeTypesBlacklist()) {
                if(types.contains(type)) {
                    cir.setReturnValue(false);
                    return;
                }
            }
        }
    }
}