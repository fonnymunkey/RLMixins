package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityIceDragon.class)
public abstract class EntityIceDragonMixin extends EntityDragonBase {

    public EntityIceDragonMixin(World world, double minimumDamage, double maximumDamage, double minimumHealth, double maximumHealth, double minimumSpeed, double maximumSpeed) {
        super(world, minimumDamage, maximumDamage, minimumHealth, maximumHealth, minimumSpeed, maximumSpeed);
    }

    @Redirect(
            method = "attackEntityAsMob",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z", ordinal = 0)
    )
    public boolean rlmixins_iceAndFireEntityIceDragon_attackEntityAsMob0(Entity instance, DamageSource source, float amount) {
        boolean success = instance.attackEntityFrom(
                DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue())
        );
        if(success) this.heal((float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        return success;
    }

    @Redirect(
            method = "attackEntityAsMob",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z", ordinal = 1)
    )
    public boolean rlmixins_iceAndFireEntityIceDragon_attackEntityAsMob1(Entity instance, DamageSource source, float amount) {
        boolean success = instance.attackEntityFrom(
                DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue())
        );
        if(success) this.heal((float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        return success;
    }

    @Redirect(
            method = "attackEntityAsMob",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z", ordinal = 4)
    )
    public boolean rlmixins_iceAndFireEntityIceDragon_attackEntityAsMob4(Entity instance, DamageSource source, float amount) {
        boolean success = instance.attackEntityFrom(
                DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue())
        );
        if(success) this.heal((float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        return success;
    }
}
