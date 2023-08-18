package rlmixins;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.spongepowered.asm.launch.MixinBootstrap;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.mixin.Mixins;
import rlmixins.handlers.ForgeConfigHandler;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.SortingIndex(-5000)
public class RLMixinsPlugin implements IFMLLoadingPlugin {

	private static final Map<String, String> configMap = setupMap();

	private static Map<String, String> setupMap() {
		Map<String, String> map = new HashMap<>();
		map.put("Outdated Chunk Data (Vanilla)", "mixins.rlmixins.core.chunkdata.json");
		map.put("Offhand Enchants (Vanilla/RLCombat)", "mixins.rlmixins.core.offhandenchants.json");
		map.put("Boss Cart/Boat Cheese (Vanilla/InfernalMobs/ScalingHealth/Champions)", "mixins.rlmixins.core.bosscart.json");
		map.put("AntiWarp Handling (Vanilla/BetterSurvival)", "mixins.rlmixins.core.antiwarp.json");
		map.put("Configurable Fall (Vanilla)", "mixins.rlmixins.core.configfall.json");
		map.put("Lowered Crouch (Vanilla)", "mixins.rlmixins.core.lowercrouch.json");
		map.put("Health Desync Patch (Vanilla)", "mixins.rlmixins.core.healthdesync.json");
		map.put("Smoothed Crouching (Vanilla)", "mixins.rlmixins.core.smoothcrouch.json");
		map.put("Mending Priorities (Vanilla)", "mixins.rlmixins.core.mending.json");
		map.put("ChunkAnimator XRay (Vanilla/ChunkAnimator)", "mixins.rlmixins.core.chunkanimatorxray.json");
		map.put("Anvil Too Expensive (Vanilla/AnvilPatch)", "mixins.rlmixins.core.anvilexpensive.json");
		map.put("Enchantment Item Attributes (Vanilla/SME)", "mixins.rlmixins.core.enchantattribute.json");
		map.put("Enchantment ItemStack Damage (Vanilla/SME)", "mixins.rlmixins.core.enchantdamage.json");
		map.put("Entity Tracker Desync (Vanilla)", "mixins.rlmixins.core.entitytracker.json");
		map.put("Missing Particle Rendering (Vanilla)", "mixins.rlmixins.core.particlerender.json");
		map.put("Chunk Entity List (Vanilla)", "mixins.rlmixins.core.entitylist.json");
		map.put("Prevent Shulker Crate Insertion (Vanilla/Charm)", "mixins.rlmixins.core.shulkerinsertion.json");
		map.put("Stop Pigmen Portal Spawning (Vanilla)", "mixins.rlmixins.core.pigmenportal.json");
		map.put("Tipped Arrow Blacklist (Vanilla)", "mixins.rlmixins.core.tippedarrow.json");
		map.put("EXPERIMENTAL: Teleporting Lag Patch (Vanilla)", "mixins.rlmixins.core.entityteleportcollision.json");
		map.put("Stray/Husk Sky Spawning Fix (Vanilla)", "mixins.rlmixins.core.strayhuskspawning.json");
		map.put("Zombie Curing Ticks Spawners (Vanilla/MobSpawnerControl)", "mixins.rlmixins.core.zombiecuring.json");
		map.put("Potion Amplifier Visibility (Vanilla)", "mixins.rlmixins.core.potionamplifier.json");
		map.put("Stop Sleeping Resetting Weather MC-63340 (Vanilla)", "mixins.rlmixins.core.norainreset.json");
		map.put("Blast Protection Knockback Patch MC-198809 (Vanilla)", "mixins.rlmixins.core.blastprotknockback.json");
		map.put("Cache WorldBorder currentTime (Vanilla)", "mixins.rlmixins.core.cacheborder.json");
		map.put("Remove Water Chunk Gen Ticking (Vanilla)", "mixins.rlmixins.core.waterchunkgen.json");
		map.put("Slowed Item Entity Movement (Vanilla)", "mixins.rlmixins.core.itemmovement.json");
		map.put("Entity Tracker Warning More Info (Vanilla)", "mixins.rlmixins.core.entitytrackerdebug.json");
		map.put("Random Respawn Attempt Avoid Oceans Config (Vanilla)", "mixins.rlmixins.core.randomspawnocean.json");

		map.put("Forge Suppress Dangerous Prefix Errors (Forge)", "mixins.rlmixins.core.dangerousprefix.json");
		map.put("Forge Suppress Broken Ore Dictionary Errors (Forge)", "mixins.rlmixins.core.brokenoredict.json");

		map.put("Enable Dragon Dismount Fix (Vanilla/IceAndFire)", "mixins.rlmixins.core.infdismount.json");
		map.put("BetterSurvival TickDynamic Crash (BetterSurvival)", "mixins.rlmixins.core.arrowaccessor.json");

		return Collections.unmodifiableMap(map);
	}

	public RLMixinsPlugin() {
		MixinBootstrap.init();
		Mixins.addConfiguration("mixins.rlmixins.init.json");

		for(Map.Entry<String, String> entry : configMap.entrySet()) {
			if(ForgeConfigHandler.getBoolean(entry.getKey())) {
				RLMixins.LOGGER.log(Level.INFO, "RLMixins Early Loading: " + entry.getKey());
				Mixins.addConfiguration(entry.getValue());
			}
		}
	}
	@Override
	public String[] getASMTransformerClass()
	{
		return new String[0];
	}
	
	@Override
	public String getModContainerClass()
	{
		return null;
	}
	
	@Override
	public String getSetupClass()
	{
		return null;
	}
	
	@Override
	public void injectData(Map<String, Object> data)
	{
	}
	
	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}
}
