package rlmixins.mixin.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.wrapper.BlightWrapper;
import rlmixins.wrapper.ChampionWrapper;
import rlmixins.wrapper.InfernalWrapper;

@Mixin(EntityMinecart.class)
public abstract class EntityMinecartMixin {

    /**
     * Fix infernal/blight mobs getting stuck in minecarts
     */
    @Redirect(
            method = "onUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;startRiding(Lnet/minecraft/entity/Entity;)Z")
    )
    public boolean rlmixins_vanillaEntityMinecart_onUpdate(Entity instance, Entity entityIn) {
        if(instance instanceof EntityLivingBase && (
                (Loader.isModLoaded("scalinghealth") && BlightWrapper.isEntityBlight((EntityLivingBase)instance)) ||
                (Loader.isModLoaded("infernalmobs") && InfernalWrapper.isEntityInfernal((EntityLivingBase)instance)) ||
                (Loader.isModLoaded("champions") && instance instanceof EntityLiving &&  ChampionWrapper.isEntityChampion((EntityLiving)instance)))
        ) entityIn.applyEntityCollision(instance);
        else instance.startRiding(entityIn);
        return true;
    }
}
