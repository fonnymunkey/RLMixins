package rlmixins.potion;

import com.tmtravlr.potioncore.PotionCore;
import com.tmtravlr.potioncore.network.SToCMessage;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;

public class PotionDelayedLaunch extends PotionBase {
    public static final PotionDelayedLaunch INSTANCE = new PotionDelayedLaunch();

    public PotionDelayedLaunch() {
        super("delayed_launch", false, 0xD5D7F0);
    }

    @Override
    public boolean isReady(int duration, int amplifier) { return duration == 1; }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        entityLivingBaseIn.motionY = (double)(amplifier + 1);
        if(entityLivingBaseIn instanceof EntityPlayerMP) {
            PacketBuffer out = new PacketBuffer(Unpooled.buffer());
            out.writeInt(2);
            out.writeInt(amplifier + 1);
            SToCMessage packet = new SToCMessage(out);
            PotionCore.networkWrapper.sendTo(packet, (EntityPlayerMP)entityLivingBaseIn);
        }
    }
}