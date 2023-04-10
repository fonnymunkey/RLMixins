package rlmixins.mixin.quark;

import io.netty.buffer.Unpooled;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.client.event.GuiScreenEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import vazkii.quark.management.feature.LinkItems;

@Mixin(LinkItems.class)
public abstract class LinkItemsClientMixin {

    @Inject(
            method = "keyboardEvent",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Slot;getStack()Lnet/minecraft/item/ItemStack;"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    public void rlmixins_quarkLinkItems_keyboardEvent(GuiScreenEvent.KeyboardInputEvent.Post event, CallbackInfo ci, GuiContainer gui, Slot slot) {
        if(slot.getStack().getTagCompound() != null) {
            PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
            int length = buf.writeCompoundTag(slot.getStack().getTagCompound()).writerIndex();
            if(length > 8000) ci.cancel();//Arbitrary, no real use for accurate max nothing should be this big
        }
    }
}