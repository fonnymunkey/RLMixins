package rlmixins.mixin.champions;

import c4.champions.common.rank.Rank;
import c4.champions.common.rank.RankManager;
import c4.champions.common.util.ChampionHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityOwnable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.wrapper.InfernalWrapper;

@Mixin(ChampionHelper.class)
public abstract class ChampionHelperMixin {

    /**
     * Make Champions not be able to make Ownable entities or Infernals a Champion
     */
    @Inject(
            method = "generateRank",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_championsChampionHelper_generateRank(EntityLiving entityLivingIn, CallbackInfoReturnable<Rank> cir) {
        if(entityLivingIn != null && (entityLivingIn instanceof IEntityOwnable || InfernalWrapper.isEntityInfernal(entityLivingIn))) cir.setReturnValue(RankManager.getEmptyRank());
    }
}