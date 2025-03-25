package rlmixins.mixin.spawnercontrol;

import com.llamalad7.mixinextras.sugar.Local;
import ladysnake.spawnercontrol.SpawnerEventHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpawnerEventHandler.class)
public class SpawnerEventHandlerMixin {

    // Discourage the basic form of spawner abuse of spawning in many mobs over the config limit
    @Inject(
            method = "onLivingDeath(Lnet/minecraftforge/event/entity/living/LivingDeathEvent;)V",
            at = @At(value = "INVOKE",
                target = "Lnet/minecraft/world/World;getTileEntity(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/tileentity/TileEntity;"
            )
    )
    private static void rlmixins_spawnercontrolEventHandler_onLivingDeath(LivingDeathEvent event, CallbackInfo ci, @Local World world, @Local long spawnerPos){
        TileEntity tile = world.getTileEntity(BlockPos.fromLong(spawnerPos));
        if(!(tile instanceof TileEntityMobSpawner)) event.getEntity().getEntityData().setBoolean("xat:summoned", true);
    }
}
