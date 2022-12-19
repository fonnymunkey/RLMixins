package rlmixins.mixin.champions;

import c4.champions.common.affix.affix.AffixInfested;
import c4.champions.common.capability.IChampionship;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(AffixInfested.class)
public abstract class AffixInfestedMixin {

    @Inject(
            method = "spawnParasites",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void rlmixins_championsAffixInfested_spawnParasites(World world, BlockPos pos, int amount, boolean isEnder, CallbackInfoReturnable<List<EntityLiving>> cir, List parasites, int i, EntityLiving para, IChampionship chp) {
        para.getEntityData().setBoolean("xat:summoned", true);
    }
}