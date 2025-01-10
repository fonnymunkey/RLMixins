package rlmixins.mixin.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
//import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
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

	@Inject(
			method = "addPotionEffect",
			at = @At("HEAD"),
			cancellable = true
	)
	void rlmixins_entityLivingBase_addPotionEffect(PotionEffect potioneffectIn, CallbackInfo ci) {
		if (!this.world.isRemote) return;
		if (this.rLMixins$isPacket) {
			rLMixins$setIsPacket(false);
			return;
		}
		if (!((EntityLivingBase) (Object) this instanceof EntityPlayer)) return;

		/*EntityPlayer player = (EntityPlayer)(Object) this;
		player.sendMessage(new TextComponentString("Stacktrace sent to logs"));

        RLMixins.LOGGER.info("ClientPotion {} amp{} dura{}, Entity {}",
				potioneffectIn.getEffectName(),
				potioneffectIn.getAmplifier(),
				potioneffectIn.getDuration(),
				this.getName()
		);
		try{
			throw new Exception("client potion");
		} catch(Exception e){
			e.printStackTrace(System.out);
		}*/

		ci.cancel();
	}
}