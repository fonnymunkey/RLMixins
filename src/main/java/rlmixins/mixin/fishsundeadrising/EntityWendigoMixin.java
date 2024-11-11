package rlmixins.mixin.fishsundeadrising;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.EntityWendigo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityWendigo.class)
public abstract class EntityWendigoMixin extends EntityMob {
	
	@Shadow(remap = false)
	private int attackTimer;
	
	@Shadow(remap = false)
	private int jumpTimer;
	
	@Shadow(remap = false)
	public abstract int getAttackTimer();
	
	@Shadow(remap = false)
	public byte AttackStance;
	
	public EntityWendigoMixin(World worldIn) {
		super(worldIn);
	}
	
	/**
	 * @author fonnymunkey
	 * @reason fix clientside effect checks
	 */
	@Overwrite
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(this.attackTimer > 0) {
			--this.attackTimer;
		}
		
		if(this.jumpTimer > 0) {
			--this.jumpTimer;
		}
		
		if(this.world.isRemote) return;
		
		if(!Modconfig.SunScreen_Mode && this.world.isDaytime()) {
			float f = this.getBrightness();
			if(f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ))) {
				this.setFire(40);
			}
		}
		
		if(this.getAttackTarget() != null && this.getDistanceSq(this.getAttackTarget()) < 4.0 && this.getAttackTimer() == 10 && this.deathTime <= 0) {
			boolean flag = false;
			if(this.AttackStance == 40) {
				flag = this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 1.5F);
				if(this.getAttackTarget() instanceof EntityPlayer) {
					((EntityPlayer)this.getAttackTarget()).disableShield(true);
				}
			}
			else {
				flag = this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
			}
			
			if(flag) {
				float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
				if(this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F) {
					this.getAttackTarget().setFire(2 * (int)f);
				}
				
				if(this.getAttackTarget() instanceof EntityLivingBase) {
					float local_difficulty = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
					this.getAttackTarget().addPotionEffect(new PotionEffect(MobEffects.HUNGER, 140 * (int)local_difficulty, 4));
				}
			}
		}
		
	}
}