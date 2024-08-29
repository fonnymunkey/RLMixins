package rlmixins.mixin.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.monster.IMob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.ForgeConfigHandler;
import rlmixins.util.ModLoadedUtil;
import rlmixins.wrapper.BlightWrapper;
import rlmixins.wrapper.ChampionWrapper;
import rlmixins.wrapper.InfernalWrapper;

@Mixin(EntityBoat.class)
public abstract class EntityBoatMixin {

    /**
     * Fix infernal/blight mobs getting stuck in boats
     */
    @Redirect(
            method = "onUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;startRiding(Lnet/minecraft/entity/Entity;)Z")
    )
    public boolean rlmixins_vanillaEntityBoat_onUpdate(Entity instance, Entity entityIn) {
        if((instance instanceof IMob && ForgeConfigHandler.server.mobCartCheese) ||
                instance instanceof EntityLivingBase && (
                (ModLoadedUtil.isScalingHealthLoaded() && BlightWrapper.isEntityBlight((EntityLivingBase)instance)) ||
                (ModLoadedUtil.isInfernalMobsLoaded() && InfernalWrapper.isEntityInfernal((EntityLivingBase)instance)) ||
                (ModLoadedUtil.isChampionsLoaded() && instance instanceof EntityLiving &&  ChampionWrapper.isEntityChampion((EntityLiving)instance)))
        ) entityIn.applyEntityCollision(instance);
        else instance.startRiding(entityIn);
        return true;
    }
}
