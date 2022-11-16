package rlmixins.mixin.bettercombat;

import bettercombat.mod.handler.EventHandlers;
import bettercombat.mod.util.Helpers;
import com.Shultrea.Rin.Enchantments_Sector.Smc_030;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import rlmixins.handlers.ModRegistry;

import java.util.Iterator;

import static bettercombat.mod.util.Helpers.execNullable;

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
    @ModifyVariable(
            method = "attackTargetEntityItem",
            ordinal = 0,
            index = 3,
            at = @At(value = "STORE", ordinal = 3),
            remap = false
    )
    private static float rlmixins_betterCombatHelpers_attackTargetEntityItem_storeDamage(float in) {
        storedDamage = in;
        return in;
    }

    /**
     * Replace the sweep iterator with one that respecting Sweeping enchant modifiers
     * Localcapture seems to die here with the Mod Dev plugin so just recreate the iterator instead
     */
    @Inject(
            method = "attackTargetEntityItem",
            at = @At(value = "INVOKE", target = "Ljava/util/Iterator;hasNext()Z"),
            remap = false
    )
    private static void rlmixins_betterCombatHelpers_attackTargetEntityItem_replaceIterator(EntityPlayer player, Entity targetEntity, boolean offhand, CallbackInfo ci) {
        for(EntityLivingBase living : player.world.getEntitiesWithinAABB(EntityLivingBase.class, targetEntity.getEntityBoundingBox().grow(1.0, 0.25, 1.0))) {
            if (living != player && living != targetEntity && !player.isOnSameTeam(living) && player.getDistanceSq(living) < 9.0) {
                living.knockBack(player, 0.4F, (double) MathHelper.sin(player.rotationYaw * 0.017453292F), (double) (-MathHelper.cos(player.rotationYaw * 0.017453292F)));
                if (offhand) {
                    execNullable(targetEntity.getCapability(EventHandlers.SECONDHURTTIMER_CAP, (EnumFacing) null), (sht) -> {
                        sht.attackEntityFromOffhand(living, DamageSource.causePlayerDamage(player), 1.0F + EnchantmentHelper.getSweepingDamageRatio(player) * storedDamage);
                    });
                } else {
                    living.attackEntityFrom(DamageSource.causePlayerDamage(player), 1.0F + EnchantmentHelper.getSweepingDamageRatio(player) * storedDamage);
                }
            }
        }
    }

    /**
     * Cancel existing sweeping iterator
     */
    @Redirect(
            method = "attackTargetEntityItem",
            at = @At(value = "INVOKE", target = "Ljava/util/Iterator;hasNext()Z"),
            remap = false
    )
    private static boolean rlmixins_betterCombatHelpers_attackTargetEntityItem_cancelIterator(Iterator instance) {
        return false;
    }

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

                player.world.playSound(null, player.posX, player.posY, player.posZ, ModRegistry.CRITICAL_STRIKE, SoundCategory.PLAYERS, 1.0F, 1.0F /(player.world.rand.nextFloat() * 0.4F + 1.2F)* 1.6F);

                modifier += crit;
            }
        }
        return modifier;
    }
}