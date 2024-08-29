package rlmixins.mixin.astikor;

import de.mennomax.astikorcarts.entity.AbstractDrawn;
import de.mennomax.astikorcarts.entity.EntityMobCart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.handlers.ForgeConfigHandler;
import rlmixins.util.ModLoadedUtil;
import rlmixins.wrapper.BlightWrapper;
import rlmixins.wrapper.ChampionWrapper;
import rlmixins.wrapper.InfernalWrapper;

@Mixin(EntityMobCart.class)
public abstract class EntityMobCartMixin extends AbstractDrawn {

    public EntityMobCartMixin(World worldIn) {
        super(worldIn);
    }

    @Inject(
            method = "applyEntityCollision",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;startRiding(Lnet/minecraft/entity/Entity;)Z", shift = At.Shift.BEFORE),
            cancellable = true
    )
    public void rlmixins_astikorCarts_applyEntityCollision(Entity entity, CallbackInfo ci) {
        if((entity instanceof IMob && ForgeConfigHandler.server.mobCartCheese) ||
                entity instanceof EntityLivingBase && (
                (ModLoadedUtil.isScalingHealthLoaded() && BlightWrapper.isEntityBlight((EntityLivingBase)entity)) ||
                        (ModLoadedUtil.isInfernalMobsLoaded() && InfernalWrapper.isEntityInfernal((EntityLivingBase)entity)) ||
                        (ModLoadedUtil.isChampionsLoaded() && entity instanceof EntityLiving &&  ChampionWrapper.isEntityChampion((EntityLiving)entity)))
        ) {
            super.applyEntityCollision(entity);
            ci.cancel();
        }
    }
}