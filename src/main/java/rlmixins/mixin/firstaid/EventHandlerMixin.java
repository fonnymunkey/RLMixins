package rlmixins.mixin.firstaid;

import ichttt.mods.firstaid.FirstAid;
import ichttt.mods.firstaid.api.damagesystem.AbstractPlayerDamageModel;
import ichttt.mods.firstaid.common.EventHandler;
import ichttt.mods.firstaid.common.network.MessageSyncDamageModel;
import ichttt.mods.firstaid.common.util.CommonUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EventHandler.class)
public abstract class EventHandlerMixin {

    @Inject(
            method = "onLivingHurt",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/entity/living/LivingHurtEvent;getSource()Lnet/minecraft/util/DamageSource;", shift = At.Shift.AFTER),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true,
            remap = false
    )
    private static void rlmixins_firstAidEventHandler_onLivingHurt(LivingHurtEvent event, CallbackInfo ci, EntityLivingBase entity, float amountToDamage, EntityPlayer player, AbstractPlayerDamageModel damageModel) {
        if(amountToDamage == Float.MAX_VALUE || Float.isNaN(amountToDamage) || amountToDamage == Float.POSITIVE_INFINITY) {
            damageModel.forEach(damageablePart -> damageablePart.currentHealth = 0.0F);
            if(player instanceof EntityPlayerMP) {
                FirstAid.NETWORKING.sendTo(new MessageSyncDamageModel(damageModel, false), (EntityPlayerMP)player);
            }

            event.setCanceled(true);
            CommonUtils.killPlayer(damageModel, player, event.getSource());
            ci.cancel();
        }
    }
}