package rlmixins.mixin.itemphysics;

import com.creativemd.itemphysic.EventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tschipp.carryon.common.handler.RegistrationHandler;

@Mixin(EventHandler.class)
public abstract class EventHandlerCarryonMixin {

    @Shadow(remap = false)
    public static int power;

    @Inject(
            method = "gameTick",
            at = @At(value = "INVOKE", target = "Lcom/creativemd/creativecore/common/packet/PacketHandler;sendPacketToServer(Lcom/creativemd/creativecore/common/packet/CreativeCorePacket;)V", shift = At.Shift.BEFORE),
            cancellable = true,
            remap = false
    )
    public void rlmixins_itemPhysicsEventHandler_gameTick(TickEvent.ClientTickEvent event, CallbackInfo ci) {
        ItemStack stack = Minecraft.getMinecraft().player.getHeldItemMainhand();
        if(stack.getItem() == RegistrationHandler.itemEntity || stack.getItem() == RegistrationHandler.itemTile) {
            power = 0;
            ci.cancel();
        }
    }
}