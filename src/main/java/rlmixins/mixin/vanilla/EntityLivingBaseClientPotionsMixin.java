package rlmixins.mixin.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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

	@Inject(
			method = "addPotionEffect",
			at = @At("HEAD"),
			cancellable = true
	)
	private void rlmixins_entityLivingBase_addPotionEffect(PotionEffect potioneffectIn, CallbackInfo ci) {
		if(!this.world.isRemote) return;
		if(this.rLMixins$isPacket) {
			rLMixins$setIsPacket(false);
			return;
		}
		ci.cancel();
	}
}