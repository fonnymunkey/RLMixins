package rlmixins.mixin.shieldbreak;

import cursedflames.bountifulbaubles.item.ItemShieldCobalt;
import shieldbreak.handlers.EventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EventHandler.class)
public abstract class EventHandlerMixin {

    /**
     * Inject check and handling for Bountiful Bauble's Bauble Shields
     * Preserves item on 0 durability when used as a shield
     *
     * Jank regardless, the block that sets to 0 dura, does a Shield Parry for some reason
     *
     * Based on
     * https://github.com/CursedFlames/BountifulBaubles/blob/forge-1.12.x/src/main/java/cursedflames/bountifulbaubles/item/ItemShieldCobalt.java#L112
     * Prioritising being as close to ShieldBreak as possible
     */
    @Inject(
            method = "damageShield",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void mixin(EntityPlayer playerIn, ItemStack shieldIn, float damage, CallbackInfoReturnable<Boolean> cir) {
        if(shieldIn.getItem() instanceof ItemShieldCobalt) {
            int i = Math.min(shieldIn.getMaxDamage() - shieldIn.getItemDamage(), (int)damage);
            shieldIn.damageItem(i, playerIn);

            if (shieldIn.getItemDamage() >= shieldIn.getMaxDamage()) {
                if(playerIn.getActiveItemStack().isEmpty()) {
                    EnumHand hand = playerIn.getActiveHand();
                    ForgeEventFactory.onPlayerDestroyItem(playerIn, shieldIn, hand);
                    playerIn.setItemStackToSlot(hand.equals(EnumHand.MAIN_HAND) ? EntityEquipmentSlot.MAINHAND : EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
                }
                playerIn.resetActiveHand();
                playerIn.world.playSound((EntityPlayer)null, playerIn.getPosition(), SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS, 0.9F, 0.8F + playerIn.world.rand.nextFloat() * 0.4F);
                cir.setReturnValue(true);
            }
            cir.setReturnValue(false);
        }
    }
}