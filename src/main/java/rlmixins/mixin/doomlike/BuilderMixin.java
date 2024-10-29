package rlmixins.mixin.doomlike;

import jaredbgreat.dldungeons.builder.Builder;
import jaredbgreat.dldungeons.planner.Dungeon;
import jaredbgreat.dldungeons.util.cache.Coords;
import jaredbgreat.dldungeons.util.cache.WeakCache;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Builder.class)
public abstract class BuilderMixin {
	
	@Shadow(remap = false)
	@Final
	private static WeakCache<Dungeon> DUNGEON_CACHE;
	
	/**
	 * @author fonnymunkey
	 * @reason fix error during chunk generation in an area where dungeons are blacklisted
	 */
	@Overwrite(remap = false)
	public static void buildDungeonChunk(int cx, int cz, Coords dc, World world) throws Throwable {
		Dungeon dungeon = DUNGEON_CACHE.get(dc);
		if(dungeon == null) {
			dungeon = new Dungeon(world, dc);
			DUNGEON_CACHE.add(dungeon);
		}
		
		if(dungeon != null && dungeon.map != null) {
			dungeon.map.buildInChunk(dungeon, cx, cz);
		}
	}
}