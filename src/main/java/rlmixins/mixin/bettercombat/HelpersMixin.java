package rlmixins.mixin.bettercombat;

import bettercombat.mod.combat.ISecondHurtTimer;
import bettercombat.mod.util.Helpers;
import com.Shultrea.Rin.Enchantments_Sector.Smc_030;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import rlmixins.RLMixins;
import rlmixins.handlers.ModRegistry;

import java.util.function.Consumer;

@Mixin(Helpers.class)
public abstract class HelpersMixin {
    private static EntityPlayer storedPlayer = null;
    private static Entity storedTarget = null;
    private static boolean storedStrong = false;
    private static boolean storedOffhand = false;
    private static float storedModifier = 0.0F;
    private static float storedDamage = 0.0F;

    /**
     * Hacky way to access player and target from inside modifyvariables
     */
    @Inject(
            method = "attackTargetEntityItem",
            at = @At(value = "FIELD", target = "Lbettercombat/mod/util/ConfigurationHandler;randomCrits:Z"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            remap = false
    )
    private static void rlmixins_betterCombatHelpers_attackTargetEntityItem_storeInfo(
            EntityPlayer player, Entity targetEntity, boolean offhand, CallbackInfo ci,
            float damage, float cMod, int cooldown, float cooledStr, boolean isStrong, boolean knockback) {
        storedPlayer = player;
        storedTarget = targetEntity;
        storedStrong = isStrong;
        storedOffhand = offhand;
    }

    /**
     * Post the critical hit event which exists in the updated version of the mod that was never released,
     * change isCrit and store modifier depending on result
     * Also re-add jumping crits for <2 blocks away
     */
    @ModifyVariable(
            method = "attackTargetEntityItem",
            ordinal = 3,
            index = 9,
            at = @At(value = "STORE"),
            remap = false
    )
    private static boolean rlmixins_betterCombatHelpers_attackTargetEntityItem_modifyIsCrit(boolean in) {
        in = in || storedStrong && storedPlayer.fallDistance > 0.0F && !storedPlayer.onGround && !storedPlayer.isOnLadder() &&
                        !storedPlayer.isInWater() && !storedPlayer.isPotionActive(MobEffects.BLINDNESS) && !storedPlayer.isRiding() &&
                        storedTarget instanceof EntityLivingBase && !storedPlayer.isSprinting() && storedPlayer.getDistance(storedTarget) < 2.0D;
        net.minecraftforge.event.entity.player.CriticalHitEvent hitResult = net.minecraftforge.common.ForgeHooks.getCriticalHit(storedPlayer, storedTarget, in, in ? 1.5F : 1.0F);
        if(hitResult!=null) {
            storedModifier = getCriticalHitModifier(storedPlayer, storedOffhand, hitResult.getDamageModifier());
        }
        return hitResult != null;
    }

    /**
     * Undo hardcoded 1.5 multiplier, multiply by result from event
     */
    @ModifyVariable(
            method = "attackTargetEntityItem",
            ordinal = 0,
            index = 3,
            at = @At(value = "STORE", ordinal = 2),
            remap = false
    )
    private static float rlmixins_betterCombatHelpers_attackTargetEntityItem_modifyDamage(float in) {
        in /= 1.5F;//Undo previous operation
        return in * storedModifier;
    }

    /**
     * Store damage after it is modified to access later
     */
    @Inject(
            method = "attackTargetEntityItem",
            at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;distanceWalkedModified:F"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            remap = false
    )
    private static void rlmixins_betterCombatHelpers_attackTargetEntityItem_storeDamage(
            EntityPlayer player, Entity targetEntity, boolean offhand, CallbackInfo ci,
            float damage, float cMod, int cooldown, float cooledStr, boolean isStrong, boolean knockback, boolean isCrit, boolean isSword, int knockbackMod, int fireAspect) {
        storedDamage = damage;
    }
/*
    @ModifyConstant(
            method = "attackTargetEntityItem",
            constant = @Constant(floatValue = 1.0F, ordinal = 5),
            remap = false
    )
    private static float rlmixins_betterCombatHelpers_attackTargetEntityItem_sweepingConstantOffhand(float in) {
        RLMixins.LOGGER.log(Level.INFO, "Modifying first float");
        return 1.0F + EnchantmentHelper.getSweepingDamageRatio(storedPlayer) * storedDamage;
    }

    @ModifyConstant(
            method = "attackTargetEntityItem",
            constant = @Constant(floatValue = 1.0F, ordinal = 6),
            remap = false
    )
    private static float rlmixins_betterCombatHelpers_attackTargetEntityItem_sweepingConstantMainhand(float in) {
        RLMixins.LOGGER.log(Level.INFO, "Modifying second float");
        return 1.0F + EnchantmentHelper.getSweepingDamageRatio(storedPlayer) * storedDamage;
    }
*/

    /**
     * Handle CriticalStrike enchantment here to get offhand
     */
    private static float getCriticalHitModifier(EntityPlayer player, boolean offhand, float modifier) {
        ItemStack stack = offhand ? player.getHeldItemOffhand() : player.getHeldItemMainhand();
        if(!stack.isEmpty()) {
            int level = EnchantmentHelper.getEnchantmentLevel(Smc_030.CriticalStrike, stack);

            if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
            int counter = stack.getTagCompound().getInteger("failedCritCount");
            int maxReduction = level * 50;

            if(player.getRNG().nextInt(1000 - maxReduction) >= 32 * (counter + 1)) stack.getTagCompound().setInteger("failedCritCount", counter + 1);
            else {
                stack.getTagCompound().setInteger("failedCritCount", 0);
                float crit = 0.4F + (float)level * 0.4F + player.world.rand.nextFloat() * 0.5F;

                player.world.playSound(null, player.posX, player.posY, player.posZ, ModRegistry.CRITICAL_STRIKE, SoundCategory.PLAYERS, 1.0F, 1.0F /(player.world.rand.nextFloat() * 0.4F + 1.2F)* 2.0F);

                modifier += crit;
            }
        }
        return modifier;
    }
}