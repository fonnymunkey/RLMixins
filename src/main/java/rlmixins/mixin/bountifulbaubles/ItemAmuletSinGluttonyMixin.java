package rlmixins.mixin.bountifulbaubles;

import com.jamieswhiteshirt.trumpetskeleton.common.item.ItemTrumpet;
import cursedflames.bountifulbaubles.item.ItemAmuletSinGluttony;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemAmuletSinGluttony.class)
public abstract class ItemAmuletSinGluttonyMixin {

    /**
     * Fix trumpets triggering the gluttony effect
     */
    @Redirect(
            method = "onItemUse(Lnet/minecraftforge/event/entity/living/LivingEntityUseItemEvent$Finish;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getItemUseAction(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/EnumAction;")
    )
    private static EnumAction rlmixins_bountifulBaublesItemAmuletSinGluttony_onItemUseFinish(Item instance, ItemStack stack) {
        return instance instanceof ItemTrumpet ? EnumAction.NONE : instance.getItemUseAction(stack);
    }

    @Inject(
            method = "onItemUse(Lnet/minecraftforge/event/entity/living/LivingEntityUseItemEvent$Tick;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/entity/living/LivingEntityUseItemEvent$Tick;setDuration(I)V", shift = At.Shift.BEFORE),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_bountifulBaublesItemAmuletSingGluttony_onItemUseTick(LivingEntityUseItemEvent.Tick event, CallbackInfo ci) {
        if(event.getItem().getItem().getItemUseAction(event.getItem()) == EnumAction.DRINK) ci.cancel();
    }

    @Inject(
            method = "onItemUse(Lnet/minecraftforge/event/entity/living/LivingEntityUseItemEvent$Start;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/entity/living/LivingEntityUseItemEvent$Start;setDuration(I)V", shift = At.Shift.BEFORE),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_bountifulBaublesItemAmuletSingGluttony_onItemUseStart(LivingEntityUseItemEvent.Start event, CallbackInfo ci) {
        if(event.getItem().getItem().getItemUseAction(event.getItem()) == EnumAction.DRINK) ci.cancel();
    }
}