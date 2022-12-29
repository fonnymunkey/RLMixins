package rlmixins.potion;

import com.tmtravlr.potioncore.PotionCoreAttributes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;

public class PotionEncumbered extends PotionBase {
    public static final PotionEncumbered INSTANCE = new PotionEncumbered();
    public final String ENCUMBERED_MOVEMENT_SPEED = "97042b81-d029-4ea5-b8c6-82442e2862bf";
    public final String ENCUMBERED_DIG_SPEED = "8f12b2ba-2079-4402-9091-a5bb3d9718c6";
    public final String ENCUMBERED_ATTACK_SPEED = "221441b2-5002-4e0a-a2d5-b43e021298df";

    public PotionEncumbered() {
        super("encumbered", true, 0x785821);
        this.registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, ENCUMBERED_MOVEMENT_SPEED, -0.05D, 2);
        this.registerPotionAttributeModifier(PotionCoreAttributes.DIG_SPEED, ENCUMBERED_DIG_SPEED, -0.10D, 2);
        this.registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED, ENCUMBERED_ATTACK_SPEED, -0.15D, 2);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        if(entityLivingBaseIn instanceof EntityPlayer) ((EntityPlayer)entityLivingBaseIn).addExhaustion(0.005F * (float)(amplifier + 1));
    }
}