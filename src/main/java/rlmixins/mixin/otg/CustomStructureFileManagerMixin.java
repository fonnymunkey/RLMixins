package rlmixins.mixin.otg;

import com.pg85.otg.common.LocalWorld;
import com.pg85.otg.customobjects.structures.CustomStructure;
import com.pg85.otg.customobjects.structures.CustomStructureFileManager;
import com.pg85.otg.customobjects.structures.bo4.BO4CustomStructure;
import com.pg85.otg.customobjects.structures.bo4.CustomStructurePlaceHolder;
import com.pg85.otg.util.ChunkCoordinate;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import rlmixins.RLMixins;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.*;

@Mixin(CustomStructureFileManager.class)
public abstract class CustomStructureFileManagerMixin {
	
	/**
	 * @author fonnymunkey
	 * @reason improve load times on larger maps
	 */
	@Overwrite(remap = false)
	private static void mergeRegionData(LocalWorld world, HashMap<CustomStructure,ArrayList<ChunkCoordinate>> result, HashMap<CustomStructure, ArrayList<ChunkCoordinate>> output) {
		for(Map.Entry<CustomStructure, ArrayList<ChunkCoordinate>> entryResult : result.entrySet()) {
			//Reflect to directly get output entry instead of iterating it fully
			Map.Entry<CustomStructure, ArrayList<ChunkCoordinate>> entryOutput = rlmixins$stinkyReflect(output, entryResult.getKey());
			if(rlmixins$reflectionFailed) {
				//If failed, default to non-reflection-but-still-improved checks
				if(output.containsKey(entryResult.getKey())) {
					entryOutput = null;
					//Iterate to get entry from output first, avoid needing to create new HashSet to avoid concurrent exception
					for(Map.Entry<CustomStructure, ArrayList<ChunkCoordinate>> entryOutput1 : output.entrySet()) {
						if(entryResult.getKey().equals(entryOutput1.getKey())) {
							entryOutput = entryOutput1;
							break;
						}
					}
					//Should never be null by this point but check anyways cause why not
					if(entryOutput != null) {
						if(entryResult.getKey() instanceof CustomStructurePlaceHolder) {
							((CustomStructurePlaceHolder)entryResult.getKey()).mergeWithCustomStructure(world, (BO4CustomStructure)entryOutput.getKey());
							ArrayList<ChunkCoordinate> coords = entryOutput.getValue();
							coords.addAll(entryResult.getValue());
						}
						else if(entryOutput.getKey() instanceof CustomStructurePlaceHolder) {
							((CustomStructurePlaceHolder)entryOutput.getKey()).mergeWithCustomStructure(world, (BO4CustomStructure)entryResult.getKey());
							ArrayList<ChunkCoordinate> coords = entryResult.getValue();
							coords.addAll(entryOutput.getValue());
							
							// Be sure to remove before putting, or only the value gets replaced.
							output.remove(entryResult.getKey());
							output.put(entryResult.getKey(), entryResult.getValue());
						}
					}
				}
				else {
					output.put(entryResult.getKey(), entryResult.getValue());
				}
			}
			else {
				//If reflection successful, skip containsKey check, just check null
				if(entryOutput == null) {
					output.put(entryResult.getKey(), entryResult.getValue());
				}
				else {
					if(entryResult.getKey() instanceof CustomStructurePlaceHolder) {
						((CustomStructurePlaceHolder)entryResult.getKey()).mergeWithCustomStructure(world, (BO4CustomStructure)entryOutput.getKey());
						ArrayList<ChunkCoordinate> coords = entryOutput.getValue();
						coords.addAll(entryResult.getValue());
					}
					else if(entryOutput.getKey() instanceof CustomStructurePlaceHolder) {
						((CustomStructurePlaceHolder)entryOutput.getKey()).mergeWithCustomStructure(world, (BO4CustomStructure)entryResult.getKey());
						ArrayList<ChunkCoordinate> coords = entryResult.getValue();
						coords.addAll(entryOutput.getValue());
						
						// Be sure to remove before putting, or only the value gets replaced.
						output.remove(entryResult.getKey());
						output.put(entryResult.getKey(), entryResult.getValue());
					}
				}
			}
		}
	}
	
	@Unique
	private static boolean rlmixins$reflectionFailed = false;
	
	@Unique
	private static Method rlmixins$HashMap_hash = null;
	
	@Unique
	private static Method rlmixins$HashMap_getNode = null;
	
	@SuppressWarnings("unchecked")
	@Nullable
	@Unique
	private static Map.Entry<CustomStructure, ArrayList<ChunkCoordinate>> rlmixins$stinkyReflect(HashMap<CustomStructure, ArrayList<ChunkCoordinate>> map, CustomStructure key) {
		if(rlmixins$reflectionFailed) return null;
		if(rlmixins$HashMap_hash == null) {
			try {
				rlmixins$HashMap_hash = HashMap.class.getDeclaredMethod("hash", Object.class);
				rlmixins$HashMap_hash.setAccessible(true);
			}
			catch(Exception ex) {
				RLMixins.LOGGER.log(Level.INFO, "RLMixins failed to reflect HashMap::hash, defaulting to normal iteration");
				rlmixins$reflectionFailed = true;
			}
		}
		if(rlmixins$HashMap_getNode == null) {
			try {
				rlmixins$HashMap_getNode = HashMap.class.getDeclaredMethod("getNode", int.class, Object.class);
				rlmixins$HashMap_getNode.setAccessible(true);
			}
			catch(Exception ex) {
				RLMixins.LOGGER.log(Level.INFO, "RLMixins failed to reflect HashMap::getNode, defaulting to normal iteration");
				rlmixins$reflectionFailed = true;
			}
		}
		if(rlmixins$reflectionFailed || rlmixins$HashMap_hash == null || rlmixins$HashMap_getNode == null) return null;
		try {
			int hash = (int)rlmixins$HashMap_hash.invoke(null, key);
			Object returnable = rlmixins$HashMap_getNode.invoke(map, hash, key);
			
			if(returnable instanceof Map.Entry<?,?>) {
				return (Map.Entry<CustomStructure, ArrayList<ChunkCoordinate>>)returnable;
			}
			else {
				return null;
			}
		}
		catch(Exception ex) {
			RLMixins.LOGGER.log(Level.INFO, "RLMixins failed to invoke HashMap reflection, defaulting to normal iteration");
			rlmixins$reflectionFailed = true;
			return null;
		}
	}
}