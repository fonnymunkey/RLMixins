package rlmixins.mixin.lycanites;

import com.github.alexthe666.iceandfire.entity.StoneEntityProperties;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseCreatureEntity.class)
public class BaseCreatureEntityMixin {

    /**
     * Fix item consumption issues with offhand
     * Original fix from Meldexun
     * https://gitlab.com/Lycanite/LycanitesMobs/-/merge_requests/454
     */
    @Inject(
            method = "consumePlayersItem(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;I)V",
            at = @At(value = "HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_baseCreatureEntity_consumePlayersItem(EntityPlayer player, ItemStack itemStack, int amount, CallbackInfo ci) {
        if(!player.capabilities.isCreativeMode) {
            itemStack.shrink(amount);
        }

        ci.cancel();
    }

    /**
     * Fix item consumption issues with offhand
     * Original fix from Meldexun
     * https://gitlab.com/Lycanite/LycanitesMobs/-/merge_requests/454
     */
    @Inject(
            method = "replacePlayersItem(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;ILnet/minecraft/item/ItemStack;)V",
            at = @At(value = "HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_baseCreatureEntity_replacePlayersItem(EntityPlayer player, ItemStack itemStack, int amount, ItemStack newStack, CallbackInfo ci) {
        if(!player.capabilities.isCreativeMode) {
            itemStack.shrink(amount);
        }
        if(itemStack.isEmpty()) {
            player.setHeldItem(player.getHeldItemMainhand().isEmpty() ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND, newStack);
        }
        else if(!player.inventory.addItemStackToInventory(newStack)) {
            player.dropItem(newStack, false);
        }

        ci.cancel();
    }

    /**
     * Fix Lycanite mobs targetting and getting stuck targetting statues
     */
    @Inject(
            method = "canAttackEntity",
            at = @At(value = "HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_baseCreatureEntity_canAttackEntity(EntityLivingBase targetEntity, CallbackInfoReturnable<Boolean> cir) {
        StoneEntityProperties properties = (StoneEntityProperties) EntityPropertiesHandler.INSTANCE.getProperties(targetEntity, StoneEntityProperties.class);
        if(properties != null && properties.isStone) cir.setReturnValue(false);
    }
}