package rlmixins.mixin.vanilla;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import rlmixins.wrapper.IEntityMixin;

@Mixin(Entity.class)
public abstract class EntitySpawnerRenderMixin implements IEntityMixin {
	
	@Unique
	private boolean rlmixins$isFakeRender = false;
	
	@Override
	public void rlmixins$setFakeEntity(boolean val) {
		this.rlmixins$isFakeRender = val;
	}
	
	@Override
	public boolean rlmixins$isFakeEntity() {
		return this.rlmixins$isFakeRender;
	}
}