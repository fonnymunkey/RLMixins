package rlmixins.mixin.rustic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.ForgeConfigHandler;
import rustic.common.blocks.fluids.FluidBooze;
import rustic.common.blocks.fluids.ModFluids;

@Mixin(ModFluids.class)
public abstract class ModFluidsMixin {

    @Shadow(remap = false) public static Fluid ALE;
    @Shadow(remap = false) public static Fluid CIDER;
    @Shadow(remap = false) public static Fluid IRON_WINE;
    @Shadow(remap = false) public static Fluid MEAD;
    @Shadow(remap = false) public static Fluid WILDBERRY_WINE;
    @Shadow(remap = false) public static Fluid WINE;

    /**
     * Adds configuration to timing and effect
     */
    @Redirect(
            method = "init",
            at = @At(value = "FIELD", target = "Lrustic/common/blocks/fluids/ModFluids;ALE:Lnet/minecraftforge/fluids/Fluid;", opcode = Opcodes.PUTSTATIC),
            remap = false
    )
    private static void rlmixins_rusticModFluids_initAle(Fluid value) {
        ALE = new FluidBooze("ale", new ResourceLocation("rustic:blocks/fluids/booze/ale_still"), new ResourceLocation("rustic:blocks/fluids/booze/ale_flow")) {
            @Override
            protected void affectPlayer(World world, EntityPlayer player, float quality) {
                if(quality >= 0.5F) {
                    float saturation = 4.0F * quality;
                    player.getFoodStats().addStats(2, saturation);
                    int duration = (int)(ForgeConfigHandler.server.aleMaxDurationPositive * (quality - 0.5F) * 2.0F);
                    player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation(ForgeConfigHandler.server.aleEffect), duration));
                }
                else {
                    int duration = (int)(ForgeConfigHandler.server.aleMaxDurationHunger * Math.max(1.0F - quality, 0));
                    player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, duration));
                    duration = (int)(ForgeConfigHandler.server.aleMaxDurationNausea * Math.max(1.0F - quality, 0));
                    player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration));
                }
            }
        }.setInebriationChance(ForgeConfigHandler.server.aleInebriationChance).setDensity(1004).setViscosity(1016);
    }

    /**
     * Adds configuration to timing and effect
     */
    @Redirect(
            method = "init",
            at = @At(value = "FIELD", target = "Lrustic/common/blocks/fluids/ModFluids;CIDER:Lnet/minecraftforge/fluids/Fluid;", opcode = Opcodes.PUTSTATIC),
            remap = false
    )
    private static void rlmixins_rusticModFluids_initCider(Fluid value) {
        CIDER = new FluidBooze("cider", new ResourceLocation("rustic:blocks/fluids/booze/cider_still"), new ResourceLocation("rustic:blocks/fluids/booze/cider_flow")) {
            @Override
            protected void affectPlayer(World world, EntityPlayer player, float quality) {
                if(quality >= 0.5F) {
                    float saturation = 2.0F * quality;
                    player.getFoodStats().addStats(1, saturation);
                    int duration = (int)(ForgeConfigHandler.server.ciderMaxDurationPositive * (quality - 0.5F) * 2.0F);
                    player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation(ForgeConfigHandler.server.ciderEffect), duration));
                }
                else {
                    int duration = (int)(ForgeConfigHandler.server.ciderMaxDurationPoison * Math.max(1.0F - quality, 0));
                    player.addPotionEffect(new PotionEffect(MobEffects.POISON, duration));
                    duration = (int)(ForgeConfigHandler.server.ciderMaxDurationNausea * Math.max(1.0F - quality, 0));
                    player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration));
                }
            }
        }.setInebriationChance(ForgeConfigHandler.server.ciderInebriationChance).setDensity(1004).setViscosity(1400);
    }

    /**
     * Adds configuration to timing and effect
     */
    @Redirect(
            method = "init",
            at = @At(value = "FIELD", target = "Lrustic/common/blocks/fluids/ModFluids;IRON_WINE:Lnet/minecraftforge/fluids/Fluid;", opcode = Opcodes.PUTSTATIC),
            remap = false
    )
    private static void rlmixins_rusticModFluids_initIronWine(Fluid value) {
        IRON_WINE = new FluidBooze("ironwine", new ResourceLocation("rustic:blocks/fluids/booze/iron_wine_still"), new ResourceLocation("rustic:blocks/fluids/booze/iron_wine_flow")) {
           @Override
           protected void affectPlayer(World world, EntityPlayer player, float quality) {
               if(quality >= 0.5F) {
                   float saturation = 2.0F * quality;
                   player.getFoodStats().addStats(1, saturation);
                   float absorption = ForgeConfigHandler.server.ironWineMaxAbsorption * (quality - 0.5F) * 2.0F;
                   player.setAbsorptionAmount(Math.max(Math.min(player.getAbsorptionAmount() + absorption, 20.0F), player.getAbsorptionAmount()));
               }
               else {
                   int duration = (int)(ForgeConfigHandler.server.ironWineMaxDurationNausea * Math.max(1.0F - quality, 0.0F));
                   player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration));
                   float damage = ForgeConfigHandler.server.ironWineMaxDamage * 2.0F * Math.max(0.5F - quality, 0) + 1.0F;
                   player.attackEntityFrom(DamageSource.MAGIC, damage);
               }
            }
        }.setInebriationChance(ForgeConfigHandler.server.ironWineInebriationChance).setDensity(1034).setViscosity(1400);
    }

    /**
     * Adds configuration to timing and effect
     */
    @Redirect(
            method = "init",
            at = @At(value = "FIELD", target = "Lrustic/common/blocks/fluids/ModFluids;MEAD:Lnet/minecraftforge/fluids/Fluid;", opcode = Opcodes.PUTSTATIC),
            remap = false
    )
    private static void rlmixins_rusticModFluids_initMead(Fluid value) {
        MEAD = new FluidBooze("mead", new ResourceLocation("rustic:blocks/fluids/booze/mead_still"), new ResourceLocation("rustic:blocks/fluids/booze/mead_flow")) {
            @Override
            protected void affectPlayer(World world, EntityPlayer player, float quality) {
                if(quality >= 0.5F) {
                    float saturation = 2F * quality;
                    player.getFoodStats().addStats(1, saturation);
                    int duration = (int)(ForgeConfigHandler.server.meadMaxDurationPositive * (quality - 0.5F) * 2.0F);
                    player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation(ForgeConfigHandler.server.meadEffect), duration));
                }
                else {
                    int duration = (int)(ForgeConfigHandler.server.meadMaxDurationWither * Math.max(1.0F - quality, 0));
                    player.addPotionEffect(new PotionEffect(MobEffects.WITHER, duration));
                    duration = (int)(ForgeConfigHandler.server.meadMaxDurationNausea * Math.max(1.0F - quality, 0));
                    player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration));
                }
            }
        }.setInebriationChance(ForgeConfigHandler.server.meadInebriationChance).setDensity(1034).setViscosity(1500);
    }

    /**
     * Makes Wildberry Wine Effect Scale and adds configuration to timing and effect
     */
    @Redirect(
            method = "init",
            at = @At(value = "FIELD", target = "Lrustic/common/blocks/fluids/ModFluids;WILDBERRY_WINE:Lnet/minecraftforge/fluids/Fluid;", opcode = Opcodes.PUTSTATIC),
            remap = false
    )
    private static void rlmixins_rusticModFluids_initWildberryWine(Fluid value) {
        WILDBERRY_WINE = new FluidBooze("wildberrywine", new ResourceLocation("rustic:blocks/fluids/booze/wildberry_wine_still"), new ResourceLocation("rustic:blocks/fluids/booze/wildberry_wine_flow")) {
            @Override
            protected void affectPlayer(World world, EntityPlayer player, float quality) {
                if(quality >= 0.5F) {
                    float saturation = 2.0F * quality;
                    player.getFoodStats().addStats(1, saturation);
                    PotionEffect[] effects = player.getActivePotionEffects().toArray(new PotionEffect[0]);
                    for(PotionEffect potionEffect : effects) {
                        if(!potionEffect.getPotion().isBadEffect() && potionEffect.getAmplifier() < ForgeConfigHandler.server.wildberryWineMaxAmplifier) {
                            if(world.rand.nextFloat() <= (quality - 0.5F) * 2.0F) {
                                player.addPotionEffect(new PotionEffect(potionEffect.getPotion(), potionEffect.getDuration(), Math.min(potionEffect.getAmplifier() + ForgeConfigHandler.server.wildberryWineMaxAmplifierIncrease, ForgeConfigHandler.server.wildberryWineMaxAmplifier), potionEffect.getIsAmbient(), potionEffect.doesShowParticles()));
                            }
                        }
                    }
                }
                else {
                    PotionEffect[] effects = player.getActivePotionEffects().toArray(new PotionEffect[0]);
                    for(PotionEffect effect : effects) {
                        if(!effect.getPotion().isBadEffect()) {
                            int amp = effect.getAmplifier() - ForgeConfigHandler.server.wildberryWineAmplifierDecrease;
                            player.removePotionEffect(effect.getPotion());
                            if(amp >= 0) {
                                player.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration(), amp, effect.getIsAmbient(), effect.doesShowParticles()));
                            }
                        }
                    }
                    int duration = (int)(ForgeConfigHandler.server.wildberryWineMaxDurationNausea * Math.max(1.0F - quality, 0));
                    player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration));
                }

            }
        }.setInebriationChance(ForgeConfigHandler.server.wildberryWineInebriationChance).setDensity(1034).setViscosity(1500);
    }

    /**
     * Adds configuration to timing and effect
     */
    @Redirect(
            method = "init",
            at = @At(value = "FIELD", target = "Lrustic/common/blocks/fluids/ModFluids;WINE:Lnet/minecraftforge/fluids/Fluid;", opcode = Opcodes.PUTSTATIC),
            remap = false
    )
    private static void rlmixins_rusticModFluids_initWine(Fluid value) {
        WINE = new FluidBooze("wine", new ResourceLocation("rustic:blocks/fluids/booze/wine_still"), new ResourceLocation("rustic:blocks/fluids/booze/wine_flow")) {
            protected void affectPlayer(World world, EntityPlayer player, float quality) {
                if(quality >= 0.5F) {
                    float saturation = 2.0F * quality;
                    player.getFoodStats().addStats(1, saturation);
                    PotionEffect[] effects = player.getActivePotionEffects().toArray(new PotionEffect[0]);
                    for(PotionEffect effect : effects) {
                        int durationIncrease = (int)(ForgeConfigHandler.server.wineMaximumDurationIncrease * (quality - 0.5F) * 2.0F);
                        if(!effect.getPotion().isBadEffect() && effect.getDuration() < (int)ForgeConfigHandler.server.wineMaximumDuration) {
                            int duration = Math.max(Math.min(effect.getDuration() + durationIncrease, (int)ForgeConfigHandler.server.wineMaximumDuration), effect.getDuration());
                            player.addPotionEffect(new PotionEffect(effect.getPotion(), duration, effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles()));
                        }
                    }
                }
                else {
                    PotionEffect[] effects = player.getActivePotionEffects().toArray(new PotionEffect[0]);
                    for(PotionEffect effect : effects) {
                        int durationDecrease = (int)(ForgeConfigHandler.server.wineMaximumDurationDecrease * Math.max(0.5F - quality, 0));
                        if(!effect.getPotion().isBadEffect()) {
                            int duration = effect.getDuration() - durationDecrease;
                            player.removePotionEffect(effect.getPotion());
                            if(duration > 0) {
                                player.addPotionEffect(new PotionEffect(effect.getPotion(), duration, effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles()));
                            }
                        }
                    }
                    int duration = (int)(ForgeConfigHandler.server.wineMaxDurationNausea * Math.max(1.0F - quality, 0));
                    player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration));
                }
            }
        }.setInebriationChance(ForgeConfigHandler.server.wineInebriationChance).setDensity(1034).setViscosity(1500);
    }
}