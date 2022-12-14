package rlmixins.handlers;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;
import rlmixins.RLMixins;

import java.io.File;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Config(modid = RLMixins.MODID)
public class ForgeConfigHandler {
	
	@Config.Comment("Additional Server-Side Options")
	@Config.Name("Server Options")
	public static final ServerConfig server = new ServerConfig();

	@Config.Comment("Additional Client-Side Options")
	@Config.Name("Client Options")
	public static final ClientConfig client = new ClientConfig();

	@Config.Comment("Enable/Disable Tweaks and Patches")
	@Config.Name("Toggle Mixins")
	public static final MixinConfig mixinConfig = new MixinConfig();

	public static class MixinConfig {

		@Config.Comment("MC-119971 patch, created by EigenCraft Unofficial Patch")
		@Config.Name("Outdated Chunk Data (Vanilla)")
		@Config.RequiresMcRestart
		public boolean outdatedChunkData = false;

		@Config.Comment("Force Thorn and Arthropod enchantment methods to check for offhand packets")
		@Config.Name("Offhand Enchants (Vanilla/RLCombat)")
		@Config.RequiresMcRestart
		public boolean arthropodOffhandSensitivity = false;

		@Config.Comment("Disallow Infernal/Blight/Champion mobs from entering Minecarts and Boats (Does not require all mods to be loaded.)")
		@Config.Name("Boss Cart/Boat Cheese (Vanilla/InfernalMobs/ScalingHealth/Champions)")
		@Config.RequiresMcRestart
		public boolean bossCartCheesePatch = false;

		@Config.Comment("Force EntityLivingBase#attemptTeleport to cancel under the effects of AntiWarp")
		@Config.Name("AntiWarp Handling (Vanilla/BetterSurvival)")
		@Config.RequiresMcRestart
		public boolean antiwarpImprovement = false;

		@Config.Comment("Makes fall distance reduction per tick in water configurable")
		@Config.Name("Configurable Fall (Vanilla)")
		@Config.RequiresMcRestart
		public boolean configurableFallDistance = false;

		@Config.Comment("Lower the player's eye height while crouching to be more like newer versions")
		@Config.Name("Lowered Crouch (Vanilla)")
		@Config.RequiresMcRestart
		public boolean lowerEyeHeight = false;

		@Config.Comment("Patches issues with player health attributes being lowered between packets causing desynced death")
		@Config.Name("Health Desync Patch (Vanilla)")
		@Config.RequiresMcRestart
		public boolean healthAttributePatch = false;

		@Config.Comment("Smooth eye height when crouching, created by RandomPatches")
		@Config.Name("Smoothed Crouching (Vanilla)")
		@Config.RequiresMcRestart
		public boolean smoothCrouch = false;

		@Config.Comment("Force Mending to prioritize damaged items first, instead of randomly picking")
		@Config.Name("Mending Priorities (Vanilla)")
		@Config.RequiresMcRestart
		public boolean mendingPriorities = false;

		@Config.Comment("Makes Chunk Animator stop animating around the player temporarily when using F3+A or changing render distance, to stop easy xray")
		@Config.Name("ChunkAnimator XRay (Vanilla/ChunkAnimator)")
		@Config.RequiresMcRestart
		public boolean chunkAnimatorXRay = false;

		@Config.Comment("Stops Anvils from displaying \"Too Expensive\" for compatibility with AnvilPatchLawful")
		@Config.Name("Anvil Too Expensive (Vanilla/AnvilPatch)")
		@Config.RequiresMcRestart
		public boolean anvilTooExpensiveFix = false;

		@Config.Comment("Directly modify Item attributes for certain SoManyEnchantments Enchantments")
		@Config.Name("Enchantment Item Attributes (Vanilla/SME)")
		@Config.RequiresMcRestart
		public boolean enchantmentsModifyItemAttributes = false;

		@Config.Comment("Directly modify ItemStack damage for certain SoManyEnchantments Enchantments")
		@Config.Name("Enchantment ItemStack Damage (Vanilla/SME)")
		@Config.RequiresMcRestart
		public boolean enchantmentsModifyItemStackDamage = false;

		@Config.Comment("MC-92916 patch, created by EigenCraft Unofficial Patch")
		@Config.Name("Entity Tracker Desync (Vanilla)")
		@Config.RequiresMcRestart
		public boolean entityTrackerDesyncPatch = false;

		@Config.Comment("Fixes certain particles sent to the client from serverside never actually rendering, created by RandomPatches")
		@Config.Name("Missing Particle Rendering (Vanilla)")
		@Config.RequiresMcRestart
		public boolean particleRenderPatch = false;

		@Config.Comment("MC-108469 patch, created by EigenCraft Unofficial Patch")
		@Config.Name("Chunk Entity List (Vanilla)")
		@Config.RequiresMcRestart
		public boolean chunkEntityListUpdate = false;

		@Config.Comment("Overhaul a bunch of SoManyEnchantments Enchantment handlers to fix lag/bugs/offhand issues")
		@Config.Name("Overhaul SME (Vanilla/SME/RLCombat)")
		@Config.RequiresMcRestart
		public boolean overhaulEnchants = false;

		@Config.Comment("Force gathering rain into a canteen to give purified water instead of dirty water")
		@Config.Name("Purified Rain Water (SimpleDifficulty)")
		@Config.RequiresMcRestart
		public boolean purifyRainWater = false;

		@Config.Comment("Makes Coffee from Charm reduce the effects of Inebriation from Rustic instead of water")
		@Config.Name("Coffee Cures Hangover (Rustic/Charm)")
		@Config.RequiresMcRestart
		public boolean coffeeInebriation = false;

		@Config.Comment("Replace the effects of Ale, Cider, and Mead with config-defined effects")
		@Config.Name("Config Alcohol Effects (Rustic)")
		@Config.RequiresMcRestart
		public boolean alcoholConfig = false;

		@Config.Comment("Fixes Reskillable losing track of the player when returning from the end, causing baubles with level requirements to be lost")
		@Config.Name("Player Tracking Patch (Reskillable)")
		@Config.RequiresMcRestart
		public boolean playerTrackingPatch = false;

		@Config.Comment("Allows SeedFood to bypass being locked by Reskillable (Allows eating Potatos/Carrots but not planting them)")
		@Config.Name("SeedFood Bypass Lock (Reskillable)")
		@Config.RequiresMcRestart
		public boolean seedFoodBypass = false;

		@Config.Comment("Makes Golden Osmosis perk also repair Golden Book Wyrm armor")
		@Config.Name("Wyrm Osmosis (Reskillable/DefiledLands)")
		@Config.RequiresMcRestart
		public boolean goldenBookWyrmOsmosis = false;

		@Config.Comment("Adds a config defined blacklist for the Hungry Farmer perk")
		@Config.Name("HungryFarmer Blacklist (Reskillable)")
		@Config.RequiresMcRestart
		public boolean hungryFarmerBlacklistAbility = false;

		@Config.Comment("Reworks Undershirt perk to work properly with FirstAid")
		@Config.Name("Undershirt Rework (Reskillable/FirstAid)")
		@Config.RequiresMcRestart
		public boolean undershirtRework = false;

		@Config.Comment("Patches Dupe bug with Stonelings")
		@Config.Name("Stoneling Dupe Patch (Quark)")
		@Config.RequiresMcRestart
		public boolean stonelingPatch = false;

		@Config.Comment("Replaces Launch potion's effect from PotionCore with Delayed Launch, for compatibility with knockback effects")
		@Config.Name("Delayed Launch (PotionCore)")
		@Config.RequiresMcRestart
		public boolean delayedLaunch = false;

		@Config.Comment("Halves the effect of Reach potion")
		@Config.Name("Half Reach (PotionCore)")
		@Config.RequiresMcRestart
		public boolean halfReach = false;

		@Config.Comment("Modify the render bounding boxes of some Lycanite mobs to fix under/oversized render boxes")
		@Config.Name("Lycanite Render Box (LycanitesMobs)")
		@Config.RequiresMcRestart
		public boolean lycaniteRenderBoxResize = false;

		@Config.Comment("Stops Lycanite mobs from attempting to target mobs that are stone statues, or tagged with NoAI")
		@Config.Name("Lycanite Targetting (LycanitesMobs/IceAndFire)")
		@Config.RequiresMcRestart
		public boolean lycaniteTargettingPatch = false;

		@Config.Comment("Makes ItemPhysics use the player's reach attribute instead of a hardcoded value")
		@Config.Name("Item Reach Attribute (ItemPhysics)")
		@Config.RequiresMcRestart
		public boolean itemReachAttribute = false;

		@Config.Comment("Makes incorrectly mixing potions in an Inspirations cauldron turn into Mundane instead of Thick potion")
		@Config.Name("Cauldron Failure Mundane (Inspirations)")
		@Config.RequiresMcRestart
		public boolean inspirationsMundaneCauldron = false;

		@Config.Comment("Prevents Champion mobs from turning into Infernals as well")
		@Config.Name("No Infernal Champions (Champions/InfernalMobs)")
		@Config.RequiresMcRestart
		public boolean preventChampionInfernals = false;

		@Config.Comment("Stops Infernal Mobs from spamming particles while the game is paused")
		@Config.Name("Infernal Particle Spam (InfernalMobs)")
		@Config.RequiresMcRestart
		public boolean infernalParticleSpam = false;

		@Config.Comment("Modify the render bounding boxes of some Ice and Fire mobs to fix undersized render boxes")
		@Config.Name("IceAndFire Render Box (IceAndFire)")
		@Config.RequiresMcRestart
		public boolean iceAndFireRenderBoxResize = false;

		@Config.Comment("Cancels Ice and Fire's multipart mob handling to allow RLCombat to handle it instead")
		@Config.Name("InF Multipart Handling (IceAndFire)")
		@Config.RequiresMcRestart
		public boolean cancelIceAndFireMultipartHandling = false;

		@Config.Comment("Allows for reducing the amount of particles generated by dragon explosions (These normally aren't rendered without Missing Particle Rendering Patch)")
		@Config.Name("Explosion Particle Reduction (IceAndFire)")
		@Config.RequiresMcRestart
		public boolean dragonParticleReduction = false;

		@Config.Comment("Cancels Ice and Fire's handling of weapon bonuses, allowing for RLCombat to properly handle it instead")
		@Config.Name("InF Bonus Handling (IceAndFire)")
		@Config.RequiresMcRestart
		public boolean cancelIceAndFireBonusHandling = false;

		@Config.Comment("Fix issues with Dragon Skull, Dragon Egg, Dragon Horn, and Myrmex Egg deleting items or duping when used in offhand")
		@Config.Name("InF Offhand Items (IceAndFire)")
		@Config.RequiresMcRestart
		public boolean patchIceAndFireOffhand = false;

		@Config.Comment("Fix Food Expansion foods deleting the entire stack when eaten if their stack size is increased")
		@Config.Name("Food Stack Size (FoodExpansion)")
		@Config.RequiresMcRestart
		public boolean foodExpansionStackSizeFix = false;

		@Config.Comment("Fix Food Expansion's Nether Wart Soup crashing the game when eaten on a server")
		@Config.Name("Nether Wart Soup Crash (FoodExpansion)")
		@Config.RequiresMcRestart
		public boolean netherWartSoupCrashFix = false;

		@Config.Comment("Adds the ability to define Dynamic Surroundings entity chat messages in a config file")
		@Config.Name("Chat Bubble Config (DSurroundings)")
		@Config.RequiresMcRestart
		public boolean entityChatBubbleConfig = false;

		@Config.Comment("Tags mobs spawned from Infested Champions as summoned, allowing for Trinkets and Baubles to cancel their xp/item drops")
		@Config.Name("Infested Summon Tag (Champions/TrinketsAndBaubles)")
		@Config.RequiresMcRestart
		public boolean infestedSummonTags = false;

		@Config.Comment("Increases the time that Jailer Champions apply the Jailed effect for, since the original mixes up seconds and ticks")
		@Config.Name("Jailer Champion Time (Champions)")
		@Config.RequiresMcRestart
		public boolean jailerTimeFix = false;

		@Config.Comment("Replace and rework the flare gun entity and handling")
		@Config.Name("Flare Gun Rework (BountifulBaubles)")
		@Config.RequiresMcRestart
		public boolean flareGunRework = false;

		@Config.Comment("Rework the Broken Heart trinket to work with FirstAid")
		@Config.Name("Broken Heart Rework (BountifulBaubles/FirstAid)")
		@Config.RequiresMcRestart
		public boolean brokenHeartRework = false;

		@Config.Comment("Prevents trumpets from triggering the Gluttony amulet effect")
		@Config.Name("Trumpet Gluttony (BountifulBaubles/TrumpetSkeleton)")
		@Config.RequiresMcRestart
		public boolean trumpetGluttonFix = false;

		@Config.Comment("Rework Obsidian Skull/Shield fire resistance handling to be less buggy")
		@Config.Name("Obsidian Skull/Shield Rework (BountifulBaubles)")
		@Config.RequiresMcRestart
		public boolean obsidianResistanceRework = false;

		@Config.Comment("Prevents the player from removing armor cursed with Binding in the reforging station")
		@Config.Name("Reforging Binding Fix (BountifulBaubles)")
		@Config.RequiresMcRestart
		public boolean reforgingBindingFix = false;
	}
	public static class ServerConfig {
		@Config.Comment("Item Blacklist for the Hungry Farmer trait.")
		@Config.Name("Hungry Farmer Blacklist")
		public String[] hungryFarmerBlacklist = {""};

		@Config.Comment("Percentage of Flame particles to ignore for Fire Dragon explosions")
		@Config.Name("Fire Dragon Explosion Flame Percent")
		@Config.RangeDouble(min = 0.0, max = 1.0)
		public double fireDragonExplosionFlame = 0.90;

		@Config.Comment("Percentage of Snow particles to ignore for Ice Dragon explosions")
		@Config.Name("Ice Dragon Explosion Snow Percent")
		@Config.RangeDouble(min = 0.0, max = 1.0)
		public double iceDragonExplosionSnow = 0.80;

		@Config.Comment("Percentage of Smoke particles to ignore for Fire Dragon explosions")
		@Config.Name("Fire Dragon Explosion Smoke Percent")
		@Config.RangeDouble(min = 0.0, max = 1.0)
		public double fireDragonExplosionSmoke = 0.95;

		@Config.Comment("Percentage of Smoke particles to ignore for Ice Dragon explosions")
		@Config.Name("Ice Dragon Explosion Smoke Percent")
		@Config.RangeDouble(min = 0.0, max = 1.0)
		public double iceDragonExplosionSmoke = 0.95;

		@Config.Comment("How many blocks to reduce fall distance by per tick in water")
		@Config.Name("Fall Distance Reduction in Water")
		@Config.RangeDouble(min = 1.0D, max = 100.0D)
		public double fallDistanceReduction = 4.0D;

		@Config.Comment("Effect that drinking Ale should give")
		@Config.Name("Ale Effect")
		public String aleEffect = "lycanitesmobs:immunization";

		@Config.Comment("Effect that drinking Cider should give")
		@Config.Name("Cider Effect")
		public String ciderEffect = "potioncore:magic_shield";

		@Config.Comment("Effect that drinking Mead should give")
		@Config.Name("Mead Effect")
		public String meadEffect = "lycanitesmobs:rejuvenation";

		@Config.Comment("Register the Encumbered potion effect (Requires PotionCore)")
		@Config.Name("Register Encumbered")
		@Config.RequiresMcRestart
		public boolean registerEncumbered = false;
	}

	public static class ClientConfig {

	}
	
	@Mod.EventBusSubscriber(modid = RLMixins.MODID)
	private static class EventHandler{
		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(RLMixins.MODID)) ConfigManager.sync(RLMixins.MODID, Config.Type.INSTANCE);
		}
	}

	//This is jank, but easier than setting up a whole custom GUI config
	private static File configFile;
	private static String configBooleanString = "";

	public static boolean getBoolean(String name) {
		if(configFile==null) {
			configFile = new File("config", RLMixins.MODID + ".cfg");
			if(configFile.exists() && configFile.isFile()) {
				try (Stream<String> stream = Files.lines(configFile.toPath())) {
					configBooleanString = stream.filter(s -> s.trim().startsWith("B:")).collect(Collectors.joining());
				}
				catch(Exception ex) {
					RLMixins.LOGGER.log(Level.ERROR, "Failed to parse RLMixins config: " + ex);
				}
			}
		}
		//If config is not generated or missing entries, don't enable injection on first run
		return configBooleanString.contains("B:\"" + name + "\"=true");
	}
}