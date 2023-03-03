package rlmixins.mixin.bettersurvival;

import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentVampirism;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentVampirism.class)
public abstract class EnchantmentVampirismMixin {

    @Inject(
            method = "onEntityDamaged",
            at = @At("HEAD"),
            cancellable = true
    )
    public void rlmixins_betterSurvivalEnchantmentVampirism_onEntityDamaged(EntityLivingBase user, Entity target, int level, CallbackInfo ci) {
        if(!(target instanceof EntityLiving)) ci.cancel();
    }
}