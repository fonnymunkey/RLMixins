package rlmixins.mixin.betterfoliage;

import mods.octarinecore.common.config.ConfigurableBlockMatcher;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.concurrent.ConcurrentHashMap;

@Mixin(ConfigurableBlockMatcher.class)
public abstract class ConfigurableBlockMatcherMixin {

    @Unique
    private final ConcurrentHashMap<Class<?>, Boolean> rlmixins$matchesMap = new ConcurrentHashMap<>();

    @Inject(
            method = "matchesClass",
            at = @At(value = "INVOKE", target = "Lmods/octarinecore/common/config/ConfigurableBlockMatcher;getBlackList()Ljava/util/List;", shift = At.Shift.BEFORE),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD,
            remap = false
    )
    public void rlmixins_betterFoliageConfigurableBlockMatcher_matchesClass_inject0(Block block, CallbackInfoReturnable<Boolean> cir, Class<?> blockClass) {
        Boolean returnable = this.rlmixins$matchesMap.get(blockClass);
        if(returnable != null) {
            cir.setReturnValue(returnable);
        }
    }

    @Inject(
            method = "matchesClass",
            at = @At("RETURN"),
            remap = false
    )
    public void rlmixins_betterFoliageConfigurableBlockMatcher_matchesClass_inject1(Block block, CallbackInfoReturnable<Boolean> cir) {
        this.rlmixins$matchesMap.putIfAbsent(block.getClass(), cir.getReturnValue());
    }
}