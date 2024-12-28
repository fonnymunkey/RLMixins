package rlmixins.mixin.clumps;

import com.blamejared.clumps.entities.EntityXPOrbBig;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.RLMixins;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.List;

@Mixin(EntityXPOrbBig.class)
public abstract class EntityXPOrbBigMixin extends EntityXPOrb {

	public EntityXPOrbBigMixin(World worldIn, double x, double y, double z, int expValue) {
		super(worldIn, x, y, z, expValue);
	}

	/**
	 * @author Nischhelm
	 * @reason needs a lot of changes
	 */
	@Overwrite
	public void onUpdate() {
		super.onUpdate();
		if (!this.world.isRemote && this.xpValue == 0) {
			this.setDead();
			return;
		}

		if (this.world.getTotalWorldTime() % 5L == 0L && this.xpValue < ForgeConfigHandler.server.orbMaxXpValue) {
			List<EntityXPOrbBig> orbs = this.world.getEntitiesWithinAABB(EntityXPOrbBig.class, new AxisAlignedBB(this.posX - 2.0, this.posY - 2.0, this.posZ - 2.0, this.posX + 2.0, this.posY + 2.0, this.posZ + 2.0), EntitySelectors.IS_ALIVE);
			int newSize = 0;
			if (!orbs.isEmpty()) {
				EntityXPOrbBig orb = orbs.get(this.world.rand.nextInt(orbs.size()));
				if (!orb.getUniqueID().equals(this.getUniqueID()) && orb.xpValue <= this.xpValue && orb.xpValue > 0) {
					newSize = orb.getXpValue() + this.xpValue;
					orb.xpValue = 0;
				}

				if (newSize > this.xpValue) {
					if(!this.world.isRemote) {
						EntityXPOrbBig newOrb = new EntityXPOrbBig(this.world, this.posX, this.posY, this.posZ, newSize);
						this.world.spawnEntity(newOrb);
					}
					this.xpValue = 0;
				}

				orbs.clear();
			}
		}
	}

	/**
	 * @author Nischhelm
	 * @reason injecting doesnt get picked up for some reason
	 */
	@Overwrite
	public void onCollideWithPlayer(EntityPlayer entityIn) {
		super.onCollideWithPlayer(entityIn);
	}
}