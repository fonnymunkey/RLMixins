package rlmixins.mixin.vanilla;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.wrapper.BetterSurvivalWrapper;

import javax.annotation.Nullable;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseBSPotionHitsMixin extends Entity {

    @Shadow protected float lastDamage;

    @Shadow public abstract boolean isPotionActive(Potion potionIn);
    @Shadow public abstract void addPotionEffect(PotionEffect potioneffectIn);
    @Shadow @Nullable public abstract PotionEffect getActivePotionEffect(Potion potionIn);

    public EntityLivingBaseBSPotionHitsMixin(World worldIn) {
        super(worldIn);
    }

    @Inject(
            method = "attackEntityFrom",
            at = @At(value = "RETURN", ordinal = 6),
            remap = true
    )
    public void rlmixins_vanillaEntityLivingBase_attackEntityFrom(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 2) boolean flag2){
        if(flag2 && !this.world.isRemote && source.getImmediateSource() instanceof EntityLivingBase) {
            EntityLivingBase attacker = (EntityLivingBase)source.getImmediateSource();
            ItemStack stack = attacker.getHeldItemMainhand();
            if (stack.hasTagCompound()) {
                NBTTagCompound compound = stack.getTagCompound();
                int potionHits = 0 ;
                boolean reduceCount = false;

                if(compound.hasKey("remainingPotionHits")) potionHits = compound.getInteger("remainingPotionHits");

                if (potionHits > 0) {
                    for (PotionEffect effect : PotionUtils.getEffectsFromStack(stack)) {
                        if (effect.getPotion().isInstant()) {
                            int currentHurtResistantTime = this.hurtResistantTime;
                            float currentLastDamage = this.lastDamage;

                            this.hurtResistantTime = 0;
                            effect.getPotion().affectEntity(null, attacker, (EntityLivingBase)(Object)this, effect.getAmplifier(), 1 / 6D);

                            this.hurtResistantTime = currentHurtResistantTime;
                            this.lastDamage = currentLastDamage;
                            reduceCount = true;
                        }
                        else if (!this.isPotionActive(effect.getPotion()) || this.getActivePotionEffect(effect.getPotion()).getDuration() <= 10) {
                            this.addPotionEffect(new PotionEffect(effect.getPotion(), Math.max(effect.getDuration() / BetterSurvivalWrapper.getPotionDivisor(), 1), effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles()));
                            reduceCount = true;
                        }
                    }
                    if (reduceCount && attacker instanceof EntityPlayer && !((EntityPlayer)attacker).capabilities.isCreativeMode) {
                        compound.setInteger("remainingPotionHits", potionHits - 1);
                        if (potionHits - 1 <= 0) {
                            compound.removeTag("Potion");
                            compound.removeTag("CustomPotionEffects");
                        }
                    }
                }
            }
        }
    }
}
