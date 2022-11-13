package rlmixins.mixin.rustic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import org.apache.logging.log4j.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.RLMixins;
import rustic.common.blocks.fluids.FluidBooze;
import rustic.common.blocks.fluids.ModFluids;

@Mixin(ModFluids.class)
public class ModFluidsMixin {
    /*
    @Shadow
    private static void register(Fluid fluid, boolean addBucket) { }

    private static Fluid ALE_REPLACE = new FluidBooze("ale", new ResourceLocation("rustic:blocks/fluids/booze/ale_still"), new ResourceLocation("rustic:blocks/fluids/booze/ale_flow")) {
        @Override
        protected void affectPlayer(World world, EntityPlayer player, float quality) {
            if (quality >= 0.5F) {
                float saturation = 4F * quality;
                player.getFoodStats().addStats(2, saturation);
                int duration = (int) (12000 * (Math.max(Math.abs((quality - 0.5F) * 2F), 0F)));
                player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("lycanitesmobs:immunization"), duration));
            } else {
                int duration = (int) (6000 * Math.max(1 - quality, 0));
                player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, duration));
                player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration));
            }
        }
    }.setInebriationChance(0.5F).setDensity(1004).setViscosity(1016);

    private static Fluid CIDER_REPLACE = new FluidBooze("cider", new ResourceLocation("rustic:blocks/fluids/booze/cider_still"), new ResourceLocation("rustic:blocks/fluids/booze/cider_flow")) {
        @Override
        protected void affectPlayer(World world, EntityPlayer player, float quality) {
            if (quality >= 0.5F) {
                float saturation = 2F * quality;
                player.getFoodStats().addStats(1, saturation);
                int duration = (int) (12000 * (Math.max(Math.abs((quality - 0.5F) * 2F), 0F)));
                player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("potioncore:magic_shield"), duration));
            } else {
                int duration = (int) (1200 * Math.max(1 - quality, 0));
                player.addPotionEffect(new PotionEffect(MobEffects.POISON, duration));
                duration = (int) (6000 * Math.max(1 - quality, 0));
                player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration));
            }
        }
    }.setInebriationChance(0.5F).setDensity(1004).setViscosity(1400);

    private static Fluid MEAD_REPLACE = new FluidBooze("mead", new ResourceLocation("rustic:blocks/fluids/booze/mead_still"), new ResourceLocation("rustic:blocks/fluids/booze/mead_flow")) {
        @Override
        protected void affectPlayer(World world, EntityPlayer player, float quality) {
            if (quality >= 0.5F) {
                float saturation = 2F * quality;
                player.getFoodStats().addStats(1, saturation);
                int duration = (int) (6000 * (Math.max(Math.abs((quality - 0.5F) * 2F), 0F)));
                player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("lycanitesmobs:rejuvenation"), duration));
            } else {
                int duration = (int) (800 * Math.max(1 - quality, 0));
                player.addPotionEffect(new PotionEffect(MobEffects.WITHER, duration));
                duration = (int) (6000 * Math.max(1 - quality, 0));
                player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration));
            }
        }
    }.setInebriationChance(0.5F).setDensity(1034).setViscosity(1500);

    @Inject(
            method = "register(Lnet/minecraftforge/fluids/Fluid;)V",
            at = @At(value = "HEAD"),
            remap = false
    )
    private static void rlmixins_rusticModFluids_registerBucketless(Fluid fluid, CallbackInfo ci) {
        RLMixins.LOGGER.log(Level.INFO, "Registering bucketless: " + fluid.getName());
    }

    @Inject(
            method = "register(Lnet/minecraftforge/fluids/Fluid;Z)V",
            at = @At(value = "HEAD"),
            remap = false
    )
    private static void rlmixins_rusticModFluids_registerBucket(Fluid fluid, boolean addBucket, CallbackInfo ci) {
        RLMixins.LOGGER.log(Level.INFO, "Registering with bucket: " + fluid.getName());
    }

    @Redirect(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lrustic/common/blocks/fluids/ModFluids;register(Lnet/minecraftforge/fluids/Fluid;Z)V",
                    ordinal = 0),
            remap = false
    )
    private static void rlmixins_rusticModFluids_initAle(Fluid fluid, boolean addBucket) {
        RLMixins.LOGGER.log(Level.INFO, "Replaced ALE");
        register(ALE_REPLACE, false);
    }

    @Redirect(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lrustic/common/blocks/fluids/ModFluids;register(Lnet/minecraftforge/fluids/Fluid;Z)V",
                    ordinal = 1),
            remap = false
    )
    private static void rlmixins_rusticModFluids_initCider(Fluid fluid, boolean addBucket) {
        RLMixins.LOGGER.log(Level.INFO, "Replaced CIDER");
        register(CIDER_REPLACE, false);
    }

    @Redirect(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lrustic/common/blocks/fluids/ModFluids;register(Lnet/minecraftforge/fluids/Fluid;Z)V",
                    ordinal = 3),
            remap = false
    )
    private static void rlmixins_rusticModFluids_initMead(Fluid fluid, boolean addBucket) {
        RLMixins.LOGGER.log(Level.INFO, "Replaced MEAD");
        register(MEAD_REPLACE, false);
    }
     */

    @Shadow(remap = false) public static Fluid ALE;

    @Shadow(remap = false) public static Fluid CIDER;

    @Shadow(remap = false) public static Fluid MEAD;


    @Redirect(
            method = "init",
            at = @At(value = "FIELD", target = "Lrustic/common/blocks/fluids/ModFluids;ALE:Lnet/minecraftforge/fluids/Fluid;", opcode = Opcodes.PUTSTATIC),
            remap = false
    )
    private static void rlmixins_rusticModFluids_initAle(Fluid value) {
        ALE = new FluidBooze("ale", new ResourceLocation("rustic:blocks/fluids/booze/ale_still"), new ResourceLocation("rustic:blocks/fluids/booze/ale_flow")) {
            @Override
            protected void affectPlayer(World world, EntityPlayer player, float quality) {
                if (quality >= 0.5F) {
                    float saturation = 4F * quality;
                    player.getFoodStats().addStats(2, saturation);
                    int duration = (int) (12000 * (Math.max(Math.abs((quality - 0.5F) * 2F), 0F)));
                    player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("lycanitesmobs:immunization"), duration));
                } else {
                    int duration = (int) (6000 * Math.max(1 - quality, 0));
                    player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, duration));
                    player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration));
                }
            }
        }.setInebriationChance(0.5F).setDensity(1004).setViscosity(1016);
    }

    @Redirect(
            method = "init",
            at = @At(value = "FIELD", target = "Lrustic/common/blocks/fluids/ModFluids;CIDER:Lnet/minecraftforge/fluids/Fluid;", opcode = Opcodes.PUTSTATIC),
            remap = false
    )
    private static void rlmixins_rusticModFluids_initCider(Fluid value) {
        CIDER = new FluidBooze("cider", new ResourceLocation("rustic:blocks/fluids/booze/cider_still"), new ResourceLocation("rustic:blocks/fluids/booze/cider_flow")) {
            @Override
            protected void affectPlayer(World world, EntityPlayer player, float quality) {
                if (quality >= 0.5F) {
                    float saturation = 2F * quality;
                    player.getFoodStats().addStats(1, saturation);
                    int duration = (int) (12000 * (Math.max(Math.abs((quality - 0.5F) * 2F), 0F)));
                    player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("potioncore:magic_shield"), duration));
                } else {
                    int duration = (int) (1200 * Math.max(1 - quality, 0));
                    player.addPotionEffect(new PotionEffect(MobEffects.POISON, duration));
                    duration = (int) (6000 * Math.max(1 - quality, 0));
                    player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration));
                }
            }
        }.setInebriationChance(0.5F).setDensity(1004).setViscosity(1400);
    }

    @Redirect(
            method = "init",
            at = @At(value = "FIELD", target = "Lrustic/common/blocks/fluids/ModFluids;MEAD:Lnet/minecraftforge/fluids/Fluid;", opcode = Opcodes.PUTSTATIC),
            remap = false
    )
    private static void rlmixins_rusticModFluids_initMead(Fluid value) {
        MEAD = new FluidBooze("mead", new ResourceLocation("rustic:blocks/fluids/booze/mead_still"), new ResourceLocation("rustic:blocks/fluids/booze/mead_flow")) {
            @Override
            protected void affectPlayer(World world, EntityPlayer player, float quality) {
                if (quality >= 0.5F) {
                    float saturation = 2F * quality;
                    player.getFoodStats().addStats(1, saturation);
                    int duration = (int) (6000 * (Math.max(Math.abs((quality - 0.5F) * 2F), 0F)));
                    player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("lycanitesmobs:rejuvenation"), duration));
                } else {
                    int duration = (int) (800 * Math.max(1 - quality, 0));
                    player.addPotionEffect(new PotionEffect(MobEffects.WITHER, duration));
                    duration = (int) (6000 * Math.max(1 - quality, 0));
                    player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration));
                }
            }
        }.setInebriationChance(0.5F).setDensity(1034).setViscosity(1500);
    }
}