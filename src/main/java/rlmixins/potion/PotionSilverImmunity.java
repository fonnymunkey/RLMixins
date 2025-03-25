package rlmixins.potion;

import net.minecraft.entity.EntityLivingBase;

public class PotionSilverImmunity extends PotionBase {

    public static final PotionSilverImmunity INSTANCE = new PotionSilverImmunity();

    public PotionSilverImmunity() {
        super("immunity", false, 0xFF55FF);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) { } // rlmixins.handlers.rlmixins.SilverImmunityEffectsHandler
}