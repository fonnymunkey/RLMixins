package rlmixins;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import fermiumbooter.FermiumRegistryAPI;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.launch.MixinBootstrap;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import rlmixins.handlers.ForgeConfigHandler;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.SortingIndex(-5000)
public class RLMixinsPlugin implements IFMLLoadingPlugin {

	private static final Map<String, String> earlyMap = setupEarlyMap();

	private static Map<String, String> setupEarlyMap() {
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
		map.put("Random Respawn Attempt Avoid Oceans Config (Vanilla)", "mixins.rlmixins.core.randomspawnocean.json");
		map.put("Modify Gamma Max And Min (Vanilla)", "mixins.rlmixins.core.modifygamma.json");
		map.put("Broken Advancement Log Spam Silence (Vanilla/Forge)", "mixins.rlmixins.core.advancementspam.json");
		map.put("Nuke Advancements (Vanilla)", "mixins.rlmixins.core.advancementnuke.json");
		map.put("Spawn Chunk Radius Patch (Vanilla)", "mixins.rlmixins.core.spawnchunkradius.json");
		map.put("Bloodmoon Optifine Patch (Bloodmoon)", "mixins.rlmixins.core.bloodmoonoptifine.json");
		map.put("Cache Player Chunk Visibility (Vanilla)", "mixins.rlmixins.core.refreshchunkcache.json");
		map.put("World Flammable BlockPos Replacement (Vanilla)", "mixins.rlmixins.core.worldflammableblockpos.json");
		map.put("Particle Collision Defaults (Vanilla)", "mixins.rlmixins.core.particlecollision.json");
		map.put("Untipped Arrow Particles Fix (Vanilla)", "mixins.rlmixins.core.untippedarrowparticle.json");
		map.put("Mineshaft Biome Blacklist Patch (Vanilla)", "mixins.rlmixins.core.mineshaftblacklistpatch.json");
		map.put("Fix Player Model Death Shake (Vanilla)", "mixins.rlmixins.core.deathshake.json");
		map.put("Bloodmoon Loot Pickup Fix (Vanilla/Bloodmoon)", "mixins.rlmixins.core.bloodmoonlootpickup.json");
		map.put("Allow Hoe Repairing (Vanilla)", "mixins.rlmixins.core.hoerepair.json");
		map.put("Weather Timing Config (Vanilla)", "mixins.rlmixins.core.worldweathertiming.json");
		map.put("Furnace XP Limit Fix (Vanilla)", "mixins.rlmixins.core.furnacexp.json");

		map.put("Forge Suppress Dangerous Prefix Errors (Forge)", "mixins.rlmixins.core.dangerousprefix.json");
		map.put("Forge Suppress Broken Ore Dictionary Errors (Forge)", "mixins.rlmixins.core.brokenoredict.json");

		map.put("BetterSurvival TickDynamic Crash (BetterSurvival)", "mixins.rlmixins.core.arrowaccessor.json");
		map.put("MoBends Arrow Trail Patch (MoBends)", "mixins.rlmixins.core.arrowaccessor.json");
		map.put("Quark Rune Optifine Fix (Quark)", "mixins.rlmixins.core.quarkruneoptifine.json");
		map.put("Rework Waystone Used Name Check (Waystones)", "mixins.rlmixins.core.biomeaccessor.json");

		return Collections.unmodifiableMap(map);
	}

	private static final Map<String, String> lateMap = setupLateMap();

	private static Map<String, String> setupLateMap() {
		Map<String, String> map = new HashMap<>();
		map.put("Overhaul SME (Vanilla/SME/RLCombat)", "mixins.rlmixins.smeoverhaul.json");
		map.put("Purified Rain Water (SimpleDifficulty)", "mixins.rlmixins.sdrain.json");
		map.put("Coffee Cures Hangover (Rustic/Charm)", "mixins.rlmixins.coffeehangover.json");
		map.put("Config Alcohol Effects (Rustic)", "mixins.rlmixins.alcoholconfig.json");
		map.put("Player Tracking Patch (Reskillable)", "mixins.rlmixins.reskillabletracking.json");
		map.put("SeedFood Bypass Lock (Reskillable)", "mixins.rlmixins.seedfoodbypass.json");
		map.put("Wyrm Osmosis (Reskillable/DefiledLands)", "mixins.rlmixins.wyrmosmosis.json");
		map.put("HungryFarmer Blacklist (Reskillable)", "mixins.rlmixins.hungryfarmer.json");
		map.put("Undershirt Rework (Reskillable/FirstAid)", "mixins.rlmixins.undershirt.json");
		map.put("Stoneling Dupe Patch (Quark)", "mixins.rlmixins.stoneling.json");
		map.put("Delayed Launch (PotionCore)", "mixins.rlmixins.delayedlaunch.json");
		map.put("Half Reach (PotionCore)", "mixins.rlmixins.halfreach.json");
		map.put("Lycanite Render Box (LycanitesMobs)", "mixins.rlmixins.lycaniterender.json");
		map.put("Lycanite Targetting (LycanitesMobs/IceAndFire)", "mixins.rlmixins.lycanitetargetting.json");
		map.put("Item Reach Attribute (ItemPhysics)", "mixins.rlmixins.itemreach.json");
		map.put("Cauldron Failure Mundane (Inspirations)", "mixins.rlmixins.cauldronfailure.json");
		map.put("No Infernal Champions (Champions/InfernalMobs)", "mixins.rlmixins.infernalchampions.json");
		map.put("Infernal Particle Spam (InfernalMobs)", "mixins.rlmixins.infernalparticle.json");
		map.put("Chat Bubble Config (DSurroundings)", "mixins.rlmixins.chatbubble.json");
		map.put("Infested Summon Tag (Champions/TrinketsAndBaubles)", "mixins.rlmixins.infestedsummon.json");
		map.put("Jailer Champion Time (Champions)", "mixins.rlmixins.jailer.json");
		map.put("Flare Gun Rework (BountifulBaubles)", "mixins.rlmixins.flaregun.json");
		map.put("Broken Heart Rework (BountifulBaubles/FirstAid)", "mixins.rlmixins.brokenheart.json");
		map.put("Trumpet Gluttony (BountifulBaubles/TrumpetSkeleton)", "mixins.rlmixins.trumpetglutton.json");
		map.put("Obsidian Skull/Shield Rework (BountifulBaubles)", "mixins.rlmixins.obsidianskull.json");
		map.put("Reforging Binding Fix (BountifulBaubles)", "mixins.rlmixins.reforgebinding.json");
		map.put("Replace Parasite Armor Models (SRParasites)", "mixins.rlmixins.parasitearmor.json");
		map.put("Enable AntiDragon Cheese (IceAndFire)", "mixins.rlmixins.infdragoncheese.json");
		map.put("Cancel Parasite Reach Packet (SRParasites)", "mixins.rlmixins.srppacket.json");
		map.put("Champion Death Message Tweak (Champions)", "mixins.rlmixins.championname.json");
		map.put("Champion Potion Invis (Champions)", "mixins.rlmixins.championpotion.json");
		map.put("Prevent Revival Potion on Non-Players (PotionCore)", "mixins.rlmixins.potionrevival.json");
		map.put("Parasite Spawn Fix (SRParasites)", "mixins.rlmixins.srpspawning.json");
		map.put("Parasite Light Level (SRParasites)", "mixins.rlmixins.srplightlevel.json");
		map.put("Stalagnate Bowl Fix (BetterNether)", "mixins.rlmixins.stalagnatebowl.json");
		map.put("Cobalt Shield Increased Resistance (BountifulBaubles)", "mixins.rlmixins.cobaltshield.json");
		map.put("Stoned Chicken Feather Fix (Quark/IceAndFire)", "mixins.rlmixins.chickenfeather.json");
		map.put("Education Tweak (BetterSurvival)", "mixins.rlmixins.educationtweak.json");
		map.put("Remove Shield Protection Enchant (Inspirations)", "mixins.rlmixins.inspirationsshield.json");
		map.put("ScalingHealth Death Desync (ScalingHealth)", "mixins.rlmixins.scalinghealthdesync.json");
		map.put("Sync Sign Edit Config (Quark)", "mixins.rlmixins.quarksignedit.json");
		map.put("Rehandle Sentient Scythe Effect (SRParasites/RLCombat)", "mixins.rlmixins.sentientscythe.json");
		map.put("InF Bow Multishot patch (IceAndFire/BetterSurvival)", "mixins.rlmixins.dragonbow.json");
		map.put("Changeable Nunchaku Combo (BetterSurvival)", "mixins.rlmixins.nunchakucombo.json");
		map.put("Quark Chest Boat Dupe (Quark)", "mixins.rlmixins.chestboatdupe.json");
		map.put("Vampirism Cheese Patch (BetterSurvival)", "mixins.rlmixins.bsvampirism.json");
		map.put("Locks Keyring GUI Dupe Patch (Locks)", "mixins.rlmixins.locksguidupe.json");
		map.put("ToolBelt Belt GUI Dupe Patch (ToolBelt)", "mixins.rlmixins.toolbeltguidupe.json");
		map.put("Rain Collector Canteen Fix (SimpleDifficulty)", "mixins.rlmixins.raincollector.json");
		map.put("Clay Tool Enchant Patch (NoTreePunching)", "mixins.rlmixins.claytoolenchanting.json");
		map.put("Mattock Breaking Patch (NoTreePunching)", "mixins.rlmixins.mattockbreaking.json");
		map.put("Switchbow Quiver Patch (Switchbow)", "mixins.rlmixins.switchbowquiver.json");
		map.put("Vein Pickaxe Patch (ForgottenItems)", "mixins.rlmixins.veinpickaxe.json");
		map.put("Reskillable Indirect Self Damage Patch (Reskillable)", "mixins.rlmixins.reskillableindirect.json");
		map.put("Scarlite Sword Config Effect (DefiledLands)", "mixins.rlmixins.scarlitesword.json");
		map.put("Disenchanting Table Crash Patch (Disenchanter)", "mixins.rlmixins.disenchantcrash.json");
		map.put("Quark Chat Link NBT Crash (Quark)", "mixins.rlmixins.chatlinkcrash.json");
		map.put("Potion Amplifier Visibility (DSHuds)", "mixins.rlmixins.dshudpotion.json");
		map.put("Parasite Cleaver Effect Config (SRParasites)", "mixins.rlmixins.cleaverpotion.json");
		map.put("Parasite Indirect Damage Adaption Fix (SRParasites)", "mixins.rlmixins.indirectadaption.json");
		map.put("Better Foliage Chunk XRay (BetterFoliage/ChunkAnimator)", "mixins.rlmixins.betterfoliagechunkanimator.json");
		map.put("CarryOn Ungenerated Chest Patch (CarryOn)", "mixins.rlmixins.carryonunlooted.json");
		map.put("CarryOn Pig Saddle Patch (CarryOn)", "mixins.rlmixins.carryonpig.json");
		map.put("CorpseComplex XP Death Fix (CorpseComplex)", "mixins.rlmixins.corpsecomplexreturn.json");
		map.put("Magnetic Dupe Patch (Charm)", "mixins.rlmixins.magneticdupe.json");
		map.put("Penetration Fix (BetterSurvival/SpartanWeaponry/RLCombat)", "mixins.rlmixins.bspenetration.json");
		map.put("Spriggan Growth Rate Override (LycanitesMobs)", "mixins.rlmixins.sprigganfarmrate.json");
		map.put("BetterSurvival TickDynamic Crash (BetterSurvival)", "mixins.rlmixins.bstickdynamic.json");
		map.put("SoManyEnchantments TickDynamic Crash (SoManyEnchantments)", "mixins.rlmixins.smetickdynamic.json");
		map.put("QualityTools Limit Modifiers (QualtiyTools)", "mixins.rlmixins.qtlimitmodifier.json");
		map.put("LycanitesMobs Lowercase Performance Patch (LycanitesMobs)", "mixins.rlmixins.lycanitelowercase.json");
		map.put("BetterSurvival LivingUpdateHandler Optimization (BetterSurvival)", "mixins.rlmixins.bslivingupdate.json");
		map.put("XaT Magic Handler Players Only (Trinkets and Baubles)", "mixins.rlmixins.xatmagicperf.json");
		map.put("Supress DT Branch Errors (DynamicTrees)", "mixins.rlmixins.dtfallingerror.json");
		map.put("DT Leaves Ignore Light on WorldGen (DynamicTrees)", "mixins.rlmixins.dtleavesworldgen.json");
		map.put("EnderWatcher Performance Patch (Quark)", "mixins.rlmixins.enderwatcherperf.json");
		map.put("BetterNether Memory Leak Fix (BetterNether)", "mixins.rlmixins.betternethermemleak.json");
		map.put("BetterQuesting Memory Leak Fix (BetterQuesting Standard Expansion)", "mixins.rlmixins.bqmemleak.json");
		map.put("BetterQuesting Unofficial Memory Leak Fix (BetterQuesting Unofficial)", "mixins.rlmixins.bqumemleak.json");
		map.put("MoBends Memory Leak Fix (MoBends)", "mixins.rlmixins.mobendsmemleak.json");
		map.put("JEI Suppress Varied Commodities Errors (JEI)", "mixins.rlmixins.jeivcerror.json");
		map.put("Elenai Dodge Better Tick Performance (Elenai Dodge)", "mixins.rlmixins.elenaitick.json");
		map.put("Quark Springy Slime Force Disable (Quark)", "mixins.rlmixins.quarkslime.json");
		map.put("SereneSeasons Reflection Caching Patch (SereneSeasons)", "mixins.rlmixins.serenereflection.json");
		map.put("Quark Emotes Force Disable (Quark)", "mixins.rlmixins.quarkemote.json");
		map.put("XaT Cache Config Attributes (Trinkets and Baubles)", "mixins.rlmixins.trinketsmemusage.json");
		map.put("Quark Force Disable Potion Colorizer (Quark)", "mixins.rlmixins.quarkcolorizer.json");
		map.put("Quark Reduced Villager Double Door AI Checks (Quark)", "mixins.rlmixins.doubledoors.json");
		map.put("Lost Cities No Respawn (Lost Cities)", "mixins.rlmixins.lostcityrespawn.json");
		map.put("Disable MoBends Online Checks (MoBends)", "mixins.rlmixins.nukemobends.json");
		map.put("Boss AstikorCart Cheese (Vanilla/AstikorCarts/InfernalMobs/ScalingHealth/Champions)", "mixins.rlmixins.astikorcheese.json");
		map.put("Stoneling Eyeheight Stall Patch (Quark)", "mixins.rlmixins.stonelingloop.json");
		map.put("Dummy Damage Value Patch (MmmMmmMmmMmm)", "mixins.rlmixins.dummypatch.json");
		map.put("EV Death Message Translation (EnhancedVisuals)", "mixins.rlmixins.evdeathmessage.json");
		map.put("Neat Shaders Patch (Neat)", "mixins.rlmixins.neatshaders.json");
		map.put("DSurround Chat Bubble Shaders Patch (DSurround)", "mixins.rlmixins.chatbubbleoptifine.json");
		map.put("JEI Revert Bookmark Location Changes (JEI)", "mixins.rlmixins.jeibookmark.json");
		map.put("JEI Ignore Anvil Recipes (JEI)", "mixins.rlmixins.jeianvil.json");
		map.put("Battletower Golem Attack Target Patch (Battletowers)", "mixins.rlmixins.battletowertarget.json");
		map.put("BQU Remove Hardcoded Backspace (BetterQuesting Unofficial)", "mixins.rlmixins.bqubackspace.json");
		map.put("DynamicTrees AABB Cache (DynamicTrees)", "mixins.rlmixins.dynamictreescache.json");
		map.put("BetterFoliage Whitelist/Blacklist Cache (BetterFoliage)", "mixins.rlmixins.betterfoliagecache.json");
		map.put("BetterFoliage Geometry Offset (BetterFoliage)", "mixins.rlmixins.betterfoliagegeometry.json");
		map.put("ForgottenItems Rune Fix (ForgottenItems)", "mixins.rlmixins.forgottenitemsrune.json");
		map.put("Rusting Curse Negative Durability Patch (Charm)", "mixins.rlmixins.rustingnegative.json");
		map.put("Quark Enchanted Book Tooltip Rendering Patch (Quark)", "mixins.rlmixins.quarkenchantedtooltip.json");
		map.put("MoBends Arrow Trail Patch (MoBends)", "mixins.rlmixins.mobendsarrowtrail.json");
		map.put("ScalingHealth Bandaged Icon Fix (ScalingHealth)", "mixins.rlmixins.bandagedicon.json");
		map.put("Horse Meat From Llamas Fix (FoodExpansion)", "mixins.rlmixins.llamahorsemeat.json");
		map.put("Remove SME Mortalitas Resurrection Capability (SoManyEnchantments)", "mixins.rlmixins.smecapremoval.json");
		map.put("Fix LycanitesMobs Charges in Item Frames (LycanitesMobs)", "mixins.rlmixins.lycanitechargeframe.json");
		map.put("JSONPaintings Placement Crash Fix (JSONPaintings)", "mixins.rlmixins.jsonpaintingcrash.json");
		map.put("DramaticTrees Falling Overhaul (DramaticTrees)", "mixins.rlmixins.dramatictreefalling.json");
		map.put("DSHuds Barometer Patch (DSHuds/Inspirations)", "mixins.rlmixins.dshudbarometer.json");
		map.put("BountifulBaubles isShield Fix (BountifulBaubles)", "mixins.rlmixins.bountifulbaublesshieldfix.json");
		map.put("Rework Waystone Used Name Check (Waystones)", "mixins.rlmixins.waystonename.json");
		map.put("Force Cart Unpull Over Distance (AstikorCarts)", "mixins.rlmixins.cartdistancelimit.json");
		map.put("ForgottenItems Fix Binding NBT (ForgottenItems)", "mixins.rlmixins.forgottenitemsbound.json");
		map.put("BetterNether Double Slab Drop Fix (BetterNether)", "mixins.rlmixins.betternetherdoubleslab.json");
		map.put("BetterNether Door Dupe Fix (BetterNether)", "mixins.rlmixins.betternetherdoor.json");
		map.put("Skeleton King Loot Drop Fix (FishsUndeadRising)", "mixins.rlmixins.fishsskeletonking.json");
		map.put("Allow Changing Lucky Horseshoe Weight (BountifulBaubles)", "mixins.rlmixins.bountifulbaublesluckyhorseshoe.json");
		map.put("CarryOn Passenger Rider Death Fix (CarryOn)", "mixins.rlmixins.carryonpositionfix.json");
		map.put("ItemPhysics Q CarryOn Fix (CarryOn/ItemPhysics)", "mixins.rlmixins.itemphysiccarryonq.json");
		map.put("Inspirations Bookshelf Color Crash Fix (Inspirations)", "mixins.rlmixins.inspirationscolorcheck.json");
		map.put("Aquatic Mob Underwater Spawning (FishsUndeadRising)", "mixins.rlmixins.lavacowaquaspawn.json");
		map.put("SRP Bush Generation Loaded Checks (SRParasites)", "mixins.rlmixins.srpbushcascading.json");

		map.put("Flowering Oak DT Fix (DynamicTrees/BOP/DTBOP)", "mixins.rlmixins.floweringoakleaves.json");
		map.put("OTG Create World Simplify Fix (OTG)", "mixins.rlmixins.otgguibutton.json");
		map.put("DregoraRL First Time Setup Progress (DregoraRL)", "mixins.rlmixins.dregorarlprogress.json");

		map.put("ChunkAnimator XRay (Vanilla/ChunkAnimator)", "mixins.rlmixins.chunkanimator.json");

		return Collections.unmodifiableMap(map);
	}

	public RLMixinsPlugin() {
		MixinBootstrap.init();

		RLMixins.LOGGER.log(Level.INFO, "RLMixins Early Enqueue Start");
		for(Map.Entry<String, String> entry : earlyMap.entrySet()) {
			RLMixins.LOGGER.log(Level.INFO, "RLMixins Early Enqueue: " + entry.getKey());
			FermiumRegistryAPI.enqueueMixin(false, entry.getValue(), () -> ForgeConfigHandler.getBoolean(entry.getKey()));
		}

		RLMixins.LOGGER.log(Level.INFO, "RLMixins Late Enqueue Start");
		for(Map.Entry<String, String> entry : lateMap.entrySet()) {
			RLMixins.LOGGER.log(Level.INFO, "RLMixins Late Enqueue: " + entry.getKey());
			FermiumRegistryAPI.enqueueMixin(true, entry.getValue(), () -> ForgeConfigHandler.getBoolean(entry.getKey()));
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
	public void injectData(Map<String, Object> data) { }
	
	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}
}