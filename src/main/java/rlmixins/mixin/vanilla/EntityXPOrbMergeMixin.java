package rlmixins.mixin.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.List;

@Mixin(EntityXPOrb.class)
public abstract class EntityXPOrbMergeMixin extends Entity {

	@Shadow public int xpValue;

	public EntityXPOrbMergeMixin(World worldIn) {
		super(worldIn);
	}

	@Inject(
			method = "onUpdate",
			at = @At(value = "TAIL")
	)
	private void rlmixins_entityXPOrb_onUpdate(CallbackInfo ci) {
		if (this.world.isRemote) return;
		if (this.world.getTotalWorldTime() % 10L != 0L) return;
		if (this.xpValue >= ForgeConfigHandler.server.orbMaxXpValue) return;
		if (this.ticksExisted < ForgeConfigHandler.server.orbMergeEarliestTick) return;
		if (this.isDead) return;

		List<EntityXPOrb> orbs = this.world.getEntitiesWithinAABB(
				EntityXPOrb.class,
				new AxisAlignedBB(this.getPosition().add(-1, -1, -1), this.getPosition().add(1, 1, 1)),
				EntitySelectors.IS_ALIVE
		);

		int newSize = this.xpValue;
		for (EntityXPOrb orb : orbs) {
			if (!orb.getUniqueID().equals(this.getUniqueID()) && orb.xpValue <= this.xpValue) {
				newSize += orb.xpValue;
				orb.setDead();
				if (newSize >= ForgeConfigHandler.server.orbMaxXpValue) break;
			}
		}
		orbs.clear();

		if (newSize > this.xpValue) {
			this.setDead();
			EntityXPOrb newOrb = new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, newSize);
			newOrb.motionX = this.motionX;
			newOrb.motionY = this.motionY;
			newOrb.motionZ = this.motionZ;
			newOrb.rotationYaw = this.rotationYaw;
			this.world.spawnEntity(newOrb);
		}
	}
}