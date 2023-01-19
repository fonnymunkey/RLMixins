package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

import static com.github.alexthe666.iceandfire.entity.EntityDragonBase.ANIMATION_SHAKEPREY;

@Mixin(EntityDragonBase.class)
public abstract class EntityDragonBaseBiteMixin extends EntityLivingBase {

    public EntityDragonBaseBiteMixin(World worldIn) {
        super(worldIn);
    }

    @Shadow(remap = false) public abstract Animation getAnimation();
    @Shadow(remap = false) public abstract int getAnimationTick();
    @Shadow(remap = false) public abstract void setAnimation(Animation animation);
    @Shadow(remap = false) public abstract float getRenderSize();

    /**
     * @author fonnymunkey
     * @reason modify biting to use scaled damage, and heal the dragon
     */
    @Overwrite(remap = false)
    private void updatePreyInMouth(Entity prey) {
        this.setAnimation(ANIMATION_SHAKEPREY);
        if(this.getAnimation() == ANIMATION_SHAKEPREY && this.getAnimationTick() > 55 && prey != null && !this.world.isRemote) {
            if(this.getAnimationTick() == 56 && prey instanceof EntityLivingBase) {
                EntityLivingBase target = (EntityLivingBase)prey;
                boolean success = target.attackEntityFrom(
                        DamageSource.causeMobDamage(this),
                        (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()
                );
                if(success) {
                    this.heal((float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                }
            }
            else if(this.getAnimationTick() > 60) {
                prey.dismountRidingEntity();
            }
        }

        this.renderYawOffset = this.rotationYaw;
        float modTick_0 = (float)(this.getAnimationTick() - 25);
        float modTick_1 = this.getAnimationTick() > 25 && this.getAnimationTick() < 55
                ? 8.0F * MathHelper.clamp(MathHelper.sin((float)(Math.PI + (double)modTick_0 * 0.25)), -0.8F, 0.8F)
                : 0.0F;
        float modTick_2 = this.getAnimationTick() > 30 ? 10.0F : (float)Math.max(0, this.getAnimationTick() - 20);
        float radius = 0.75F * (0.6F * this.getRenderSize() / 3.0F) * -3.0F;
        float angle = (float) (Math.PI / 180.0) * this.renderYawOffset + 3.15F + modTick_1 * 2.0F * 0.015F;
        double extraX = (double)(radius * MathHelper.sin((float)(Math.PI + (double)angle)));
        double extraZ = (double)(radius * MathHelper.cos(angle));
        double extraY = modTick_2 == 0.0F
                ? 0.0
                : 0.035F * ((double)(this.getRenderSize() / 3.0F) + (double)modTick_2 * 0.5 * (double)(this.getRenderSize() / 3.0F));
        prey.setPosition(this.posX + extraX, this.posY + extraY, this.posZ + extraZ);
    }

    @Redirect(
            method = "onUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z")
    )
    public boolean rlmixins_iceAndFireEntityDragonBase_onUpdate(EntityLivingBase instance, DamageSource entitywolf, float b0) {
        boolean success = instance.attackEntityFrom(
                DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue())
        );
        if(success) this.heal((float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
        return success;
    }
}