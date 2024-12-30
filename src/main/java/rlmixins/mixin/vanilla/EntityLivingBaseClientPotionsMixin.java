package rlmixins.mixin.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import rlmixins.RLMixins;
import rlmixins.wrapper.IClientPotionApplier;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseClientPotionsMixin extends Entity implements IClientPotionApplier {
	public EntityLivingBaseClientPotionsMixin(World worldIn) {
		super(worldIn);
	}

	@Unique
	private boolean rLMixins$isPacket = false;

	@Override
	public void rLMixins$setIsPacket(boolean isPacket) {
		this.rLMixins$isPacket = isPacket;
	}

	@Shadow
	public abstract boolean isServerWorld();

	//@Shadow public abstract boolean isPotionApplicable(PotionEffect potioneffectIn);

	@Inject(
			method = "addPotionEffect",
			at = @At("HEAD"),
			cancellable = true
	)
	void rlmixins_entityLivingBase_addPotionEffect(PotionEffect potioneffectIn, CallbackInfo ci) {
		if (this.isServerWorld()) return;
		if (!((EntityLivingBase) (Object) this instanceof EntityPlayer)) return;
		if (this.rLMixins$isPacket) {
			rLMixins$setIsPacket(false);
			return;
		}

//        RLMixins.LOGGER.info("ClientPotion {} amp{} dura{}, Entity {} applicable {}",
//				potioneffectIn.getEffectName(),
//				potioneffectIn.getAmplifier(),
//				potioneffectIn.getDuration(),
//				this.getName(),
//				this.isPotionApplicable(potioneffectIn)
//		);
		ci.cancel();
	}
}