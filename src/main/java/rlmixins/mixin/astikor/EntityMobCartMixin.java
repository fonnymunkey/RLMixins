package rlmixins.mixin.astikor;

import de.mennomax.astikorcarts.entity.AbstractDrawn;
import de.mennomax.astikorcarts.entity.EntityMobCart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.handlers.ForgeConfigHandler;
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
                (Loader.isModLoaded("scalinghealth") && BlightWrapper.isEntityBlight((EntityLivingBase)entity)) ||
                        (Loader.isModLoaded("infernalmobs") && InfernalWrapper.isEntityInfernal((EntityLivingBase)entity)) ||
                        (Loader.isModLoaded("champions") && entity instanceof EntityLiving &&  ChampionWrapper.isEntityChampion((EntityLiving)entity)))
        ) {
            super.applyEntityCollision(entity);
            ci.cancel();
        }
    }
}