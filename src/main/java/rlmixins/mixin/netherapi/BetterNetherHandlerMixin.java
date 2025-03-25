package rlmixins.mixin.netherapi;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import git.jbredwards.nether_api.mod.common.compat.betternether.BetterNetherHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import paulevs.betternether.biomes.NetherBiome;

import java.util.Set;
import java.util.function.ObjIntConsumer;

@Mixin(BetterNetherHandler.class)
public class BetterNetherHandlerMixin {
    @WrapWithCondition(
            method = "registerBiomes(Lnet/minecraftforge/event/RegistryEvent$Register;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/function/ObjIntConsumer;accept(Ljava/lang/Object;I)V"),
            remap = false
    )
    private static boolean rlmixins_betterNetherHandler_registerBiomes_consumerAccept(ObjIntConsumer<NetherBiome> instance, Object biome, int biomeId){
        return biome != null;
    }

    @WrapWithCondition(
            method = "registerBiomes(Lnet/minecraftforge/event/RegistryEvent$Register;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z"),
            remap = false
    )
    private static boolean rlmixins_betterNetherHandler_registerBiomes_setAdd(Set<NetherBiome> instance, Object biome){
        return biome != null;
    }
}
