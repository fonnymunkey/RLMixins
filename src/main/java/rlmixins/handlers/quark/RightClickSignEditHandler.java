package rlmixins.handlers.quark;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Level;
import rlmixins.RLMixins;
import vazkii.quark.base.module.ConfigHelper;

public class RightClickSignEditHandler {
    public static boolean isEnabled;

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        final EntityPlayer player = event.player;
        final World world = player.world;

        if(world.isRemote || !(player instanceof EntityPlayerMP)) return;

        RLMixins.LOGGER.log(Level.INFO, "Syncing Quark configuration with player: " + player.getName());
        final boolean prop = ConfigHelper.loadPropBool("Right click sign edit", "tweaks", "", true);
        isEnabled = prop;
        PacketHandler.instance.sendTo(new MessageSyncConfig(prop), (EntityPlayerMP)player);
    }

    public static class MessageSyncConfig implements IMessage {
        public MessageSyncConfig() {
        }

        private boolean rightClickSignEditEnabled;

        public MessageSyncConfig(boolean rightClickSignEditEnabled) {
            this.rightClickSignEditEnabled = rightClickSignEditEnabled;
        }

        public void fromBytes(ByteBuf buf) {
            rightClickSignEditEnabled = buf.readBoolean();
        }

        public void toBytes(ByteBuf buf) {
            buf.writeBoolean(rightClickSignEditEnabled);
        }

        public static class Handler implements IMessageHandler<MessageSyncConfig, IMessage> {
            @Override
            public IMessage onMessage(MessageSyncConfig message, MessageContext ctx) {
                if(ctx.side == Side.CLIENT) {
                    Minecraft.getMinecraft().addScheduledTask(() -> {
                        RightClickSignEditHandler.isEnabled = message.rightClickSignEditEnabled;
                    });
                }
                return null;
            }
        }
    }
}
