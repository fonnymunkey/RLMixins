package rlmixins.mixin.vanilla;

import net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.wrapper.IEntityMixin;

@Mixin(TileEntityMobSpawnerRenderer.class)
public abstract class TileEntityMobSpawnerRendererMixin {
	
	@Redirect(
			method = "renderMob",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/tileentity/MobSpawnerBaseLogic;getCachedEntity()Lnet/minecraft/entity/Entity;")
	)
	private static Entity rlmixins_vanillaTileEntityMobSpawnerRenderer_renderMob(MobSpawnerBaseLogic instance) {
		Entity entity = instance.getCachedEntity();
		if(entity != null) ((IEntityMixin)entity).rlmixins$setFakeEntity(true);
		return entity;
	}
}