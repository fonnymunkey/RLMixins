package rlmixins.mixin.quark;

import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.quark.management.feature.LinkItems;

@Mixin(LinkItems.class)
public abstract class LinkItemsServerMixin {

    @Inject(
            method = "linkItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getTextComponent()Lnet/minecraft/util/text/ITextComponent;"),
            cancellable = true
    )
    private static void rlmixins_quarkLinkItems_linkItem(EntityPlayer player, ItemStack item, CallbackInfo ci) {
        if(item.getTagCompound() != null) {
            PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
            int length = buf.writeCompoundTag(item.getTagCompound()).writerIndex();
            if(length > 8000) ci.cancel();//Arbitrary, no real use for accurate max nothing should be this big
        }
    }
}