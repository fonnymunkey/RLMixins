package rlmixins.mixin.lycanites;

import com.lycanitesmobs.client.localisation.LanguageManager;
import com.lycanitesmobs.core.item.special.ItemSoulkey;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemSoulkey.class)
public abstract class ItemSoulkeyMixin {

    /**
     * Fix Soulkey issues with offhand
     * Original fix from Meldexun
     * https://gitlab.com/Lycanite/LycanitesMobs/-/merge_requests/454
     */
    @Inject(
            method = "onItemUse",
            at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerCapabilities;isCreativeMode:Z", ordinal = 0),
            cancellable = true
    )
    public void rlmixins_lycanitesItemSoulKey_onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, CallbackInfoReturnable<EnumActionResult> cir) {
        ItemStack itemStack = player.getHeldItem(hand);
        if(!player.capabilities.isCreativeMode) {
            itemStack.shrink(1);
        }
        String message = LanguageManager.translate("message.soulkey.active");
        player.sendMessage(new TextComponentString(message));

        cir.setReturnValue(EnumActionResult.SUCCESS);
    }
}