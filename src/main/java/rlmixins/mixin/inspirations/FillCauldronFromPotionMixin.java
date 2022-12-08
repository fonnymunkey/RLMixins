package rlmixins.mixin.inspirations;

import knightminer.inspirations.library.recipe.cauldron.ICauldronRecipe;
import knightminer.inspirations.recipes.recipe.FillCauldronFromPotion;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FillCauldronFromPotion.class)
public abstract class FillCauldronFromPotionMixin {

    /**
     * Make incorrect cauldron mixing return Mundane potion instead of Thick potion
     */
    @Redirect(
            method = "getState",
            at = @At(value = "INVOKE", target = "Lknightminer/inspirations/library/recipe/cauldron/ICauldronRecipe$CauldronState;potion(Lnet/minecraft/potion/PotionType;)Lknightminer/inspirations/library/recipe/cauldron/ICauldronRecipe$CauldronState;", ordinal = 1),
            remap = false
    )
    public ICauldronRecipe.CauldronState rlmixins_inspirationsFillCauldronFromPotion_getState(PotionType potion) {
        return ICauldronRecipe.CauldronState.potion(PotionTypes.MUNDANE);
    }

    /**
     * Make incorrect cauldron mixin play an explosion sound instead
     */
    @Inject(
            method = "getSound",
            at = @At("RETURN"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_inspirationsFillCauldronFromPotion_getState(ItemStack stack, boolean boiling, int level, ICauldronRecipe.CauldronState state, CallbackInfoReturnable<SoundEvent> cir) {
        if(cir.getReturnValue().equals(SoundEvents.BLOCK_FIRE_EXTINGUISH)) cir.setReturnValue(SoundEvents.ENTITY_GENERIC_EXPLODE);
    }
}