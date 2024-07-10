package rlmixins.mixin.fishsundeadrising;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.EntitySkeletonKing;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntitySkeletonKing.class)
public abstract class EntitySkeletonKingMixin {

    @Inject(
            method = "getLootTable",
            at = @At("HEAD"),
            cancellable = true
    )
    public void rlmixins_fishsUndeadRisingEntitySkeletonKing(CallbackInfoReturnable<ResourceLocation> cir) {
        cir.setReturnValue(!Modconfig.SkeletonKing_Loot_Option ? LootTableHandler.SKELETON_KING : null);
    }
}