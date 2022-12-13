package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_4_0.EnchantmentSubjectEnchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentSubjectEnchantments.class)
public abstract class EnchantmentSubjectEnchantmentsMixin {

    /**
     * Fix Subject enchantments causing clientside desync
     */
    @Inject(
            method = "onEntityDamagedAlt",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_soManyEnchantmentsEnchantmentSubjectEnchantments_onEntityDamagedAlt(EntityLivingBase user, Entity target, ItemStack stack, int level, CallbackInfo ci) {
        if(target == null || target.world.isRemote) ci.cancel();
    }
}
