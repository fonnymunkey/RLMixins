package rlmixins.mixin.fishsundeadrising;

import com.Fishmod.mod_LavaCow.entities.EntityZombieMushroom;
import com.Fishmod.mod_LavaCow.mod_LavaCow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Random;

@Mixin(EntityZombieMushroom.class)
public abstract class EntityZombieMushroomMixin extends EntityZombie {
	
	@Shadow(remap = false)
	private Vec3d[] spore_color;
	
	@Shadow(remap = false)
	public abstract int getSkin();
	
	public EntityZombieMushroomMixin(World worldIn) {
		super(worldIn);
	}
	
	/**
	 * @author fonnymunkey
	 * @reason fix clientside effect checks
	 */
	@Overwrite
	public void onUpdate() {
		super.onUpdate();
		if(!this.getEntityWorld().isRemote && this.ticksExisted % 20 == 0) {
			List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(2.0, 2.0, 2.0));
			if(!list.isEmpty()) {
				float local_difficulty = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
				for(Entity entity1 : list) {
					if(entity1 instanceof EntityLivingBase) {
						if(((EntityLivingBase)entity1).getActivePotionEffect(MobEffects.POISON) == null) {
							((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(MobEffects.POISON, 40 * (int)local_difficulty, 0));
						}
					}
				}
			}
		}
		
		if(this.getEntityWorld().isRemote && this.ticksExisted % 5 == 0) {
			mod_LavaCow.PROXY.spawnCustomParticle("spore", this.world, this.posX + (double)((new Random()).nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)((new Random()).nextFloat() * this.height), this.posZ + (double)((new Random()).nextFloat() * this.width * 2.0F) - (double)this.width, 0.0, 0.0, 0.0, (float)this.spore_color[this.getSkin()].x, (float)this.spore_color[this.getSkin()].y, (float)this.spore_color[this.getSkin()].z);
		}
	}
}