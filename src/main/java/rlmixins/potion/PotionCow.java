package rlmixins.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.SoundCategory;
import rlmixins.handlers.ModRegistry;

public class PotionCow extends PotionBase {

    public static final PotionCow INSTANCE = new PotionCow();

    public PotionCow() { super("cow", false, 0xF3F4F9); }

    @Override
    public boolean isReady(int duration, int amplifier) { return true; }

    @Override
    public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
        if(entityLivingBase.world.isRemote) return;
        if(entityLivingBase.ticksExisted%40==0 && entityLivingBase.world.rand.nextFloat() < 0.30F) {
            entityLivingBase.world.playSound(null, entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ, ModRegistry.COW, SoundCategory.PLAYERS, 0.9F, (entityLivingBase.world.rand.nextFloat()-entityLivingBase.world.rand.nextFloat())*0.1F+1.0F);
        }
    }
}