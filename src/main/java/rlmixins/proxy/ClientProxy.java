package rlmixins.proxy;

import net.minecraft.client.Minecraft;
import rlmixins.entity.flare.EntityFlareNonAlbedo;
import rlmixins.entity.flare.MovingSoundFlare;

public class ClientProxy extends CommonProxy {
    @Override
    public void playSoundFlare(EntityFlareNonAlbedo flare) {
        MovingSoundFlare soundFlare = new MovingSoundFlare(flare);
        Minecraft.getMinecraft().getSoundHandler().playSound(soundFlare);
        //Minecraft.getMinecraft().getSoundHandler().playDelayedSound(soundFlare, 5);//Seems to cause a concurrentmodification exception because of chunk load/unload
    }
}
