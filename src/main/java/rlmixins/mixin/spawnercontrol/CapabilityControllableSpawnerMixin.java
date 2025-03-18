package rlmixins.mixin.spawnercontrol;

import ladysnake.spawnercontrol.config.SpawnerConfig;
import ladysnake.spawnercontrol.controlledspawner.CapabilityControllableSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.ForgeConfigHandler;

import javax.annotation.Nonnull;

@Mixin(CapabilityControllableSpawner.DefaultControllableSpawner.class)
public abstract class CapabilityControllableSpawnerMixin {

    @Shadow(remap = false)
    private int spawnedMobsCount;
    @Shadow(remap = false)
    @Nonnull public abstract SpawnerConfig getConfig();

    // Allows nerfing instead of removing spawner abuse by as spawns are disabled by mobThreshold
    //      and mob loot is disabled by mobThreshold + spawnerControlGraceThreshold
    //      Although delays when spawner turns into air, as HarvestDropsEvent does not find a tileentity
    @Redirect(
            method = "incrementSpawnedMobsCount",
            at = @At(value = "FIELD", target = "Lladysnake/spawnercontrol/config/SpawnerConfig;breakSpawner:Z"),
            remap = false
    )
    public boolean rlmixins_spawnercontrolDefaultControllableSpawner_breakSpawnerAfterGrace(SpawnerConfig instance) {
        return (this.spawnedMobsCount >= instance.mobThreshold + ForgeConfigHandler.server.spawnerControlGraceThreshold) && instance.breakSpawner;
    }
}
