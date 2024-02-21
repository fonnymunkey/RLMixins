package rlmixins.mixin.vanilla;

import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

@Mixin(EntityTippedArrow.class)
public abstract class EntityTippedArrowColorMixin {

    @Shadow private PotionType potion;

    @Shadow @Final private Set<PotionEffect> customPotionEffects;

    @Redirect(
            method = "refreshColor",
            at = @At(value = "INVOKE", target = "Ljava/lang/Integer;valueOf(I)Ljava/lang/Integer;")
    )
    public Integer rlmixins_vanillaEntityTippedArrow_refreshColor(int i) {
        if(potion == PotionTypes.EMPTY && customPotionEffects.isEmpty()) return -1;
        return i;
    }
}