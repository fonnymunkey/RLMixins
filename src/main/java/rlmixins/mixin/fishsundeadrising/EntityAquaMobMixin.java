package rlmixins.mixin.fishsundeadrising;

import com.Fishmod.mod_LavaCow.entities.aquatic.EntityAquaMob;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityAquaMob.class)
public abstract class EntityAquaMobMixin extends EntityMob {
	
	public EntityAquaMobMixin(World worldIn) {
		super(worldIn);
	}
	
	@Override
	public boolean isNotColliding() {
		return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this)
				&& this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty();
	}
}