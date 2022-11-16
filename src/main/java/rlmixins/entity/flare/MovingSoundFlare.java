package rlmixins.entity.flare;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.SoundCategory;
import rlmixins.handlers.ModRegistry;

public class MovingSoundFlare extends MovingSound {

    private final EntityFlareNonAlbedo flare;

    public MovingSoundFlare(EntityFlareNonAlbedo flare) {
        super(ModRegistry.FLARE_BURN, SoundCategory.NEUTRAL);
        this.flare = flare;
        this.repeat = true;
        this.repeatDelay = 0;
    }

    @Override
    public void update() {
        if(!this.flare.isDead) {
            this.xPosF = (float)this.flare.posX;
            this.yPosF = (float)this.flare.posY;
            this.zPosF = (float)this.flare.posZ;
            this.volume = 0.3F;
            this.pitch = 2.0F;// + (this.flare.world.rand.nextFloat()-0.5F)*0.5F;
        }
        else this.donePlaying = true;
    }
}