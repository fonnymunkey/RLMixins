package rlmixins.mixin.bettersurvival;

import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.INunchakuCombo;
import com.mujmajnkraft.bettersurvival.capabilities.nunchakucombo.NunchakuComboProvider;
import com.mujmajnkraft.bettersurvival.config.ForgeConfigHandler;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentAgility;
import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentVitality;
import com.mujmajnkraft.bettersurvival.eventhandlers.CommonEventHandler;
import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import com.mujmajnkraft.bettersurvival.init.ModPotions;
import com.mujmajnkraft.bettersurvival.items.ItemCustomShield;
import com.mujmajnkraft.bettersurvival.items.ItemNunchaku;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import java.util.Arrays;
import java.util.UUID;

@Mixin(CommonEventHandler.class)
public abstract class CommonEventHandler_OptimizationMixin {
	
	@Unique
	private static final UUID rlmixins$followUUID = UUID.fromString("a6107045-134f-4c14-a645-75c3ae5c7a27");
	
	@Unique
	private static final AttributeModifier rlmixins$SHIELD_KNOCKBACK = new AttributeModifier(ItemCustomShield.knockbackModifierUUID, "shield_knockback_adjustment", 0.0, 0);
	
	@Unique
	private static final AttributeModifier rlmixins$SHIELD_WEIGHT = new AttributeModifier(ItemCustomShield.weightModifierUUID, "shield_speed_adjustment", 0.0, 0);
	
	@Unique
	private static final AttributeModifier rlmixins$AGILITY = new AttributeModifier(EnchantmentAgility.speedModifier, "agility", 0.0, 0);
	
	@Unique
	private static final AttributeModifier rlmixins$BLIND = new AttributeModifier(rlmixins$followUUID, "blind", 0.0, 1);
	
	/**
	 * @author fonnymunkey
	 * @reason rewrite to optimize performance
	 */
	@Overwrite(remap = false)
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if(entity instanceof EntityPlayer) {
			if(entity.getHeldItemMainhand().getItem() instanceof ItemNunchaku) {
				INunchakuCombo combo = entity.getCapability(NunchakuComboProvider.NUNCHAKUCOMBO_CAP, null);
				if(combo != null && (combo.getComboTime() > 0 || combo.getComboPower() > 0.0F)) combo.countDown();
			}
			
			int vitLvl = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.vitality, entity);
			if(vitLvl > 0) EnchantmentVitality.healPlayer((EntityPlayer)entity, vitLvl);
			
			IAttributeInstance movSpeed = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
			if(entity.getActiveItemStack().getItem() instanceof ItemCustomShield) ((ItemCustomShield)entity.getActiveItemStack().getItem()).applyModifiers(entity);
			else if(movSpeed.hasModifier(rlmixins$SHIELD_KNOCKBACK)
					|| movSpeed.hasModifier(rlmixins$SHIELD_WEIGHT)) {
				movSpeed.removeModifier(ItemCustomShield.knockbackModifierUUID);
				movSpeed.removeModifier(ItemCustomShield.weightModifierUUID);
			}
			
			int speedLvl = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.agility, entity);
			if(speedLvl > 0) EnchantmentAgility.applySpeedModifier(entity, speedLvl);
			else if(movSpeed.hasModifier(rlmixins$AGILITY)) {
				movSpeed.removeModifier(EnchantmentAgility.speedModifier);
			}
		}
		
		if(entity.getActivePotionEffect(ModPotions.stun) != null) {
			entity.motionX = 0.0;
			if(entity.motionY > 0.0) entity.motionY = 0.0;
			entity.motionZ = 0.0;
			if(entity instanceof EntityCreeper) ((EntityCreeper)entity).setCreeperState(-1);
		}
		
		if(entity instanceof EntityLiving) {
			IAttributeInstance follow = entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
			if(follow.hasModifier(rlmixins$BLIND)) {
				follow.removeModifier(rlmixins$followUUID);
			}
			
			if(entity.getActivePotionEffect(MobEffects.BLINDNESS) != null) {
				EntityEntry entry = EntityRegistry.getEntry(entity.getClass());
				if(entry != null && !Arrays.asList(ForgeConfigHandler.potions.blindnessBlacklist).contains(entry.getRegistryName().toString())) {
					double strength = -0.01 * ForgeConfigHandler.potions.blindnessStrength;
					if(strength < 0.0) {
						AttributeModifier modifier = new AttributeModifier(rlmixins$followUUID, "blind", strength, 1);
						follow.applyModifier(modifier);
					}
				}
			}
		}
	}
}