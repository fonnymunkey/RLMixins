package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.item.ItemGorgonHead;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemGorgonHead.class)
public abstract class ItemGorgonHeadMixin extends Item {

    @Inject(
            method = "onPlayerStoppedUsing",
            at = @At("HEAD"),
            cancellable = true
    )
    public void rlmixins_iceAndFireItemGorgonHead_onPlayerStoppedUsing_head(ItemStack stack, World worldIn, EntityLivingBase entity, int timeLeft, CallbackInfo ci) {
        if(worldIn.isRemote) {
            stack.setItemDamage(0);
            ci.cancel();
        }
    }

    @Redirect(
            method = "onPlayerStoppedUsing",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;playSound(Lnet/minecraft/util/SoundEvent;FF)V", ordinal = 0)
    )
    public void rlmixins_iceAndFireItemGorgonHead_onPlayerStoppedUsing_playSound0(EntityLivingBase instance, SoundEvent soundEvent, float vol, float pit) {
        instance.world.playSound(null, instance.posX, instance.posY, instance.posZ, soundEvent, SoundCategory.PLAYERS, vol, pit);
    }

    @Redirect(
            method = "onPlayerStoppedUsing",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;playSound(Lnet/minecraft/util/SoundEvent;FF)V", ordinal = 1)
    )
    public void rlmixins_iceAndFireItemGorgonHead_onPlayerStoppedUsing_playSound1(EntityLivingBase instance, SoundEvent soundEvent, float vol, float pit) {
        instance.world.playSound(null, instance.posX, instance.posY, instance.posZ, soundEvent, SoundCategory.PLAYERS, vol, pit);
    }

    @Redirect(
            method = "onPlayerStoppedUsing",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;playSound(Lnet/minecraft/util/SoundEvent;FF)V", ordinal = 2)
    )
    public void rlmixins_iceAndFireItemGorgonHead_onPlayerStoppedUsing_playSound2(EntityLivingBase instance, SoundEvent soundEvent, float vol, float pit) {
        instance.world.playSound(null, instance.posX, instance.posY, instance.posZ, soundEvent, SoundCategory.PLAYERS, vol, pit);
    }
}