package rlmixins.mixin.spawnercontrol;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import ladysnake.spawnercontrol.SpawnerEventHandler;
import ladysnake.spawnercontrol.config.SpawnerConfig;
import ladysnake.spawnercontrol.controlledspawner.IControllableSpawner;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.wrapper.SpawnerControlWrapper;

@Mixin(SpawnerEventHandler.class)
public class SpawnerEventHandlerMixin {

    // Discourage the basic form of spawner abuse of spawning in many mobs over the config limit by stopping xp and loot drop after a specified amount of deaths
    @ModifyExpressionValue(
            method = "onLivingDeath",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getTileEntity(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/tileentity/TileEntity;")
    )
    private static TileEntity rlmixins_spawnercontrolEventHandler_onLivingDeath_incrementCount(TileEntity tile, @Local World world, @Local long spawnerPos, @Local(argsOnly = true) LivingDeathEvent event){
        //If the spawner is already broken, we count additionally killed mobs from that spawner until a threshold
        if(!(tile instanceof TileEntityMobSpawner))
            if(!SpawnerControlWrapper.incrementBrokenSpawner(world.provider.getDimension(), spawnerPos))
                event.getEntity().getEntityData().setBoolean("xat:summoned", true);
        return tile;
    }

    // When the spawner breaks from 10+ deaths, create an entry in the map to track the dying mobs created by the spawner
    @WrapOperation(
            method = "onLivingDeath",
            at = @At(value = "INVOKE", target = "Lladysnake/spawnercontrol/controlledspawner/IControllableSpawner;incrementSpawnedMobsCount()Z"),
            remap = false
    )
    private static boolean rlmixins_spawnercontrolEventHandler_onLivingDeath_createMapEntry(IControllableSpawner spawner, Operation<Boolean> original, @Local World world, @Local long spawnerPos){
        //Will be incremented to threshold inside the call we inject at, then directly get broken
        if(spawner.getSpawnedMobsCount() == spawner.getConfig().mobThreshold - 1)
            SpawnerControlWrapper.registerBrokenSpawner(world.provider.getDimension(), spawnerPos);
        return original.call(spawner);
    }

    // When someone breaks the spawner, create an entry in the map to track the dying mobs created by the spawner (otherwise all mobs killed after breaking the spawner would get summoned tag
    @Inject(
            method = "onBlockBreak",
            at = @At(value = "FIELD", target = "Lladysnake/spawnercontrol/config/SpawnerConfig;xpDropped:I"),
            remap = false
    )
    private static void rlmixins_spawnercontrolEventHandler_onBlockBreak(BlockEvent.BreakEvent event, CallbackInfo ci, @Local SpawnerConfig cfg){
        if(cfg.incrementOnMobDeath)
            SpawnerControlWrapper.registerBrokenSpawner(event.getWorld().provider.getDimension(), event.getPos().toLong());
    }
}
