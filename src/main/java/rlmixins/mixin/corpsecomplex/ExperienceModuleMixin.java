package rlmixins.mixin.corpsecomplex;

import c4.corpsecomplex.common.modules.ExperienceModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceModule.class)
public abstract class ExperienceModuleMixin {

    @Shadow(remap = false) private static boolean keepXP;

    @Shadow(remap = false) private static double xpLoss;

    @Shadow(remap = false) private static double xpRecover;

    @Shadow(remap = false) private static int maxXPRecover;

    @Shadow(remap = false) private static void resetXP(EntityPlayer player) { }

    @Shadow(remap = false) private static void addExperience(EntityPlayer player, int amount) { }

    @Inject(
            method = "onPlayerRespawnBegin",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_corpseComplexExperienceModule_onPlayerRespawnBegin(PlayerEvent.Clone e, CallbackInfo ci) {
        if(!keepXP) ci.cancel();
    }

    /**
     * @author fonnymunkey
     * @reason fix corpsecomplex using experienceTotal which does not properly update
     */
    @Overwrite(remap = false)
    private static void setExperiencesValues(LivingExperienceDropEvent e) {
        EntityPlayer player = (EntityPlayer)e.getEntityLiving();
        if(keepXP) e.setCanceled(true);
        else {
            int experienceReal = (int)(getExperienceForLevel(player.experienceLevel) + (player.experience * player.xpBarCap()));
            int dropXP = (int)Math.round((double)experienceReal * xpLoss * xpRecover);
            int keptXP = (int)Math.round((double)experienceReal * (1.0 - xpLoss));
            if(maxXPRecover > 0) dropXP = Math.min(maxXPRecover, dropXP);

            e.setDroppedExperience(dropXP);
            resetXP(player);
            addExperience(player, keptXP);
        }
    }

    /**
     * Based on method from OpenModsLib MIT
     * https://github.com/OpenMods/OpenModsLib/blob/master/src/main/java/openmods/utils/EnchantmentUtils.java
     */
    private static int getExperienceForLevel(int level) {
        if(level == 0) return 0;
        if(level <= 15) return sum(level, 7, 2);
        if(level <= 30) return 315 + sum(level - 15, 37, 5);
        return 1395 + sum(level - 30, 112, 9);
    }

    /**
     * Based on method from OpenModsLib MIT
     * https://github.com/OpenMods/OpenModsLib/blob/master/src/main/java/openmods/utils/EnchantmentUtils.java
     */
    private static int sum(int n, int a0, int d) {
        return n * (2 * a0 + (n - 1) * d) / 2;
    }
}