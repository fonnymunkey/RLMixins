package rlmixins.mixin.potioncore;

import com.tmtravlr.potioncore.potion.PotionReach;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PotionReach.class)
public abstract class PotionReachMixin {

    @Redirect(
            method = "registerPotionAttributeModifiers",
            at = @At(value = "INVOKE", target = "Lcom/tmtravlr/potioncore/potion/PotionReach;registerPotionAttributeModifier(Lnet/minecraft/entity/ai/attributes/IAttribute;Ljava/lang/String;DI)Lnet/minecraft/potion/Potion;")
    )
    public Potion rlmixins_potionCorePotionReach_registerPotionAttributeModifiers(PotionReach instance, IAttribute iAttribute, String s, double v, int i) {
        return instance.registerPotionAttributeModifier(EntityPlayer.REACH_DISTANCE, "3944821f-9c5b-41a3-8b4c-146447f591d3", 0.5D, 0);
    }
}