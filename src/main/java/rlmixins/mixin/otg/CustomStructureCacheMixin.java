package rlmixins.mixin.otg;

import com.pg85.otg.common.LocalWorld;
import com.pg85.otg.customobjects.structures.CustomStructureCache;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import rlmixins.RLMixins;
import rlmixins.handlers.ForgeConfigHandler;
import rlmixins.wrapper.IObjectSpawnerMixin;

import java.util.concurrent.TimeUnit;

@Mixin(CustomStructureCache.class)
public abstract class CustomStructureCacheMixin {
	
	@Shadow(remap = false)
	private LocalWorld world;
	
	@Shadow(remap = false)
	protected abstract void saveStructureCache();
	
	/**
	 * @author Meldexun
	 * @reason Fix save-to-disk timeout crash during chunk generation
	 */
	@Overwrite(remap = false)
	public void saveToDisk() {
		//OTG.log(LogMarker.INFO, "Saving structure and pregenerator data.");
		RLMixins.LOGGER.log(Level.INFO, "OTG Saving structure and pregenerator data.");
		
		try {
			if(!((IObjectSpawnerMixin)this.world.getObjectSpawner()).rlmixins$getLock().tryLock(ForgeConfigHandler.server.otgSaveToDiskLockTime, TimeUnit.SECONDS)) {
				throw new RuntimeException();
			}
		}
		catch(InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException();
		}
		try {
			saveStructureCache();
			this.world.getObjectSpawner().saveRequired = false;
		} finally {
			((IObjectSpawnerMixin)this.world.getObjectSpawner()).rlmixins$getLock().unlock();
		}
		
		//OTG.log(LogMarker.INFO, "Structure and pregenerator data saved.");
		RLMixins.LOGGER.log(Level.INFO, "OTG Structure and pregenerator data saved.");
	}
}