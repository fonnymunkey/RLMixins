package rlmixins.mixin.otg;

import com.pg85.otg.OTG;
import com.pg85.otg.common.LocalWorld;
import com.pg85.otg.generator.ObjectSpawner;
import com.pg85.otg.logging.LogMarker;
import com.pg85.otg.util.ChunkCoordinate;
import org.spongepowered.asm.mixin.*;
import rlmixins.handlers.ForgeConfigHandler;
import rlmixins.wrapper.IObjectSpawnerMixin;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Mixin(ObjectSpawner.class)
public abstract class ObjectSpawnerMixin implements IObjectSpawnerMixin {
	
	@Shadow(remap = false)
	public boolean processing;
	
	@Shadow(remap = false)
	@Final
	private LocalWorld world;
	
	@Shadow(remap = false)
	protected abstract void doPopulate(ChunkCoordinate chunkCoord);
	
	@Shadow(remap = false)
	public boolean saveRequired;
	
	@Unique
	public final Lock rlmixins$lock = new ReentrantLock();
	
	@Unique
	public Lock rlmixins$getLock() {
		return this.rlmixins$lock;
	}
	
	/**
	 * @author Meldexun
	 * @reason Fix save-to-disk timeout crash during chunk generation
	 */
	@Overwrite(remap = false)
	public void populate(ChunkCoordinate chunkCoord) {
		try {
			if(!rlmixins$lock.tryLock(ForgeConfigHandler.server.otgPopulateLockTime, TimeUnit.SECONDS)) {
				throw new RuntimeException();
			}
		}
		catch(InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException();
		}
		
		try {
			if(!this.processing) {
				this.processing = true;
				
				// Cache all biomes in the area being populated (2x2 chunks)
				this.world.cacheBiomesForPopulation(chunkCoord);
				this.doPopulate(chunkCoord);
				
				this.processing = false;
			}
			else {
				// Don't use the population chunk biome cache during cascading chunk generation
				this.world.invalidatePopulationBiomeCache();
				this.doPopulate(chunkCoord);
				
				OTG.log(LogMarker.INFO, "Cascading chunk generation detected.");
				if(OTG.getPluginConfig().developerMode) {
					OTG.log(LogMarker.INFO, Arrays.toString(Thread.currentThread().getStackTrace()));
				}
			}
			this.saveRequired = true;
		}
		finally {
			rlmixins$lock.unlock();
		}
		
		// Resource spawning may have changed terrain dramatically, update
		// the spawnpoint so players don't spawn mid-air or underground
		if(chunkCoord.equals(this.world.getSpawnChunk())) {
			this.world.updateSpawnPointY();
		}
	}
}