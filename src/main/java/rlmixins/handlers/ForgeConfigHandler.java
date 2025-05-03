package rlmixins.handlers;

import com.google.common.collect.BiMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.Level;
import rlmixins.RLMixins;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Config(modid = RLMixins.MODID)
public class ForgeConfigHandler {

	private static List<String> netherBaneMobs = null;
	private static List<String> netherBaneWeapons = null;
	private static List<String> particleCollisionClasses = null;
	private static HashSet<String> mineshaftBiomeNames = null;
	private static HashSet<BiomeDictionary.Type> mineshaftBiomeTypes = null;
	private static HashSet<Block> dramaticTreeNonSolidList = null;
	private static HashSet<Block> dramaticTreeNonSolidBreakableList = null;
	private static HashSet<Block> dramaticTreeSolidBreakableList = null;
	private static HashSet<Block> reskillablePerfectRecoverList = null;
	private static HashSet<ResourceLocation> dregoraArrowAllowedEntities = null;
	private static List<PotionType> dregoraArrowAllowedPotionTypes = null;
	private static HashSet<ResourceLocation> silverImmunityBlacklistedPotionEffects = null;
	private static Map<Integer, IBlockState> dimensionBlockFillerMap = null;
	private static HashSet<Block> caveRavineCarverSet = null;
	private static Map<String, Double> orbitalPeriodOverrides = null;
	private static Map<String, Double> orbitalPeriodMults = null;
	
	@Config.Comment("Additional Server-Side Options")
	@Config.Name("Server Options")
	public static final ServerConfig server = new ServerConfig();

	@Config.Comment("Additional Client-Side Options")
	@Config.Name("Client Options")
	public static final ClientConfig client = new ClientConfig();

	@Config.Comment("Enable/Disable Tweaks and Patches")
	@Config.Name("Toggle Mixins")
	public static final MixinConfig mixinConfig = new MixinConfig();

	@SuppressWarnings("unused")
	public static class MixinConfig {

		@Config.Comment("MC-119971 patch, created by EigenCraft Unofficial Patch")
		@Config.Name("Outdated Chunk Data (Vanilla)")
		@Config.RequiresMcRestart
		public boolean outdatedChunkData = false;

		@Config.Comment("Disallow Infernal/Blight/Champion mobs from entering Minecarts and Boats (Does not require all mods to be loaded.)")
		@Config.Name("Boss Cart/Boat Cheese (Vanilla/InfernalMobs/ScalingHealth/Champions)")
		@Config.RequiresMcRestart
		public boolean bossCartCheesePatch = false;

		@Config.Comment("Disallow Infernal/Blight/Champion mobs from entering Astikor Carts (Does not require all mods to be loaded except Astikor.)")
		@Config.Name("Boss AstikorCart Cheese (Vanilla/AstikorCarts/InfernalMobs/ScalingHealth/Champions)")
		@Config.RequiresMcRestart
		public boolean astikorCartCheesePatch = false;

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

		@Config.Comment("Directly replaces getCanSpawn for Giant Zombies - allowing them to spawn")
		@Config.Name("Giant Zombie Spawn Fix (Vanilla)")
		@Config.RequiresMcRestart
		public boolean giantZombieSpawnFix = false;

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

		@Config.Comment("Adds a config defined blacklist for the Hungry Farmer perk, eats first edible item in inventory instead of prioritization, and optionally fires ItemUseFinish event afterwards")
		@Config.Name("HungryFarmer Rework (Reskillable)")
		@Config.RequiresMcRestart
		public boolean hungryFarmerRework = false;

		@Config.Comment("Reworks Undershirt perk to work properly with FirstAid")
		@Config.Name("Undershirt Rework (Reskillable/FirstAid)")
		@Config.RequiresMcRestart
		public boolean undershirtRework = false;

		@Config.Comment("Reworks Road Walk perk to be active anywhere exposed to sky instead of only Grass Path Blocks")
		@Config.Name("Road Walk Rework (Reskillable)")
		@Config.RequiresMcRestart
		public boolean roadWalkRework = false;

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

		@Config.Comment("Invert potion only turns Positive -> Negative")
		@Config.Name("Invert Buffs Only (PotionCore)")
		@Config.RequiresMcRestart
		public boolean invertBuffsOnly = false;

		@Config.Comment("Modify the render bounding boxes of some Lycanite mobs to fix under/oversized render boxes")
		@Config.Name("Lycanite Render Box (LycanitesMobs)")
		@Config.RequiresMcRestart
		public boolean lycaniteRenderBoxResize = false;

		@Config.Comment("Stops Lycanite mobs from attempting to target mobs that are stone statues, or tagged with NoAI")
		@Config.Name("Lycanite Targetting (LycanitesMobs/IceAndFire)")
		@Config.RequiresMcRestart
		public boolean lycaniteTargettingPatch = false;

		@Config.Comment("Considers mining a stoned callable horse a kill")
		@Config.Name("Stoned Horse Kill (CallableHorses/IceAndFire)")
		@Config.RequiresMcRestart
		public boolean stonedCallableHorseKill = false;

		@Config.Comment("Considers SRP conversions as callable horse kills")
		@Config.Name("SRP Conversion Horse Kill (CallableHorses/SRParasites)")
		@Config.RequiresMcRestart
		public boolean srpConvertCallableHorseKill = false;

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

		@Config.Comment("Adds the ability to define Dynamic Surroundings entity chat messages in a config file")
		@Config.Name("Chat Bubble Config (DSurroundings)")
		@Config.RequiresMcRestart
		public boolean entityChatBubbleConfig = false;

		@Config.Comment("Tags mobs spawned from Infested Champions as summoned, allowing for Trinkets and Baubles to cancel their xp/item drops")
		@Config.Name("Infested Summon Tag (Champions/TrinketsAndBaubles)")
		@Config.RequiresMcRestart
		public boolean infestedSummonTags = false;

		@Config.Comment("Tags mobs without a Spawner Tile as summoned, allowing for Trinkets and Baubles to cancel their xp/item drops")
		@Config.Name("Destroyed Spawner Summon Tag (MobSpawnerControl/TrinketsAndBaubles)")
		@Config.RequiresMcRestart
		public boolean destroyedSpawnerSummonTags = false;

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

		@Config.Comment("Replaces SRParasites Living and Sentient armor models with custom models")
		@Config.Name("Replace Parasite Armor Models (SRParasites)")
		@Config.RequiresMcRestart
		public boolean replaceParasiteArmorModel = false;

		@Config.Comment("Makes SRParasites Strange Bones stack to 16")
		@Config.Name("Strange Bones stack to 16 (SRParasites)")
		@Config.RequiresMcRestart
		public boolean increaseStrangeBoneStackSize = false;

		@Config.Comment("Ignore SRParasites spawning logic for Mob Spawners when using Evolution Phase Custom Spawner")
		@Config.Name("Parasite Mob Spawner Fix (SRParasites)")
		@Config.RequiresMcRestart
		public boolean enableSRPSpawners = false;

		@Config.Comment("Attempts to stop the ability to cheese dragons on the edge of render distance")
		@Config.Name("Enable AntiDragon Cheese (IceAndFire)")
		@Config.RequiresMcRestart
		public boolean enableInFDragonCheese = false;

		@Config.Comment("Cancels SRParasites manually packet handling for reach bonuses")
		@Config.Name("Cancel Parasite Reach Packet (SRParasites)")
		@Config.RequiresMcRestart
		public boolean cancelParasiteReachPacket = false;

		@Config.Comment("Makes Champions death messages use the Champion's name")
		@Config.Name("Champion Death Message Tweak (Champions)")
		@Config.RequiresMcRestart
		public boolean championDeathMessage = false;

		@Config.Comment("Makes Champions with potions use invisible particles")
		@Config.Name("Champion Potion Invis (Champions)")
		@Config.RequiresMcRestart
		public boolean championPotionInvis = false;

		@Config.Comment("Blacklists PotionCore Revival/1UP potion from affecting non-players, to prevent duping.")
		@Config.Name("Prevent Revival Potion on Non-Players (PotionCore)")
		@Config.RequiresMcRestart
		public boolean revivalPotionPlayerOnly = false;

		@Config.Comment("Prevents Charm Crates from being inserted into Shulker Boxes, manually and by hopper.")
		@Config.Name("Prevent Shulker Crate Insertion (Vanilla/Charm)")
		@Config.RequiresMcRestart
		public boolean shulkerCrateInsertion = false;

		@Config.Comment("Cancels Parasites attempting to run a custom spawn tick check. (Seems to help performance/spawning)")
		@Config.Name("Parasite Spawn Fix (SRParasites)")
		@Config.RequiresMcRestart
		public boolean parasiteSpawnFix = false;

		@Config.Comment("Makes parasite spawning ignore all light level if the ignore sunlight option is true.")
		@Config.Name("Parasite Light Level (SRParasites)")
		@Config.RequiresMcRestart
		public boolean parasiteLightLevel = false;

		@Config.Comment("Makes nether portals not spawn zombie pigmen, to prevent farming.")
		@Config.Name("Stop Pigmen Portal Spawning (Vanilla)")
		@Config.RequiresMcRestart
		public boolean stopPigmenPortalSpawning = false;

		@Config.Comment("Prevents hive blocks from being pushed by pistons, which could be exploited to mass produce Nether Wasps.")
		@Config.Name("Prevent Nether Wasp Farming (Vanilla/BOP)")
		@Config.RequiresMcRestart
		public boolean preventNetherWaspFarming = false;

		@Config.Comment("Fixes BetterNether's food bowls from deleting whole stacks when eaten.")
		@Config.Name("Stalagnate Bowl Fix (BetterNether)")
		@Config.RequiresMcRestart
		public boolean stalagnateBowlFix = false;

		@Config.Comment("Fixes FoodExpansion's food bowls from deleting whole stacks when eaten, not returning bowls correctly and applying potions on clientside.")
		@Config.Name("Fix FoodExpansion Eating (FoodExpansion)")
		@Config.RequiresMcRestart
		public boolean foodExpansionFix = false;

		@Config.Comment("Fixes Vanilla soups not return bowls correctly when allowed to stack.")
		@Config.Name("Vanilla stackable Soups return bowls correctly (Vanilla)")
		@Config.RequiresMcRestart
		public boolean vanillaBowlsFix = false;

		@Config.Comment("Adds a blacklist to prevent certain potion effects from working on tipped arrows.")
		@Config.Name("Tipped Arrow Blacklist (Vanilla)")
		@Config.RequiresMcRestart
		public boolean tippedArrowBlacklist = false;

		@Config.Comment("Modifies the Bountiful Baubles Cobalt Shield Knockback Resistance Attribute from 10 -> 1000.")
		@Config.Name("Cobalt Shield Increased Resistance (BountifulBaubles)")
		@Config.RequiresMcRestart
		public boolean cobaltShieldIncreasedResistance = false;

		@Config.Comment("Skips checking oversized AABB for collisions when teleporting long distances, causing extreme lag.")
		@Config.Name("EXPERIMENTAL: Teleporting Lag Patch (Vanilla)")
		@Config.RequiresMcRestart
		public boolean teleportCollisionPatch = false;

		@Config.Comment("Makes feathers not passively drop from chickens if they're stoned")
		@Config.Name("Stoned Chicken Feather Fix (Quark/IceAndFire)")
		@Config.RequiresMcRestart
		public boolean chickenStonedFix = false;

		@Config.Comment("Tweaks the values of the Education enchant.")
		@Config.Name("Education Tweak (BetterSurvival)")
		@Config.RequiresMcRestart
		public boolean educationTweak = false;

		@Config.Comment("Removes the ability to add protection enchant to shields")
		@Config.Name("Remove Shield Protection Enchant (Inspirations)")
		@Config.RequiresMcRestart
		public boolean inspirationsShieldEnchantRemove = false;

		@Config.Comment("Attempts to fix a desync caused by ScalingHealth when a mob dies in the same tick it is spawned")
		@Config.Name("ScalingHealth Death Desync (ScalingHealth)")
		@Config.RequiresMcRestart
		public boolean scalingHealthDesync = false;

		@Config.Comment("Forces Quark's Right-Click sign edit to sync the config value from server to client to prevent desyncs (Thanks to Venom)")
		@Config.Name("Sync Sign Edit Config (Quark)")
		@Config.RequiresMcRestart
		public boolean fixRightClickSignEdit = false;

		@Config.Comment("Rehandles the Sentient Scythe's (and Maul's) AOE effect to make it less ridiculous and more compatible with other effects")
		@Config.Name("Rehandle Sentient Scythe Effect (SRParasites/RLCombat)")
		@Config.RequiresMcRestart
		public boolean rehandleSentientScythe = false;

		@Config.Comment("Makes Strays and Husks ignore whether or not they can see the sky when spawning")
		@Config.Name("Stray/Husk Sky Spawning Fix (Vanilla)")
		@Config.RequiresMcRestart
		public boolean strayHuskSpawningFix = false;

		@Config.Comment("Makes Guardians not sink while idle in water")
		@Config.Name("Idle Guardian No Sink (Vanilla)")
		@Config.RequiresMcRestart
		public boolean guardianNoSink = false;

		@Config.Comment("Makes curing Zombie Villagers and pig lightning conversion count as a kill for Mob Spawner Control spawners")
		@Config.Name("Zombie Curing Ticks Spawners (Vanilla/MobSpawnerControl)")
		@Config.RequiresMcRestart
		public boolean curingTicksSpawners = false;

		@Config.Comment("Fixes Dragonbone Bow duping arrows with the BetterSurvival Multishot enchant")
		@Config.Name("InF Bow Multishot patch (IceAndFire/BetterSurvival)")
		@Config.RequiresMcRestart
		public boolean dragonboneBowMultishot = false;

		@Config.Comment("Allows for modifying the combo multiplier of Nunchaku")
		@Config.Name("Changeable Nunchaku Combo (BetterSurvival)")
		@Config.RequiresMcRestart
		public boolean nunchakuComboModifier = false;

		@Config.Comment("Fixes Vampirism from Better Survival healing the player when hitting non-living entities")
		@Config.Name("Vampirism Cheese Patch (BetterSurvival)")
		@Config.RequiresMcRestart
		public boolean vampirismCheese = false;

		@Config.Comment("Fixes Quark boat chests duping")
		@Config.Name("Quark Chest Boat Dupe (Quark)")
		@Config.RequiresMcRestart
		public boolean quarkChestBoatDupe = false;

		@Config.Comment("Fixes dupe issues with the Locks keyring")
		@Config.Name("Locks Keyring GUI Dupe Patch (Locks)")
		@Config.RequiresMcRestart
		public boolean locksGuiDupe = false;

		@Config.Comment("Fixes dupe issues with the ToolBelt belt")
		@Config.Name("ToolBelt Belt GUI Dupe Patch (ToolBelt)")
		@Config.RequiresMcRestart
		public boolean toolbeltGuiDupe = false;

		@Config.Comment("Fixes not being able to use Iron Canteen and Dragon Canteen on Rain Collectors")
		@Config.Name("Rain Collector Canteen Fix (SimpleDifficulty)")
		@Config.RequiresMcRestart
		public boolean canteenFix = false;

		@Config.Comment("Fixes the clay tool from NTP being abusable for guaranteed unbreaking enchantments")
		@Config.Name("Clay Tool Enchant Patch (NoTreePunching)")
		@Config.RequiresMcRestart
		public boolean clayToolPatch = false;

		@Config.Comment("Fixes the mattock from NTP not breaking when tilling ground")
		@Config.Name("Mattock Breaking Patch (NoTreePunching)")
		@Config.RequiresMcRestart
		public boolean mattockPatch = false;

		@Config.Comment("Fixes the quivers from Switchbow not closing when the quiver is dropped")
		@Config.Name("Switchbow Quiver Patch (Switchbow)")
		@Config.RequiresMcRestart
		public boolean switchbowQuiverPatch = false;

		@Config.Comment("Removes the arrow refund from Switchbow Love Arrows when hitting animals already in love")
		@Config.Name("Switchbow Love Arrow Dupe Fix (Switchbow)")
		@Config.RequiresMcRestart
		public boolean switchbowLoveArrowDupeFix = false;

		@Config.Comment("Fixes Luck Arrows from Switchbow setting looting level and not stacking with other looting mechanics")
		@Config.Name("Switchbow Luck Arrow Looting Set Fix (Switchbow)")
		@Config.RequiresMcRestart
		public boolean switchbowLootingSetFix = false;

		@Config.Comment("Fix ForgottenItems vein pickaxe mining tile entities and bypassing protection")
		@Config.Name("Vein Pickaxe Patch (ForgottenItems)")
		@Config.RequiresMcRestart
		public boolean veinPickaxePatch = false;

		@Config.Comment("Fix Reskillable cancelling indirect self damage")
		@Config.Name("Reskillable Indirect Self Damage Patch (Reskillable)")
		@Config.RequiresMcRestart
		public boolean reskillableIndirectSelfDamage = false;

		@Config.Comment("Replace the effect from the Scarlite Sword with a config-able effect")
		@Config.Name("Scarlite Sword Config Effect (DefiledLands)")
		@Config.RequiresMcRestart
		public boolean scarliteSwordConfigEffect = false;

		@Config.Comment("Fixes Disenchanting table crashing when broken while someone is still in the GUI")
		@Config.Name("Disenchanting Table Crash Patch (Disenchanter)")
		@Config.RequiresMcRestart
		public boolean disenchantingCrash = false;

		@Config.Comment("Fixes crashes caused by Quark's chat linking when items with large nbt are linked")
		@Config.Name("Quark Chat Link NBT Crash (Quark)")
		@Config.RequiresMcRestart
		public boolean chatLinkCrash = false;

		@Config.Comment("Makes potion effects actually display their values above amplifier 3 in the inventory")
		@Config.Name("Potion Amplifier Visibility (Vanilla)")
		@Config.RequiresMcRestart
		public boolean potionAmplifierVisibility = false;

		@Config.Comment("Makes potion effects actually display their values above amplifier 3 in dshud's display")
		@Config.Name("Potion Amplifier Visibility (DSHuds)")
		@Config.RequiresMcRestart
		public boolean potionAmplifierVisibilityDSHud = false;

		@Config.Comment("Replaces the Parasite Cleaver effect with a config defined effect")
		@Config.Name("Parasite Cleaver Effect Config (SRParasites)")
		@Config.RequiresMcRestart
		public boolean parasiteCleaverCustomEffect = false;

		@Config.Comment("Updates the adaption for indirect attacks to ignore the true damage source")
		@Config.Name("Parasite Indirect Damage Adaption Fix (SRParasites)")
		@Config.RequiresMcRestart
		public boolean parasiteIndirectDamageAdaptionFix = false;

		@Config.Comment("Stops Better Foliage's enable toggle from Chunk Animator XRaying")
		@Config.Name("Better Foliage Chunk XRay (BetterFoliage/ChunkAnimator)")
		@Config.RequiresMcRestart
		public boolean betterFoliageChunkXRay = false;

		@Config.Comment("Stops CarryOn from being able to pickup chests that have not had their loot generated")
		@Config.Name("CarryOn Ungenerated Chest Patch (CarryOn)")
		@Config.RequiresMcRestart
		public boolean carryOnUngeneratedChest = false;

		@Config.Comment("Stops CarryOn from being able to pickup pigs that still have saddles equipped, cows and squids with cooldown, or dead entities")
		@Config.Name("CarryOn Pig Saddle Patch (CarryOn)")
		@Config.RequiresMcRestart
		public boolean carryOnPigSaddlePatch = false;

		@Config.Comment("Fixes CorpseComplex improperly using ExperienceTotal for calculating XP returns and restoring player XP on death when it shouldnt")
		@Config.Name("CorpseComplex XP Death Fix (CorpseComplex)")
		@Config.RequiresMcRestart
		public boolean corpseComplexXPDeathFix = false;

		@Config.Comment("Fixes Charms Magnetic enchant being janky, possible memory leaks, and also duping on SpongeForge")
		@Config.Name("Magnetic Dupe Patch (Charm)")
		@Config.RequiresMcRestart
		public boolean magneticDupePatch = false;

		@Config.Comment("Modifies BetterSurvivals Penetration enchant to use SpartanWeaponrys penetration")
		@Config.Name("Penetration Fix (BetterSurvival/SpartanWeaponry/RLCombat)")
		@Config.RequiresMcRestart
		public boolean penetrationFix = false;

		@Config.Comment("Fixes Flowering Oak Leaves not working properly on Dynamic Trees")
		@Config.Name("Flowering Oak DT Fix (DynamicTrees/BOP/DTBOP)")
		@Config.RequiresMcRestart
		public boolean floweringOakDTFix = false;

		@Config.Comment("Modifies the world creation process to limit users to creating only the preset world type")
		@Config.Name("OTG Create World Simplify Fix (OTG)")
		@Config.RequiresMcRestart
		public boolean otgCreateWorldFix = false;

		@Config.Comment("Modifies Spriggans to properly allow changing the farming growth rate")
		@Config.Name("Spriggan Growth Rate Override (LycanitesMobs)")
		@Config.RequiresMcRestart
		public boolean sprigganGrowthPatch = false;

		@Config.Comment("Stops sleeping from resetting weather and weather timers, causing weather to be less common")
		@Config.Name("Stop Sleeping Resetting Weather MC-63340 (Vanilla)")
		@Config.RequiresMcRestart
		public boolean stopSleepResetWeather = false;

		@Config.Comment("Fixes Blast Protection knockback flooring the reduction making it not effective")
		@Config.Name("Blast Protection Knockback Patch MC-198809 (Vanilla)")
		@Config.RequiresMcRestart
		public boolean blastProtKnockback = false;

		@Config.Comment("Modifies BetterSurvival to fix a crash with TickDynamic, and improve performance")
		@Config.Name("BetterSurvival TickDynamic Crash (BetterSurvival)")
		@Config.RequiresMcRestart
		public boolean bsTickDynamic = false;

		@Config.Comment("Limits QualityTools to applying modifiers to only Players and tamed Horses for performance")
		@Config.Name("QualityTools Limit Modifiers (QualtiyTools)")
		@Config.RequiresMcRestart
		public boolean qualityToolsLimitModifiers = false;

		@Config.Comment("Stops LycanitesMobs from calling String::toLowerCase for every ObjectManager.getEffect() call")
		@Config.Name("LycanitesMobs Lowercase Performance Patch (LycanitesMobs)")
		@Config.RequiresMcRestart
		public boolean lycaniteLowercasePatch = false;

		@Config.Comment("Makes BaseCreatureEntity summon minions method spawn with persistence")
		@Config.Name("LycanitesMobs Minion Persistence Patch (LycanitesMobs)")
		@Config.RequiresMcRestart
		public boolean lycaniteMinionPersistencePatch = false;

		@Config.Comment("Optimizes performance of BetterSurvival LivingUpdateHandler by caching and skipping agility enchant for non-players")
		@Config.Name("BetterSurvival LivingUpdateHandler Optimization (BetterSurvival)")
		@Config.RequiresMcRestart
		public boolean bsLivingUpdatePerf = false;

		@Config.Comment("Disables XaTs magic handler from running for non players for performance")
		@Config.Name("XaT Magic Handler Players Only (Trinkets and Baubles)")
		@Config.RequiresMcRestart
		public boolean xatMagicHandlerPerf = false;

		@Config.Comment("Caches System.currentTimeMillis per tick for use by WorldBorder::getDiameter for performance")
		@Config.Name("Cache WorldBorder currentTime (Vanilla)")
		@Config.RequiresMcRestart
		public boolean cacheWorldBorder = false;

		@Config.Comment("Suppresses DynamicTrees falling tree missing branch errors (Not needed with DramaticTrees)")
		@Config.Name("Supress DT Branch Errors (DynamicTrees)")
		@Config.RequiresMcRestart
		public boolean suppressDTError = false;

		@Config.Comment("Makes DT Leaves spawning on worldgen ignore light level for performance")
		@Config.Name("DT Leaves Ignore Light on WorldGen (DynamicTrees)")
		@Config.RequiresMcRestart
		public boolean dtLeavesLightIgnore = false;

		@Config.Comment("Disables water from forced updates on chunk gen")
		@Config.Name("Remove Water Chunk Gen Ticking (Vanilla)")
		@Config.RequiresMcRestart
		public boolean waterChunkGenPerf = false;

		@Config.Comment("Patches Quarks EnderWatcher to greatly improve performance")
		@Config.Name("EnderWatcher Performance Patch (Quark)")
		@Config.RequiresMcRestart
		public boolean enderWatcherPerf = false;

		@Config.Comment("Fixes a memory leak in BetterNether when going from singleplayer to multiplayer (Thanks to Meldexun)")
		@Config.Name("BetterNether Memory Leak Fix (BetterNether)")
		@Config.RequiresMcRestart
		public boolean betterNetherMemLeak = false;

		@Config.Comment("Fixes a memory leak in BetterQuesting when going from singleplayer to multiplayer or logging out in another dimension (Thanks to Meldexun)")
		@Config.Name("BetterQuesting Memory Leak Fix (BetterQuesting Standard Expansion)")
		@Config.RequiresMcRestart
		public boolean betterQuestingMemLeak = false;

		@Config.Comment("BetterQuesting Memory Leak Fix but for BetterQuesting Unofficial")
		@Config.Name("BetterQuesting Unofficial Memory Leak Fix (BetterQuesting Unofficial)")
		@Config.RequiresMcRestart
		public boolean betterQuestingUnofficialMemLeak = false;

		@Config.Comment("Fixes multiple memory leaks in MoBends (Thanks to Meldexun)")
		@Config.Name("MoBends Memory Leak Fix (MoBends)")
		@Config.RequiresMcRestart
		public boolean mobendsMemLeak = false;

		@Config.Comment("Suppresses JEI outputting too many input errors related to Varied Commodities")
		@Config.Name("JEI Suppress Varied Commodities Errors (JEI)")
		@Config.RequiresMcRestart
		public boolean jeiSuppressVC = false;

		@Config.Comment("Suppresses Forge's potentially dangerous prefix errors")
		@Config.Name("Forge Suppress Dangerous Prefix Errors (Forge)")
		@Config.RequiresMcRestart
		public boolean forgeDangerousPrefix = false;

		@Config.Comment("Suppresses Forge's broken ore dictionary errors")
		@Config.Name("Forge Suppress Broken Ore Dictionary Errors (Forge)")
		@Config.RequiresMcRestart
		public boolean forgeBrokenOreDict = false;

		@Config.Comment("Rewrites Elenai Dodge's TickEventListener for better performance when ability caps and cooldowns are set to 0")
		@Config.Name("Elenai Dodge Better Tick Performance (Elenai Dodge)")
		@Config.RequiresMcRestart
		public boolean elenaiDodgePerformance = false;

		@Config.Comment("Forcibly disable Quarks Springy Slime check for performance")
		@Config.Name("Quark Springy Slime Force Disable (Quark)")
		@Config.RequiresMcRestart
		public boolean quarkSpringySlimeDisable = false;

		@Config.Comment("Caches reflection in SereneSeasons BiomeHook to fix severe wasted performance")
		@Config.Name("SereneSeasons Reflection Caching Patch (SereneSeasons)")
		@Config.RequiresMcRestart
		public boolean sereneSeasonsReflection = false;

		@Config.Comment("Forcibly disable Quarks Emotes for performance")
		@Config.Name("Quark Emotes Force Disable (Quark)")
		@Config.RequiresMcRestart
		public boolean quarkEmoteForceDisable = false;

		@Config.Comment("Caches XaTs config attributes for better performance and mem usage")
		@Config.Name("XaT Cache Config Attributes (Trinkets and Baubles)")
		@Config.RequiresMcRestart
		public boolean xatCacheConfig = false;

		@Config.Comment("Force disables Quarks Potion Colorizer from running and wasting performance and networking")
		@Config.Name("Quark Force Disable Potion Colorizer (Quark)")
		@Config.RequiresMcRestart
		public boolean quarkColorizer = false;

		@Config.Comment("Slows how often item entities update their position to improve performance. WARNING: It is not recommended you use this feature unless you absolutely need to, it may cause issues with certain item mechanics")
		@Config.Name("Slowed Item Entity Movement (Vanilla)")
		@Config.RequiresMcRestart
		public boolean slowItemMovement = false;

		@Config.Comment("Reduces the frequency of Quark attempting to replace villager AI to open double doors for performance")
		@Config.Name("Quark Reduced Villager Double Door AI Checks (Quark)")
		@Config.RequiresMcRestart
		public boolean quarkDoubleDoor = false;

		@Config.Comment("Disallows respawning in the Lost Cities")
		@Config.Name("Lost Cities No Respawn (Lost Cities)")
		@Config.RequiresMcRestart
		public boolean lostCityRespawn = false;

		@Config.Comment("Enables setting a number of retries in the server config to attempt to get a better random respawn location")
		@Config.Name("Random Respawn Attempt Safety (Vanilla)")
		@Config.RequiresMcRestart
		public boolean randomRespawnMixin = false;
		
		@Config.Comment("Forces OTG to use Vanilla spawn checks instead of its modified respawn method")
		@Config.Name("Force OTG No Set Spawn (OTG)")
		@Config.RequiresMcRestart
		public boolean forceOTGNoSetSpawn = false;

		@Config.Comment("Disables Mo'Bends online checks that can cause the game to freeze on loading")
		@Config.Name("Disable MoBends Online Checks (MoBends)")
		@Config.RequiresMcRestart
		public boolean moBendsOnlineCheck = false;

		@Config.Comment("Forces Stonelings to assume minimum entity eyeheight to prevent infinite loops")
		@Config.Name("Stoneling Eyeheight Stall Patch (Quark)")
		@Config.RequiresMcRestart
		public boolean stonelingLoopPatch = false;

		@Config.Comment("Allows Stonelings to spawn in all biomes with zombies")
		@Config.Name("Stoneling Spawn Patch (Quark)")
		@Config.RequiresMcRestart
		public boolean stonelingSpawnPatch = false;

		@Config.Comment("Allows for setting different minimum and maximum Gamma values")
		@Config.Name("Modify Gamma Max And Min (Vanilla)")
		@Config.RequiresMcRestart
		public boolean allowModifyGamma = false;

		@Config.Comment("Makes the Dummy display values in damage not hearts")
		@Config.Name("Dummy Damage Value Patch (MmmMmmMmmMmm)")
		@Config.RequiresMcRestart
		public boolean patchDummyDamage = false;

		@Config.Comment("Silences broken advancement error messages")
		@Config.Name("Broken Advancement Log Spam Silence (Vanilla/Forge)")
		@Config.RequiresMcRestart
		public boolean silenceBrokenAdvancement = false;

		@Config.Comment("Fixes Quark's armor rune enchantment glint not working when Optifine is installed")
		@Config.Name("Quark Rune Optifine Fix (Quark)")
		@Config.RequiresMcRestart
		public boolean quarkRuneOptifineFix = false;

		@Config.Comment("Modifies EnhancedVisual's death messages to be translatable")
		@Config.Name("EV Death Message Translation (EnhancedVisuals)")
		@Config.RequiresMcRestart
		public boolean evDeathMessage = false;

		@Config.Comment("Modifies Neat's rendering to make it (slightly) more compatible with shaders")
		@Config.Name("Neat Shaders Patch (Neat)")
		@Config.RequiresMcRestart
		public boolean neatShadersPatch = false;

		@Config.Comment("Modifies DSurround's chat bubble rendering to make it (slightly) more compatible with shaders")
		@Config.Name("DSurround Chat Bubble Shaders Patch (DSurround)")
		@Config.RequiresMcRestart
		public boolean dsurroundShadersPatch = false;

		@Config.Comment("Reverts the behavior of JEI moving bookmarks out of the config folder")
		@Config.Name("JEI Revert Bookmark Location Changes (JEI)")
		@Config.RequiresMcRestart
		public boolean jeiRevertBookmark = false;

		@Config.Comment("Nukes the Advancement system from loading")
		@Config.Name("Nuke Advancements (Vanilla)")
		@Config.RequiresMcRestart
		public boolean nukeAdvancements = false;

		@Config.Comment("Makes JEI ignore anvil enchantment recipes to save on memory")
		@Config.Name("JEI Ignore Anvil Recipes (JEI)")
		@Config.RequiresMcRestart
		public boolean jeiAnvil = false;

		@Config.Comment("Fixes the BattleTower Golem never clearing its attack target, even if its target died and respawned")
		@Config.Name("Battletower Golem Attack Target Patch (Battletowers)")
		@Config.RequiresMcRestart
		public boolean battletowerGolemTarget = false;

		@Config.Comment("Modifies the radius of spawn chunks to keep loaded even when a player is not near")
		@Config.Name("Spawn Chunk Radius Patch (Vanilla)")
		@Config.RequiresMcRestart
		public boolean spawnChunkRadiusPatch = false;

		@Config.Comment("Adds a progress bar during DregoraRL first time setup file creation")
		@Config.Name("DregoraRL First Time Setup Progress (DregoraRL)")
		@Config.RequiresMcRestart
		public boolean dregoraRLProgress = false;

		@Config.Comment("Removes BQU's hardcoded handling of the backspace key to allow BQUTweaker to handle it instead")
		@Config.Name("BQU Remove Hardcoded Backspace (BetterQuesting Unofficial)")
		@Config.RequiresMcRestart
		public boolean bquBackspace = false;

		@Config.Comment("Patches Bloodmoon's red light rendering to work when Optifine is installed")
		@Config.Name("Bloodmoon Optifine Patch (Bloodmoon)")
		@Config.RequiresMcRestart
		public boolean bloodmoonOptifine = false;

		@Config.Comment("Cache DynamicTree leaf and branch AABBs to save on memory allocation usage")
		@Config.Name("DynamicTrees AABB Cache (DynamicTrees)")
		@Config.RequiresMcRestart
		public boolean dynamicTreeCache = false;

		@Config.Comment("Cache player chunk position to not refresh visible chunk list every tick to save on memory allocation")
		@Config.Name("Cache Player Chunk Visibility (Vanilla)")
		@Config.RequiresMcRestart
		public boolean cachePlayerChunkVisibility = false;

		@Config.Comment("Sets world flammable checks to use an existing mutable blockpos instead of creating a new one during flammable checks, for memory usage")
		@Config.Name("World Flammable BlockPos Replacement (Vanilla)")
		@Config.RequiresMcRestart
		public boolean worldFlammableBlockpos = false;

		@Config.Comment("Caches the results of BetterFoliage Class whitelist/blacklist checks for performance and memory usage")
		@Config.Name("BetterFoliage Whitelist/Blacklist Cache (BetterFoliage)")
		@Config.RequiresMcRestart
		public boolean betterFoliageListCache = false;

		@Config.Comment("Modifies BetterFoliage geometry checks to not create a new BlockPos if offset is 0")
		@Config.Name("BetterFoliage Geometry Offset (BetterFoliage)")
		@Config.RequiresMcRestart
		public boolean betterFoliageGeometry = false;

		@Config.Comment("Sets particles by default to not do collision checks, unless defined in the relevant config setting")
		@Config.Name("Particle Collision Defaults (Vanilla)")
		@Config.RequiresMcRestart
		public boolean particleCollision = false;

		@Config.Comment("Fixes issues with rune id handling resulting in missing runes from creative and invalid runes in loot")
		@Config.Name("ForgottenItems Rune Fix (ForgottenItems)")
		@Config.RequiresMcRestart
		public boolean forgottenItemsRune = false;

		@Config.Comment("Fixes Rusting curse allowing items to get negative durability")
		@Config.Name("Rusting Curse Negative Durability Patch (Charm)")
		@Config.RequiresMcRestart
		public boolean rustingCurseNegative = false;

		@Config.Comment("Prevents the soft killing from Charm Rune Portals")
		@Config.Name("Colored Rune Portals only teleport players (Quark/Charm)")
		@Config.RequiresMcRestart
		public boolean runePortalPlayersOnly = false;

		@Config.Comment("Makes Quark's enchanted book tooltip rendering wrap lines and fixed item lighting rendering")
		@Config.Name("Quark Enchanted Book Tooltip Rendering Patch (Quark)")
		@Config.RequiresMcRestart
		public boolean quarkEnchantedTooltip = false;

		@Config.Comment("Fixes Mo'Bends arrow trail animation not clearing entries properly")
		@Config.Name("MoBends Arrow Trail Patch (MoBends)")
		@Config.RequiresMcRestart
		public boolean mobendArrowTrail = false;

		@Config.Comment("Fixes arrows in ground rendering particles like tipped arrows when a world is reloaded")
		@Config.Name("Untipped Arrow Particles Fix (Vanilla)")
		@Config.RequiresMcRestart
		public boolean untippedArrowParticle = false;

		@Config.Comment("Fixes ScalingHealth's bandaged effect not having an icon")
		@Config.Name("ScalingHealth Bandaged Icon Fix (ScalingHealth)")
		@Config.RequiresMcRestart
		public boolean scalingHealthBandaged = false;

		@Config.Comment("Allows for blacklisting biomes to prevent spawning of Mineshafts")
		@Config.Name("Mineshaft Biome Blacklist Patch (Vanilla)")
		@Config.RequiresMcRestart
		public boolean mineshaftBlacklistPatch = false;

		@Config.Comment("Fix Food Expansion dropping horse meat from llamas")
		@Config.Name("Horse Meat From Llamas Fix (FoodExpansion)")
		@Config.RequiresMcRestart
		public boolean horseMeatLlamaFix = false;

		@Config.Comment("Allows for placing LycanitesMobs charges in item frames")
		@Config.Name("Fix LycanitesMobs Charges in Item Frames (LycanitesMobs)")
		@Config.RequiresMcRestart
		public boolean lycaniteChargeItemFrame = false;

		@Config.Comment("Fixes the player's model shaking when in the death screen")
		@Config.Name("Fix Player Model Death Shake (Vanilla)")
		@Config.RequiresMcRestart
		public boolean playerModelDeathShake = false;

		@Config.Comment("Fixes JSONPaintings crashing when trying to place a random painting in an invalid location")
		@Config.Name("JSONPaintings Placement Crash Fix (JSONPaintings)")
		@Config.RequiresMcRestart
		public boolean jsonPaintingsCrash = false;

		@Config.Comment("Overhauls and fixes some issues with DramaticTrees such as making sound volume dependant on speed/size and allowing for passing through or breaking additional blocks")
		@Config.Name("DramaticTrees Falling Overhaul (DramaticTrees)")
		@Config.RequiresMcRestart
		public boolean dramaticTreesFallingOverhaul = false;

		@Config.Comment("Allows for displaying the Y level in DSHuds when holding the Barometer from Inspirations (Also displays light level from photometer)")
		@Config.Name("DSHuds Barometer Patch (DSHuds/Inspirations)")
		@Config.RequiresMcRestart
		public boolean dsHudBarometerPatch = false;

		@Config.Comment("Fixes BountifulBaubles shields not properly overriding isShield method and cancels custom on hit durability handling")
		@Config.Name("BountifulBaubles isShield Fix (BountifulBaubles)")
		@Config.RequiresMcRestart
		public boolean bountifulBaublesShieldFix = false;

		@Config.Comment("1. Avoid isShield true and 2. stop ShieldBreak from destroying bauble shields instead of preserving at 0 durability")
		@Config.Name("ShieldBreak Handles BountifulBaubles Shields (BountifulBaubles/ShieldBreak)")
		@Config.RequiresMcRestart
		public boolean bountifulBaublesShieldBreak = false;

		@Config.Comment("Reworks Waystones used name system to use less memory and be more performant")
		@Config.Name("Rework Waystone Used Name Check (Waystones)")
		@Config.RequiresMcRestart
		public boolean waystonesUsedNameRework = false;

		@Config.Comment("Forces carts to be unpulled when the distance between the cart and the puller in a single tick is too high")
		@Config.Name("Force Cart Unpull Over Distance (AstikorCarts)")
		@Config.RequiresMcRestart
		public boolean astikorCartUnpull = false;

		@Config.Comment("Fixes ForgottenItems bound tools NBT being reset when the tool is bound")
		@Config.Name("ForgottenItems Fix Binding NBT (ForgottenItems)")
		@Config.RequiresMcRestart
		public boolean forgottenItemsBoundNBT = false;

		@Config.Comment("Fixes BetterNether double slabs not dropping items when broken")
		@Config.Name("BetterNether Double Slab Drop Fix (BetterNether)")
		@Config.RequiresMcRestart
		public boolean betterNetherDoubleSlabFix = false;

		@Config.Comment("Fixes mobs spawned from Bloodmoon being able to pick up loot (And then despawn with the loot)")
		@Config.Name("Bloodmoon Loot Pickup Fix (Vanilla/Bloodmoon)")
		@Config.RequiresMcRestart
		public boolean bloodmoonLootPickupFix = false;

		@Config.Comment("Fixes BetterNether doors being duped when broken")
		@Config.Name("BetterNether Door Dupe Fix (BetterNether)")
		@Config.RequiresMcRestart
		public boolean betterNetherDoorDupe = false;

		@Config.Comment("Allows for hoes to be repaired with their repair material like normal tools")
		@Config.Name("Allow Hoe Repairing (Vanilla)")
		@Config.RequiresMcRestart
		public boolean hoeRepair = false;

		@Config.Comment("Allows for setting timings of weather events with config values")
		@Config.Name("Weather Timing Config (Vanilla)")
		@Config.RequiresMcRestart
		public boolean weatherTiming = false;

		@Config.Comment("Fixes the Skeleton King not dropping loot if it is set to not drop loot inside a chest")
		@Config.Name("Skeleton King Loot Drop Fix (FishsUndeadRising)")
		@Config.RequiresMcRestart
		public boolean skeletonKingLootFix = false;

		@Config.Comment("Allows for changing the dungeon weight of the Lucky Horseshoe")
		@Config.Name("Allow Changing Lucky Horseshoe Weight (BountifulBaubles)")
		@Config.RequiresMcRestart
		public boolean luckyHorseShoeWeightTweak = false;

		@Config.Comment("Fixes passengers and riders being killed when an entity is picked up")
		@Config.Name("CarryOn Passenger Rider Death Fix (CarryOn)")
		@Config.RequiresMcRestart
		public boolean carryOnPositionFix = false;

		@Config.Comment("Fixes being able to throw CarryOn entities/tiles by holding Q")
		@Config.Name("ItemPhysics Q CarryOn Fix (CarryOn/ItemPhysics)")
		@Config.RequiresMcRestart
		public boolean carryOnItemPhysicsFix = false;

		@Config.Comment("Fixes possible rare crash when an item with invalid particle texture is placed in a bookshelf")
		@Config.Name("Inspirations Bookshelf Color Crash Fix (Inspirations)")
		@Config.RequiresMcRestart
		public boolean inspirationsColorShelfFix = false;
		
		@Config.Comment("Fixes a vanilla/forge bug limiting the xp result of smelting to 1 - 2 xp per item")
		@Config.Name("Furnace XP Limit Fix (Vanilla)")
		@Config.RequiresMcRestart
		public boolean furnaceXPFix = false;
		
		@Config.Comment("Fixes aqua mobs from Fish's Undead Rising not being able to spawn from spawners underwater")
		@Config.Name("Aquatic Mob Underwater Spawning (FishsUndeadRising)")
		@Config.RequiresMcRestart
		public boolean aquaSpawningFix = false;
		
		@Config.Comment("Adds checks to SRParasite Bush generation to hopefully prevent chunk loading/generation of unloaded chunks")
		@Config.Name("SRP Bush Generation Loaded Checks (SRParasites)")
		@Config.RequiresMcRestart
		public boolean srpBushGen = false;
		
		@Config.Comment("Improves the Defiled corruption checks and adds the ability to lower the chance of corruption")
		@Config.Name("Defiled Corruption Improvements (DefiledLands)")
		@Config.RequiresMcRestart
		public boolean defiledCorruptionImprovement = false;
		
		@Config.Comment("Fixes the healing salve from RoughTweaks not giving back empty bowls after the first salve is used")
		@Config.Name("Healing Salve Bowl Return Fix (RoughTweaks)")
		@Config.RequiresMcRestart
		public boolean roughTweaksBowlFix = false;
		
		@Config.Comment("Fixes FancyMenu crashing when loaded serverside")
		@Config.Name("FancyMenu Server Crash (FancyMenu)")
		@Config.RequiresMcRestart
		public boolean fancyMenuServerCrash = false;
		
		@Config.Comment("Replaces the advancements tab button in the escape menu with a button for BetterQuesting's quest menu")
		@Config.Name("Advancement Tab Quest Replacement (Vanilla/BetterQuesting)")
		@Config.RequiresMcRestart
		public boolean advancementQuestingReplacement = false;
		
		@Config.Comment("Suppress removed entity warnings from EntityTracker")
		@Config.Name("Suppress EntityTracker Removed Entity Warnings (Vanilla)")
		@Config.RequiresMcRestart
		public boolean suppressEntityTracker = false;
		
		@Config.Comment("Allows for modifying the max amount of levels that bookwyrms can digest")
		@Config.Name("Modify BookWyrm Max Level (DefiledLands)")
		@Config.RequiresMcRestart
		public boolean bookWyrmMaxLevelModify = false;

		@Config.Comment("At low render distance (<9) mobs can spawn in lazy loaded chunks, creating an automatic mob switch. Set to true to prevent that.")
		@Config.Name("Prevent Mob spawns in lazy loaded chunks (Vanilla)")
		@Config.RequiresMcRestart
		public boolean preventLazySpawns = false;
		
		@Config.Comment("Suppresses the Received passengers for unknown entity log warnings")
		@Config.Name("Suppress Unknown Passengers Warnings (Vanilla)")
		@Config.RequiresMcRestart
		public boolean suppressUnknownPassenger = false;

		@Config.Comment("Changes the lang key for rubies to avoid overlap with BoP")
		@Config.Name("VC Ruby Name Change (VariedCommodities)")
		@Config.RequiresMcRestart
		public boolean vcRubyNameChange = false;
		
		@Config.Comment("Fixes grenades not being consumed if it is the last one")
		@Config.Name("Fishs Undead Grenade Consuming (Fish's Undead Rising)")
		@Config.RequiresMcRestart
		public boolean fishsGrenadeDupe = false;
		
		@Config.Comment("Improves some checks in Bloodmoon spawning for performance")
		@Config.Name("Bloodmoon Spawning Performance (Bloodmoon)")
		@Config.RequiresMcRestart
		public boolean bloodMoonPerformance = false;
		
		@Config.Comment("Adds additional checks to attempt to help prevent OTG's SaveToDIsk from crashing during pregeneration")
		@Config.Name("OTG Save To Disk Crash Checks (OTG)")
		@Config.RequiresMcRestart
		public boolean otgSaveToDiskCrash = false;

		@Config.Comment("Adds additional checks to attempt to help prevent OTG from crashing during world-gen")
		@Config.Name("OTG World-Gen Crash Checks (OTG)")
		@Config.RequiresMcRestart
		public boolean otgWorldGenCrash = false;

		@Config.Comment("Disable the digging AI for digging mobs that are not carrying a pickaxe")
		@Config.Name("Digging AI (Epic Siege Mod)")
		@Config.RequiresMcRestart
		public boolean epicSiegeModDiggingAI = false;
		
		@Config.Comment("Improves world load speed on large maps such as pregenerated servers when using OTG")
		@Config.Name("OTG CustomStructureCache Improve Load Speed (OTG)")
		@Config.RequiresMcRestart
		public boolean otgImproveLoadSpeed = false;
		
		@Config.Comment("Fixes exiting a hardcore world without spectating first not properly unloading the world")
		@Config.Name("Fix Hardcore World Not Unloading (Vanilla)")
		@Config.RequiresMcRestart
		public boolean hardcoreWorldUnload = false;
		
		@Config.Comment("Fixes a crash when something runs pickBlock on a ClassyHat hat stand and passes in a null player")
		@Config.Name("Fix ClassyHats Hat Container Null Player Crash (ClassyHats)")
		@Config.RequiresMcRestart
		public boolean classyHatNullPlayerCrash = false;
		
		@Config.Comment("Fixes Fish's Undead Rising Ghost Stew crashing/kicking players when eaten on a server")
		@Config.Name("Fix Fish's Undead Rising Ghost Stew Crash (Fish's Undead Rising)")
		@Config.RequiresMcRestart
		public boolean fishsUndeadRisingGhostStew = false;
		
		@Config.Comment("Fixes errors from processing packets received after the player has already left a world")
		@Config.Name("Fix Delayed Packet Errors (Vanilla)")
		@Config.RequiresMcRestart
		public boolean fixDelayedPacketErrors = false;
		
		@Config.Comment("Fixes a crash where mob spawner mob ids will overlap with existing ids causing a MoBends crash")
		@Config.Name("Fix Duplicate MoBends Render ID Crash (Vanilla/MoBends)")
		@Config.RequiresMcRestart
		public boolean moBendsDuplicateIDCrash = false;
		
		@Config.Comment("Fixes chunk generation errors when a doomlike dungeon attempts to generate in an area with no theme")
		@Config.Name("Doomlike Dungeon No Theme Error (DoomlikeDungeons)")
		@Config.RequiresMcRestart
		public boolean doomlikeNoThemeError = false;
		
		@Config.Comment("Fixes a memory leak with Fish's Undead Rising Skeleton King Crown")
		@Config.Name("Fishs Undead Rising Skeleton King Crown Mem Leak (Fishs Undead Rising)")
		@Config.RequiresMcRestart
		public boolean fishsSkeletonKingCrown = false;
		
		@Config.Comment("Fixes some Fishs Undead Rising mobs applying potion effects on the client side")
		@Config.Name("Fishs Undead Rising Client Side Effects (Fishs Undead Rising)")
		@Config.RequiresMcRestart
		public boolean fishsClientSideEffects = false;
		
		@Config.Comment("Forcibly disables OTG's pregenerator from running its tick operations which wastes performance as it runs when it isn't active")
		@Config.Name("Force Disable OTG Pregenerator Ticking (OTG)")
		@Config.RequiresMcRestart
		public boolean otgForceDisablePregenerator = false;
		
		@Config.Comment("Allows for defining a blacklist/whitelist of allowed armors to be worn as cosmetics")
		@Config.Name("Cosmetic Armor Blacklist (CosmeticArmorReworked)")
		@Config.RequiresMcRestart
		public boolean cosmeticArmorReworkedBlacklist = false;
		
		@Config.Comment("Blocks BlockConcretePowder from running onBlockAdded during worldgen for performance")
		@Config.Name("Chunk OnBlockAdded ConcretePowder Disable (Vanilla)")
		@Config.RequiresMcRestart
		public boolean chunkOnBlockAddedConcretePowderDisable = false;
		
		@Config.Comment("Disables non-SRP armor models from rendering in SRPLayerBipedArmor to avoid crashes")
		@Config.Name("SRParasites Layer Biped Armor Crash Fix (SRParasites)")
		@Config.RequiresMcRestart
		public boolean srpDisableLayerBipedArmorRender = false;
		
		@Config.Comment("Allows for replacing the world generation filler block by dimension id (Warning: this will occur a slight performance cost, and may cause issues with world generation that expects blocks to be stone)")
		@Config.Name("Dimension Custom Filler Block (Vanilla)")
		@Config.RequiresMcRestart
		public boolean dimensionCustomFillerBlock = false;
		
		@Config.Comment("Allows for setting the maximum range of bedrock generation")
		@Config.Name("Maximum Bedrock Generation Range (Vanilla)")
		@Config.RequiresMcRestart
		public boolean maxBedrockGenerationRange = false;
		
		@Config.Comment("Allows for setting additional blocks to allow to be carved by caves and ravines")
		@Config.Name("Additional Caves and Ravines Carver Blocks (Vanilla)")
		@Config.RequiresMcRestart
		public boolean additionalCaveRavineBlocks = false;
		
		@Config.Comment("Patches CodeChickenLib's Chunk Unwatch event to not run due to it causing severe world generation lag during dimension changing (Warning: If you use a mod that depends on this event, it may cause issues, you should only use this if you know you don't need it)")
		@Config.Name("CodeChickenLib Chunk Unwatch Lag (CodeChickenLib)")
		@Config.RequiresMcRestart
		public boolean codeChickenLibChunkLag = false;

		@Config.Comment("Disables old SME compatibility checks of BetterSurvival and SpartanWeaponry")
		@Config.Name("Disable old SME compat of BS and Spartan (BS/Spartan)")
		@Config.RequiresMcRestart
		public boolean disableSMEcompat = false;

		@Config.Comment("Keeps Quark Usage Ticker rendered permanently")
		@Config.Name("Usage Ticker stays visible (Quark)")
		@Config.RequiresMcRestart
		public boolean usageTickerStaysActive = false;

		@Config.Comment("Merge XP orbs up to a customisable max XP value")
		@Config.Name("Merge XP Orbs (Vanilla)")
		@Config.RequiresMcRestart
		public boolean mergeXpOrbs = false;

		@Config.Comment("Many mods apply potion effects clientside leading to desyncs. Set to true to disable those.")
		@Config.Name("Cancel false clientside addPotionEffect calls (Vanilla)")
		@Config.RequiresMcRestart
		public boolean cancelClientPotions = false;
		
		@Config.Comment("Allows for setting orbital period overrides for Advanced Rocketry")
		@Config.Name("Advanced Rocketry Orbital Overrides (Advanced Rocketry)")
		@Config.RequiresMcRestart
		public boolean advRocketryOverrides = false;

		@Config.Comment("Fix FirstAid HUD: Health bar moving left with OverlayMode=HEARTS and high health. Absorption hearts not being displayed with OverlayMode=NUMBERS. Configurable threshold for switching to numbers.")
		@Config.Name("Fix FirstAid Health Display (FirstAid)")
		@Config.RequiresMcRestart
		public boolean firstAidHUDFix = false;

		@Config.Comment("Fix startup crash when using NetherAPI with some BetterNether biomes being disabled.")
		@Config.Name("Fix NetherAPI Startup Crash (NetherAPI/BetterNether)")
		@Config.RequiresMcRestart
		public boolean netherAPICrashFix = false;

		@Config.Comment("Enable Quark Hats to be function as Head slot Baubles, also adds passive looting 1 to pirate hat")
		@Config.Name("Make Quark Hats Baubles (Quark/BaublesAPI)")
		@Config.RequiresMcRestart
		public boolean quarkHatsAreBaubles = false;
	}

	public static class ServerConfig {
		@Config.Comment("Item Blacklist for the Hungry Farmer trait.")
		@Config.Name("Hungry Farmer Blacklist")
		public String[] hungryFarmerBlacklist = {""};

		@Config.Comment("Fire ItemUseFinish event after eating with hungry farmer to also account for thirst and other side effects.")
		@Config.Name("Hungry Farmer Fires Forge Event")
		public boolean hungryFarmerFiresForgeEvent = false;

		@Config.Comment("Potion Blacklist for Tipped Arrows.")
		@Config.Name("Tipped Arrow Blacklist")
		public String[] tippedArrowBlacklist = {""};

		@Config.Comment("How many blocks to reduce fall distance by per tick in water")
		@Config.Name("Fall Distance Reduction in Water")
		@Config.RangeDouble(min = 1.0D, max = 100.0D)
		public double fallDistanceReduction = 4.0D;

		@Config.Comment("Register the Encumbered potion effect (Requires PotionCore)")
		@Config.Name("Register Encumbered")
		@Config.RequiresMcRestart
		public boolean registerEncumbered = false;

		@Config.Comment("Add and register Steel armor with custom models")
		@Config.Name("Register Steel Armor")
		@Config.RequiresMcRestart
		public boolean registerSteelArmor = false;

		@Config.Comment("Add and register Scarlite armor with custom models")
		@Config.Name("Register Scarlite Armor")
		@Config.RequiresMcRestart
		public boolean registerScarliteArmor = false;

		@Config.Comment("Add and register Antimagic Talisman, and a recipe for crafting it with enchanted items")
		@Config.Name("Register Antimagic Talisman")
		@Config.RequiresMcRestart
		public boolean registerAntimagicTalisman = false;

		@Config.Comment("Add and register Cleansing Talisman, a recipe for crafting a Curse Break book, and the Curse Break potion")
		@Config.Name("Register Cleansing Talisman (Charm)")
		@Config.RequiresMcRestart
		public boolean registerCleansingTalisman = false;

		@Config.Comment("Register the Lesser Fire Resistance potion effect")
		@Config.Name("Register Lesser Fire Resistance")
		@Config.RequiresMcRestart
		public boolean registerLesserFireResistance = false;

		@Config.Comment("Register the Silver's Immunity potion effect")
		@Config.Name("Register Silver Immunity")
		@Config.RequiresMcRestart
		public boolean registerSilverImmunity = false;

		@Config.Comment("Silver's Immunity checks the incurable state set by RLTweaker injects")
		@Config.Name("Silver Immunity checks if RLTweaker incurable (RLTweaker)")
		@Config.RequiresMcRestart
		public boolean silverImmunityRLTweakerCheck = false;

		@Config.Comment("Enables the Nether Bane weapon effect to deal bonus damage to nether mobs")
		@Config.Name("Enable Nether Bane (Requires RLCombat)")
		@Config.RequiresMcRestart
		public boolean enableNetherBane = false;

		@Config.Comment("List of mobs to be classed as nether-mobs for the Nether Bane effect")
		@Config.Name("Nether Bane Mob List")
		public String[] netherBaneMobs = {"minecraft:wither_skeleton", "minecraft:zombie_pigman", "minecraft:blaze", "minecraft:magma_cube", "minecraft:wither"};

		@Config.Comment("List of weapons to have the Nether Bane effect")
		@Config.Name("Nether Bane Weapon List")
		public String[] netherBaneWeapons = {""};

		@Config.Comment("If true, Nether Bane effect will multiply damage, if false, additive")
		@Config.Name("Nether Bane Multiply/Add")
		public boolean netherBaneMultiply = false;

		@Config.Comment("Value to either multiply damage by or add to damage for the Nether Bane effect")
		@Config.Name("Nether Bane Damage Value")
		public double netherBaneValue = 4.0;

		@Config.Comment("If true, Cobalt Shield will cancel knockback events, instead of only applying an attribute")
		@Config.Name("Cobalt Shield Cancels Knockback (Bountiful Bauble)")
		@Config.RequiresMcRestart
		public boolean cobaltShieldCancelsKnockback = false;

		@Config.Comment("Fixes squid and cow milking cooldowns")
		@Config.Name("Milking Cooldown Fix (Inspirations)")
		@Config.RequiresMcRestart
		public boolean milkingFix = false;

		@Config.Comment("Registers the Cow Potion effect")
		@Config.Name("Register Cow Potion")
		@Config.RequiresMcRestart
		public boolean registerCowPotion = false;

		@Config.Comment("Maximum Modifier for Nunchaku (Damage * (1+this))")
		@Config.Name("Nunchaku Combo Max Modifier")
		public float nunchakuComboMaxModifier = 1.0F;

		@Config.Comment("Makes the Ice/Fire blood recipes retain enchantments and repair cost, optional compatibility for BetterSurvival (Except for Spear) and SpartanFire (You will still need to remove the original recipes with CraftTweaker.)")
		@Config.Name("Register Enchantment Sensitive Flame/Ice Weapon Recipes (IceAndFire)")
		@Config.RequiresMcRestart
		public boolean registerEnchantmentSensitiveFlameIceWeapon = false;

		@Config.Comment("Effect given by Scarlite Sword when hitting an entity.")
		@Config.Name("Scarlite Sword Effect")
		public String scarliteSwordEffect = "lycanitesmobs:leech";

		@Config.Comment("Duration of effect given by Scarlite Sword when hitting an entity")
		@Config.Name("Scarlite Sword Effect Duration")
		@Config.RangeInt(min = 1, max = 1200)
		public int scarliteSwordDuration = 20;

		@Config.Comment("Amplifier of effect given by Scarlite Sword when hitting an entity")
		@Config.Name("Scarlite Sword Effect Amplifier")
		@Config.RangeInt(min = 0, max = 10)
		public int scarliteSwordAmplifier = 1;

		@Config.Comment("Effect given by Parasite Cleaver when hitting an entity.")
		@Config.Name("Parasite Cleaver Effect")
		public String parasiteCleaverEffect = "potioncore:vulnerable";

		@Config.Comment("Amplifier given by Living Cleaver when hitting an entity.")
		@Config.Name("Living Cleaver Amplifier")
		@Config.RangeInt(min = 0, max = 10)
		public int livingCleaverAmplifier = 0;

		@Config.Comment("Amplifier given by Sentient Cleaver when hitting an entity.")
		@Config.Name("Sentient Cleaver Amplifier")
		@Config.RangeInt(min = 0, max = 10)
		public int sentientCleaverAmplifier = 1;

		@Config.Comment("Makes Parasite armor cure/lower the effect of Fear")
		@Config.Name("Parasite Armor Fear Curing")
		@Config.RequiresMcRestart
		public boolean parasiteArmorFearCuring = false;

		@Config.Comment("Makes Parasite armor cure/lower the effect of Viral")
		@Config.Name("Parasite Armor Viral Curing")
		@Config.RequiresMcRestart
		public boolean parasiteArmorViralCuring = false;

		@Config.Comment("Makes Antidote Vessel deny and apply reduced effects on potion apply")
		@Config.Name("Antidote Vessel checks on potion apply")
		@Config.RequiresMcRestart
		public boolean antidoteFix = false;

		@Config.Comment("Makes Cure Potion Effect cure effects during attacks")
		@Config.Name("Cure Potion cures during attacks")
		@Config.RequiresMcRestart
		public boolean potionCureFix = false;

		@Config.Comment("Makes Attack 32 Battle Spirit give Strength II on kill with critical hit")
		@Config.Name("Reskillable Battle Spirit crit kill (RLCombat)")
		@Config.RequiresMcRestart
		public boolean battleSpirit32Crit = false;

		@Config.Comment("Makes Attack 32 Neutralissse give Haste II on first hit")
		@Config.Name("Reskillable Neutralissse first hit")
		@Config.RequiresMcRestart
		public boolean neutralissse32FirstHit = false;

		@Config.Comment("Makes Defense 32 Effect Twist have a chance to cure and pass debuffs based on amplifier")
		@Config.Name("Reskillable Effect Twist random cure (RLTweaker)")
		@Config.RequiresMcRestart
		public boolean effectTwist32Cure = false;

		@Config.Comment("Makes Drop Guarantee give passive minimum looting 1 and bonus looting 1 with Gathering 32")
		@Config.Name("Reskillable Drop Guarantee looting")
		@Config.RequiresMcRestart
		public boolean dropGuarantee32Looting = false;

		@Config.Comment("Makes Magic 32 Safe Port give Resistance III on EnderTeleportEvent")
		@Config.Name("Reskillable Safe Port Resistance")
		@Config.RequiresMcRestart
		public boolean safePort32Resistance = false;

		@Config.Comment("Makes Perfect Recover cause specified blocks to drop itself similar to silk touch")
		@Config.Name("Reskillable Perfect Recover Natural Silk Touch")
		@Config.RequiresMcRestart
		public boolean perfectRecoverSilk = false;

		@Config.Comment("Maximum amplifier of Fear while wearing Parasite armor (-1 = cures it)")
		@Config.Name("Parasite Armor Fear Max Amplifier")
		@Config.RangeInt(min = -1, max = 10)
		public int parasiteArmorFearMax = 1;

		@Config.Comment("Maximum amplifier of Viral while wearing Parasite armor (-1 = cures it)")
		@Config.Name("Parasite Armor Viral Max Amplifier")
		@Config.RangeInt(min = -1, max = 10)
		public int parasiteArmorViralMax = 2;

		@Config.Comment("Whether or not Atomic Deconstructor should attempt to replicate /kill damage (More effective, but may cause bugs)")
		@Config.Name("Atomic Deconstructor /kill")
		public boolean atomicDeconstructorMaxDamage = false;

		@Config.Comment("Whether or not Atomic Deconstructor should work on bosses (May cause bugs)")
		@Config.Name("Atomic Deconstructor Works on Bosses")
		public boolean atomicDeconstructorBosses = false;

		@Config.Comment("Every x ticks Spriggans attempt growing crops around them")
		@Config.Name("Spriggan Farming Rate")
		public int sprigganFarmingRate = 20;

		@Config.Comment("How many attempts will random respawning try to find a good spawn point. WARNING: Higher values will cause more lag on respawns")
		@Config.Name("Random Respawn Protection Attempts")
		public int randomRespawnProt = 0;
		
		@Config.Comment("If random respawning should attempt to avoid ocean biome spawns")
		@Config.Name("Random Respawn Avoid Oceans")
		public boolean randomRespawnAvoidOcean = false;

		@Config.Comment("Disallows all IMobs from entering carts/boats/astikor carts (Requires Boss Cart/Boat Cheese or Boss AstikorCart Cheese mixin enabled.)")
		@Config.Name("All Mob Cart Cheese")
		public boolean mobCartCheese = false;

		@Config.Comment("Makes lightning not destroy items")
		@Config.Name("Stop Lightning Destroying Items")
		@Config.RequiresMcRestart
		public boolean lightningItemDestroyFix = false;

		@Config.Comment("Allows for overriding entity view distances with alternate values")
		@Config.Name("Allow Entity View Distance Override (Vanilla)")
		public boolean allowEntityViewDistanceOverride = false;

		@Config.Comment("List of entities and the value of their view distance to override with in the format entityid=distance")
		@Config.Name("Entity View Distance Override List")
		public Map<String, Integer> entityViewDistanceList = new HashMap<String, Integer>() {{
			put("battletowers:golem", 64);
		}};

		@Config.Comment("Radius of spawn chunks to keep loaded in memory (-1 to load none, requires Spawn Chunk Radius Patch)")
		@Config.Name("Spawn Chunk Radius")
		@Config.RangeInt(min = -1, max = 8)
		public int spawnChunkRadius = 0;

		@Config.Comment("Biome name blacklist to prevent Mineshafts from spawning")
		@Config.Name("Mineshaft Biome Name Blacklist")
		public String[] mineshaftBiomeNameBlacklist = {""};

		@Config.Comment("Biome type blacklist to prevent Mineshafts from spawning")
		@Config.Name("Mineshaft Biome Type Blacklist")
		public String[] mineshaftBiomeTypeBlacklist = {""};

		@Config.Comment("If the class names of blocks that are collided with that are considered solid should be printed to console")
		@Config.Name("DramaticTrees Debug Collision Names")
		public boolean dramaticTreesCollisionNameDebug = false;

		@Config.Comment("List of blocks for DramaticTrees to treat as non-solid when falling")
		@Config.Name("DramaticTrees Non-Solid Blocks")
		public String[] dramaticTreesNonSolidBlocks = {
				"dynamictrees:leaves0",
				"minecraft:leaves",
				"minecraft:vine",
				"minecraft:double_plant",
				"minecraft:tallgrass",
				"notreepunching:loose_rock/stone",
				"notreepunching:loose_rock/sandstone"
		};

		@Config.Comment("List of blocks from the non-solid list for DramaticTrees to break while falling")
		@Config.Name("DramaticTrees Non-Solid Breakable Blocks")
		public String[] dramaticTreesNonSolidBreakableBlocks = {
				"dynamictrees:leaves0",
				"minecraft:leaves",
				"minecraft:vine",
				"minecraft:double_plant",
				"minecraft:tallgrass"
		};

		@Config.Comment("List of blocks for DramaticTrees to treat as solid but still break while falling")
		@Config.Name("DramaticTrees Solid Breakable Blocks")
		public String[] dramaticTreesSolidBreakableBlocks = {
		};

		@Config.Comment("List of blocks for Reskillable Perfect Recover to drop similarly to silk touch")
		@Config.Name("Reskillable Perfect Recover Natural Silk Blocks")
		public String[] reskillablePerfectRecoverSilkBlocks = {
				"minecraft:glass",
				"minecraft:glass_pane",
				"minecraft:stained_glass",
				"minecraft:stained_glass_pane",
				"minecraft:ice",
				"minecraft:magma"
		};

		@Config.Comment("If Rework Waystone Used Name Check is enabled, allows for removing Biome names from village waystones")
		@Config.Name("Village Waystones Remove Biome Name")
		public boolean villageWaystoneRemoveBiome = false;

		@Config.Comment("If Rework Waystone Used Name Check is enabled, allows for ignoring already used names, may further improve performance on large maps but results in repeated names")
		@Config.Name("Waystones Ignore Used Names")
		public boolean waystonesIgnoreUsedNames = false;

		@Config.Comment("Distance difference in a single tick that will cause Astikor Carts to become unpulled")
		@Config.Name("Cart Distance Limit")
		public double cartDistanceLimit = 32.0D;

		@Config.Comment("If Stop Sleeping Resetting Weather MC-63340 is true, makes sleeping still reset the weather if it is actively raining/thundering")
		@Config.Name("Fix Weather Reset on Sleep Conditionally")
		public boolean fixWeatherResetConditionally = false;

		@Config.Comment("If Weather Timing is enabled, sets the range of how many ticks thunder will last, added to minimum")
		@Config.Name("Thunder Duration Range Ticks")
		@Config.RangeInt(min = 1200)
		public int thunderActiveRange = 12000;

		@Config.Comment("If Weather Timing is enabled, sets the minimum amount of ticks thunder will last")
		@Config.Name("Thunder Duration Minimum Ticks")
		@Config.RangeInt(min = 1200)
		public int thunderActiveMinimum = 3600;

		@Config.Comment("If Weather Timing is enabled, sets the range of how many ticks it will take for thunder to start, added to minimum")
		@Config.Name("Thunder Time Until Start Range Ticks")
		@Config.RangeInt(min = 1200)
		public int thunderInactiveRange = 168000;

		@Config.Comment("If Weather Timing is enabled, sets the minimum amount of ticks it will take for thunder to start")
		@Config.Name("Thunder Time Until Start Minimum Ticks")
		@Config.RangeInt(min = 1200)
		public int thunderInactiveMinimum = 12000;

		@Config.Comment("If Weather Timing is enabled, sets the range of how many ticks rain will last, added to minimum")
		@Config.Name("Rain Duration Range Ticks")
		@Config.RangeInt(min = 1200)
		public int rainActiveRange = 12000;

		@Config.Comment("If Weather Timing is enabled, sets the minimum amount of ticks rain will last")
		@Config.Name("Rain Duration Minimum Ticks")
		@Config.RangeInt(min = 1200)
		public int rainActiveMinimum = 12000;

		@Config.Comment("If Weather Timing is enabled, sets the range of how many ticks it will take for rain to start, added to minimum")
		@Config.Name("Rain Time Until Start Range Ticks")
		@Config.RangeInt(min = 1200)
		public int rainInactiveRange = 168000;

		@Config.Comment("If Weather Timing is enabled, sets the minimum amount of ticks it will take for rain to start")
		@Config.Name("Rain Time Until Start Minimum Ticks")
		@Config.RangeInt(min = 1200)
		public int rainInactiveMinimum = 12000;

		@Config.Comment("If enabled, handles certain scripts from Dregora through RLMixins instead, for improved performance")
		@Config.Name("Handle Dregora Scripts")
		public boolean dregoraScriptHandling = false;

		@Config.Comment("Chance for an entity to have its arrow replaced with a tipped arrow")
		@Config.Name("Dregora Tipped Arrow Replacement Chance")
		@Config.RangeDouble(min = 0.0F, max = 1.0F)
		public float dregoraTippedArrowReplacementChance = 0.05F;

		@Config.Comment("List of entities to allow randomly adding tipped arrows")
		@Config.Name("Dregora Tipped Arrow Replacement Allowed Entities")
		public String[] dregoraTippedArrowEntities = {
				"minecraft:skeleton",
				"minecraft:wither_skeleton"
		};

		@Config.Comment("List of potion types to be used for tipped arrows randomly added to entities")
		@Config.Name("Dregora Tipped Arrow Replacement Allowed PotionTypes")
		public String[] dregoraTippedArrowPotionTypes = {
				"potioncore:strong_broken_armor",
				"potioncore:broken_armor",
				"potioncore:long_klutz",
				"potioncore:strong_klutz",
				"potioncore:klutz",
				"potioncore:dispel",
				"potioncore:strong_launch",
				"potioncore:launch",
				"potioncore:long_weight",
				"potioncore:long_broken_armor",
				"potioncore:spin",
				"potioncore:strong_spin",
				"potioncore:long_spin",
				"potioncore:curse",
				"potioncore:strong_curse",
				"quark:mining_fatigue",
				"quark:long_mining_fatigue",
				"quark:strong_mining_fatigue",
				"potioncore:disorganization",
				"srparasites:foster",
				"srparasites:coth",
				"srparasites:fear",
				"srparasites:res",
				"srparasites:corro",
				"srparasites:vira",
				"srparasites:rage",
				"srparasites:debar",
				"potioncore:magic_inhibition",
				"potioncore:weight",
				"potioncore:lightning",
				"potioncore:strong_explode",
				"potioncore:long_magic_inhibition",
				"potioncore:teleport",
				"potioncore:strong_teleport",
				"potioncore:teleport_surface",
				"potioncore:drown",
				"potioncore:long_drown",
				"potioncore:teleport_spawn",
				"potioncore:long_vulnerable",
				"potioncore:strong_vulnerable",
				"potioncore:vulnerable",
				"potioncore:long_rust",
				"potioncore:strong_rust",
				"potioncore:rust",
				"potioncore:long_perplexity",
				"potioncore:perplexity",
				"minecraft:long_slowness",
				"minecraft:slowness",
				"mujmajnkraftsbettersurvival:milk",
				"mujmajnkraftsbettersurvival:long_antiwarp",
				"mujmajnkraftsbettersurvival:antiwarp",
				"mujmajnkraftsbettersurvival:strong_decay",
				"mujmajnkraftsbettersurvival:long_decay",
				"mujmajnkraftsbettersurvival:decay",
				"mujmajnkraftsbettersurvival:long_blindness",
				"potioncore:long_blindness",
				"potioncore:nausea",
				"potioncore:long_nausea",
				"potioncore:levitation",
				"potioncore:strong_levitation",
				"potioncore:long_levitation",
				"potioncore:unluck",
				"potioncore:long_hunger",
				"potioncore:strong_hunger",
				"potioncore:hunger",
				"potioncore:long_wither",
				"potioncore:strong_wither",
				"potioncore:wither",
				"elenaidodge:long_sluggish",
				"elenaidodge:sluggish",
				"elenaidodge:strong_feeble",
				"minecraft:harming",
				"minecraft:strong_harming",
				"minecraft:poison",
				"minecraft:long_poison",
				"minecraft:strong_poison",
				"minecraft:long_weakness",
				"mujmajnkraftsbettersurvival:blindness",
				"elenaidodge:feeble",
				"elenaidodge:long_feeble",
				"potioncore:strong_magic_inhibition",
				"potioncore:strong_weight",
				"potioncore:fire",
				"potioncore:invert",
				"potioncore:long_broken_magic_shield",
				"potioncore:broken_magic_shield",
				"potioncore:strong_broken_magic_shield",
				"potioncore:strong_blindness",
				"potioncore:blindness",
				"potioncore:explode",
				"xat:extended_goblin",
				"xat:goblin"
		};

		@Config.Comment("List of potion resource locations that are always incurable")
		@Config.Name("Silver's Immunity Blacklisted Potions")
		public String[] silverBlackListedPotions = {
				"potioncore:potion_sickness",
				"rustic:tipsy",
				"rustic:shame",
				"srparasites:prey",
				"rlmixins:encumbered",
				"simpledifficulty:hypothermia",
				"simpledifficulty:hyperthermia",
				"simpledifficulty:thirsty",
				"minecraft:hunger",
				"defiledlands:bleeding",
				"lycanitesmobs:bleed",
				"srparasites:bleed",
				"mod_lavacow:fear",
				"lycanitesmobs:fear",
				"srparasites:fear",
				"champions:jailed",
				"champions:injured",
				"mod_lavacow:corroded",
				"lycanitesmobs:penetration",
				"srparasites:corrosive",
				"mod_lavacow:fragile_king",
				"lycanitesmobs:decay",
				"lycanitesmobs:instability",
				"lycanitesmobs:paralysis",
				"srparasites:needler",
				"mod_lavacow:infested",
				"defiledlands:vulnerability",
				"potioncore:vulnerable",
				"potioncore:broken_armor",
				"potioncore:broken_magic_shield",
				"potioncore:curse",
				"potioncore:invert",
				"potioncore:spin",
				"potioncore:rust",
				"potioncore:disorganization",
				"potioncore:explode",
				"potioncore:lightning",
				"rlmixins:delayed_launch",
				"potioncore:launch",
				"potioncore:dispel"
		};

		@Config.Comment("Changes the weight of Lucky Horseshoe in dungeon loot tables")
		@Config.Name("Lucky Horseshoe Dungeon Weight")
		@Config.RangeInt(min = 1)
		public int luckyHorseshoeWeight = 10;

		@Config.Comment("Effect that drinking Ale should give")
		@Config.Name("Ale Effect")
		public String aleEffect = "lycanitesmobs:immunization";

		@Config.Comment("Maximum duration of the positive effect given by ale at quality 1.0")
		@Config.Name("Ale Maximum Duration Positive")
		public float aleMaxDurationPositive = 12000;

		@Config.Comment("Maximum duration of the hunger effect given by ale below quality 0.5")
		@Config.Name("Ale Maximum Duration Hunger")
		public float aleMaxDurationHunger = 6000;

		@Config.Comment("Maximum duration of the nausea effect given by ale below quality 0.5")
		@Config.Name("Ale Maximum Duration Nausea")
		public float aleMaxDurationNausea = 6000;

		@Config.Comment("Inebriation chance when drinking ale")
		@Config.Name("Ale Inebriation Chance")
		public float aleInebriationChance = 0.5F;

		@Config.Comment("Effect that drinking Cider should give")
		@Config.Name("Cider Effect")
		public String ciderEffect = "potioncore:magic_shield";

		@Config.Comment("Maximum duration of the positive effect given by cider at quality 1.0")
		@Config.Name("Cider Maximum Duration Positive")
		public float ciderMaxDurationPositive = 12000;

		@Config.Comment("Maximum duration of the poison effect given by cider below quality 0.5")
		@Config.Name("Cider Maximum Duration Poison")
		public float ciderMaxDurationPoison = 1200;

		@Config.Comment("Maximum duration of the nausea effect given by cider below quality 0.5")
		@Config.Name("Cider Maximum Duration Nausea")
		public float ciderMaxDurationNausea = 6000;

		@Config.Comment("Inebriation chance when drinking cider")
		@Config.Name("Cider Inebriation Chance")
		public float ciderInebriationChance = 0.5F;

		@Config.Comment("Maximum absorption given by iron wine at quality 1.0")
		@Config.Name("Iron Wine Maximum Absorption")
		public float ironWineMaxAbsorption = 10.0F;

		@Config.Comment("Maximum duration of the nausea effect given by iron wine below quality 0.5")
		@Config.Name("Iron Wine Maximum Duration Nausea")
		public float ironWineMaxDurationNausea = 6000;

		@Config.Comment("Maximum damage given by iron wine below quality 0.5 (Plus 1)")
		@Config.Name("Iron Wine Maximum Damage")
		public float ironWineMaxDamage = 5.0F;

		@Config.Comment("Inebriation chance when drinking iron wine")
		@Config.Name("Iron Wine Inebriation Chance")
		public float ironWineInebriationChance = 0.5F;

		@Config.Comment("Effect that drinking Mead should give")
		@Config.Name("Mead Effect")
		public String meadEffect = "lycanitesmobs:rejuvenation";

		@Config.Comment("Maximum duration of the positive effect given by mead at quality 1.0")
		@Config.Name("Mead Maximum Duration Positive")
		public float meadMaxDurationPositive = 6000;

		@Config.Comment("Maximum duration of the wither effect given by mead below quality 0.5")
		@Config.Name("Mead Maximum Duration Wither")
		public float meadMaxDurationWither = 800;

		@Config.Comment("Maximum duration of the nausea effect given by mead below quality 0.5")
		@Config.Name("Mead Maximum Duration Nausea")
		public float meadMaxDurationNausea = 6000;

		@Config.Comment("Inebriation chance when drinking mead")
		@Config.Name("Mead Inebriation Chance")
		public float meadInebriationChance = 0.5F;

		@Config.Comment("Maximum amplifier of the positive effect increased by wildberry wine above quality 0.5")
		@Config.Name("Wildberry Wine Maximum Amplifier")
		public int wildberryWineMaxAmplifier = 2;

		@Config.Comment("Maximum amplifier increase of the positive effect increased by wildberry wine above quality 0.5")
		@Config.Name("Wildberry Wine Maximum Amplifier Increase")
		public int wildberryWineMaxAmplifierIncrease = 1;

		@Config.Comment("Amplifier decrease of positive effects by wildberry wine below quality 0.5")
		@Config.Name("Wildberry Wine Amplifier Decrease")
		public int wildberryWineAmplifierDecrease = 1;

		@Config.Comment("Maximum duration of the nausea effect given by wildberry wine below quality 0.5")
		@Config.Name("Wildberry Wine Maximum Duration Nausea")
		public float wildberryWineMaxDurationNausea = 6000;

		@Config.Comment("Inebriation chance when drinking wildberry wine")
		@Config.Name("Wildberry Wine Inebriation Chance")
		public float wildberryWineInebriationChance = 0.5F;

		@Config.Comment("Maximum duration increase of the positive effect increased by wine above quality 0.5")
		@Config.Name("Wine Maximum Duration Increase")
		public float wineMaximumDurationIncrease = 2400;

		@Config.Comment("Maximum duration of the positive effect increased by wine above quality 0.5")
		@Config.Name("Wine Maximum Duration")
		public float wineMaximumDuration = 12000;

		@Config.Comment("Maximum duration decrease of the positive effect decreased by wine below quality 0.5")
		@Config.Name("Wine Maximum Duration Decrease")
		public float wineMaximumDurationDecrease = 2400;

		@Config.Comment("Maximum duration of the nausea effect given by wine below quality 0.5")
		@Config.Name("Wine Maximum Duration Nausea")
		public float wineMaxDurationNausea = 6000;

		@Config.Comment("Inebriation chance when drinking wine")
		@Config.Name("Wine Inebriation Chance")
		public float wineInebriationChance = 0.5F;
		
		@Config.Comment("Chance per random tick for a corrupted block to attempt spreading, default mod chance is 1.0")
		@Config.Name("Defiled Corruption Chance")
		public float defiledCorruptionChance = 0.5F;
		
		@Config.Comment("Health multiplier for the Dregora Script Dragon")
		@Config.Name("Dregora Script Dragon Health Multiplier")
		public float dregoraScriptDragonHealth = 0.5F;
		
		@Config.Comment("Maximum amount of enchantment levels that bookwyrms can digest")
		@Config.Name("BookWyrm Maximum Level")
		public int bookWyrmMaxLevel = 30;

		@Config.Comment("Registers additional useful loot functions for json loot tables")
		@Config.Name("Register Additional Loot Functions")
		@Config.RequiresMcRestart
		public boolean registerAdditionalLootFunctions = true;
		
		@Config.Comment("Amount of seconds OTG's populate should wait to try to lock (Do not modify this if you do not know what you are doing.)")
		@Config.Name("OTG Populate Lock Time")
		public int otgPopulateLockTime = 10;
		
		@Config.Comment("Amount of seconds OTG's saveToDisk should wait to try to lock (Do not modify this if you do not know what you are doing.)")
		@Config.Name("OTG SaveToDisk Lock Time")
		public int otgSaveToDiskLockTime = 10;
		
		@Config.Comment("Items in this list will not be allowed to be worn as cosmetic armor")
		@Config.Name("Cosmetic Armor Item Blacklist")
		public String[] cosmeticArmorItemBlacklist = {};
		
		@Config.Comment("Cosmetic Armor Item Blacklist will be treated as a Whitelist")
		@Config.Name("Cosmetic Armor Item Whitelist Toggle")
		public boolean cosmeticArmorItemBlacklistIsWhitelist = false;
		
		@Config.Comment("List of dimension ids and the block (Format: id,blockid) to override as the default filler block")
		@Config.Name("Dimension Filler Block Override List")
		public String[] dimensionFillerBlockList = {};
		
		@Config.Comment("List of blocks to additionally allow caves and ravines to carve")
		@Config.Name("Cave and Ravine Carver Block List")
		public String[] caveRavineCarverList = {};
		
		@Config.Comment("Upper limit for bedrock to attempt to generate")
		@Config.Name("Bedrock Max Range")
		@Config.RangeInt(min = 1)
		public int bedrockMaxRange = 5;

		@Config.Comment("Kills added to SpawnerControl's mobThreshold before spawner breaks and cancels mob loot")
		@Config.Name("Spawner Control Grace Threshold")
		@Config.RangeInt(min = 0)
		public int spawnerControlGraceThreshold = 40;

		@Config.Comment("XP orbs will only keep merging until they have this amount of XP stored in them.")
		@Config.Name("XP Orb max XP value")
		public int orbMaxXpValue = 100;

		@Config.Comment("XP orbs will only start merging if they existed for at least this many ticks.")
		@Config.Name("XP Orb earliest merge tick")
		public int orbMergeEarliestTick = 100;

		@Config.Comment("Allows for overriding orbital period calculation result in the format String name, double value")
		@Config.Name("Advanced Rocketry Orbital Period Overrides")
		public String[] orbitalPeriodOverrides = {};
		
		@Config.Comment("Allows for multiplying the orbital period calculation result if not overriden first in the format String name, double multiplier")
		@Config.Name("Advanced Rocketry Orbital Period Multipliers")
		public String[] orbitalPeriodMults = {};
	}

	public static class ClientConfig {

		@Config.Comment("Minimum Gamma value for brightness")
		@Config.Name("Minimum Gamma Value")
		public float minGamma = 0.0f;

		@Config.Comment("Maximum Gamma value for brightness")
		@Config.Name("Maximum Gamma Value")
		public float maxGamma = 1.0f;

		@Config.Comment("List of particle classes to keep collision enabled for")
		@Config.Name("Particle Collision Classes")
		public String[] particleCollisionArray = {""};

		@Config.Comment("Amount of health a body part needs to have for FirstAid to display as numbers instead of hearts")
		@Config.Name("First Aid draw health as numbers Threshold")
		public int firstAidHealthThreshold = 16;
	}

	public static List<String> getNetherBaneMobs() {
		if(ForgeConfigHandler.netherBaneMobs == null) ForgeConfigHandler.netherBaneMobs = Arrays.asList(ForgeConfigHandler.server.netherBaneMobs);
		return ForgeConfigHandler.netherBaneMobs;
	}

	public static List<String> getNetherBaneWeapons() {
		if(ForgeConfigHandler.netherBaneWeapons == null) ForgeConfigHandler.netherBaneWeapons = Arrays.asList(ForgeConfigHandler.server.netherBaneWeapons);
		return ForgeConfigHandler.netherBaneWeapons;
	}

	public static List<String> getParticleCollisionClasses() {
		if(ForgeConfigHandler.particleCollisionClasses == null) ForgeConfigHandler.particleCollisionClasses = Arrays.asList(ForgeConfigHandler.client.particleCollisionArray);
		return ForgeConfigHandler.particleCollisionClasses;
	}

	public static HashSet<String> getMineshaftBiomeNameBlacklist() {
		if(ForgeConfigHandler.mineshaftBiomeNames == null) ForgeConfigHandler.mineshaftBiomeNames = new HashSet<>(Arrays.asList(ForgeConfigHandler.server.mineshaftBiomeNameBlacklist));
		return ForgeConfigHandler.mineshaftBiomeNames;
	}

	public static HashSet<BiomeDictionary.Type> getMineshaftBiomeTypesBlacklist() {
		if(ForgeConfigHandler.mineshaftBiomeTypes == null) {
			ForgeConfigHandler.mineshaftBiomeTypes = new HashSet<>();
			for(String string : ForgeConfigHandler.server.mineshaftBiomeTypeBlacklist) ForgeConfigHandler.mineshaftBiomeTypes.add(BiomeDictionary.Type.getType(string));
		}
		return ForgeConfigHandler.mineshaftBiomeTypes;
	}

	public static HashSet<Block> getDramaticTreeNonSolidList() {
		if(ForgeConfigHandler.dramaticTreeNonSolidList == null) {
			ForgeConfigHandler.dramaticTreeNonSolidList = new HashSet<>();
			for(String string : ForgeConfigHandler.server.dramaticTreesNonSolidBlocks) {
				Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(string));
				if(block == null || block == Blocks.AIR) {
					RLMixins.LOGGER.log(Level.WARN, "RLMixins DramaticTree Non-Solid list invalid block: " + string + ", ignoring.");
					continue;
				}
				ForgeConfigHandler.dramaticTreeNonSolidList.add(block);
			}
		}
		return ForgeConfigHandler.dramaticTreeNonSolidList;
	}

	public static HashSet<Block> getDramaticTreeNonSolidBreakableList() {
		if(ForgeConfigHandler.dramaticTreeNonSolidBreakableList == null) {
			ForgeConfigHandler.dramaticTreeNonSolidBreakableList = new HashSet<>();
			for(String string : ForgeConfigHandler.server.dramaticTreesNonSolidBreakableBlocks) {
				Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(string));
				if(block == null || block == Blocks.AIR) {
					RLMixins.LOGGER.log(Level.WARN, "RLMixins DramaticTree Non-Solid Breakable list invalid block: " + string + ", ignoring.");
					continue;
				}
				ForgeConfigHandler.dramaticTreeNonSolidBreakableList.add(block);
			}
		}
		return ForgeConfigHandler.dramaticTreeNonSolidBreakableList;
	}

	public static HashSet<Block> getDramaticTreeSolidBreakableList() {
		if(ForgeConfigHandler.dramaticTreeSolidBreakableList == null) {
			ForgeConfigHandler.dramaticTreeSolidBreakableList = new HashSet<>();
			for(String string : ForgeConfigHandler.server.dramaticTreesSolidBreakableBlocks) {
				Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(string));
				if(block == null || block == Blocks.AIR) {
					RLMixins.LOGGER.log(Level.WARN, "RLMixins DramaticTree Solid Breakable list invalid block: " + string + ", ignoring.");
					continue;
				}
				ForgeConfigHandler.dramaticTreeSolidBreakableList.add(block);
			}
		}
		return ForgeConfigHandler.dramaticTreeSolidBreakableList;
	}

	public static HashSet<Block> getReskillablePerfectRecoverList(){
		if(ForgeConfigHandler.reskillablePerfectRecoverList == null){
			ForgeConfigHandler.reskillablePerfectRecoverList = new HashSet<>();
			for(String string : ForgeConfigHandler.server.reskillablePerfectRecoverSilkBlocks){
				Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(string));
				if(block == null || block == Blocks.AIR){
					RLMixins.LOGGER.log(Level.WARN, "RLMixins Reskillable Perfect Recover Silk list invalid block: " + string + ", ignoring.");
					continue;
				}
				ForgeConfigHandler.reskillablePerfectRecoverList.add(block);
			}
		}
		return ForgeConfigHandler.reskillablePerfectRecoverList;
	}

	public static HashSet<ResourceLocation> getDregoraArrowAllowedEntities() {
		if(ForgeConfigHandler.dregoraArrowAllowedEntities == null) {
			HashSet<ResourceLocation> set = new HashSet<>();
			for(String entity : ForgeConfigHandler.server.dregoraTippedArrowEntities) {
				set.add(new ResourceLocation(entity));
			}
			ForgeConfigHandler.dregoraArrowAllowedEntities = set;
		}
		return ForgeConfigHandler.dregoraArrowAllowedEntities;
	}

	public static List<PotionType> getDregoraArrowAllowedPotionTypes() {
		if(ForgeConfigHandler.dregoraArrowAllowedPotionTypes == null) {
			List<PotionType> list = new ArrayList<>();
			for(String string : ForgeConfigHandler.server.dregoraTippedArrowPotionTypes) {
				PotionType type = PotionType.getPotionTypeForName(string);
				if(type == null) {
					RLMixins.LOGGER.log(Level.WARN, "RLMixins Dregora Arrow PotionTypes invalid PotionType: " + string + ", ignoring.");
					continue;
				}
				list.add(type);
			}
			ForgeConfigHandler.dregoraArrowAllowedPotionTypes = list;
		}
		return ForgeConfigHandler.dregoraArrowAllowedPotionTypes;
	}

	public static HashSet<ResourceLocation> getSilverImmunityBlacklistedPotionEffects() {
		if(ForgeConfigHandler.silverImmunityBlacklistedPotionEffects == null) {
			HashSet<ResourceLocation> list = new HashSet<>();
			for(String string : ForgeConfigHandler.server.silverBlackListedPotions) {
				list.add(new ResourceLocation(string));
			}
			ForgeConfigHandler.silverImmunityBlacklistedPotionEffects = list;
		}
		return ForgeConfigHandler.silverImmunityBlacklistedPotionEffects;
	}
	
	public static IBlockState getDimensionFillerBlock(int dimension) {
		if(dimensionBlockFillerMap == null) {
			dimensionBlockFillerMap = new HashMap<>();
			for(String entry : ForgeConfigHandler.server.dimensionFillerBlockList) {
				try {
					if(entry.isEmpty()) continue;
					String[] arr = entry.split(",");
					if(arr.length != 2) continue;
					int id = Integer.parseInt(arr[0].trim());
					String name = arr[1].trim();
					if(name.isEmpty()) continue;
					ResourceLocation loc = new ResourceLocation(name);
					Block block = ForgeRegistries.BLOCKS.getValue(loc);
					if(block == null) continue;
					
					dimensionBlockFillerMap.put(id, block.getDefaultState());
				}
				catch(Exception ignored) {}
			}
		}
		return dimensionBlockFillerMap.get(dimension);
	}
	
	public static boolean isBlockCarvable(Block blockIn) {
		if(caveRavineCarverSet == null) {
			caveRavineCarverSet = new HashSet<>();
			for(String entry : ForgeConfigHandler.server.caveRavineCarverList) {
				try {
					if(entry.isEmpty()) continue;
					String name = entry.trim();
					if(name.isEmpty()) continue;
					ResourceLocation loc = new ResourceLocation(name);
					Block block = ForgeRegistries.BLOCKS.getValue(loc);
					if(block == null) continue;
					
					caveRavineCarverSet.add(block);
				}
				catch(Exception ignored) {}
			}
		}
		return caveRavineCarverSet.contains(blockIn);
	}
	
	public static Double getOrbitalPeriodOverride(String nameIn) {
		if(orbitalPeriodOverrides == null) {
			orbitalPeriodOverrides = new HashMap<>();
			for(String entry : ForgeConfigHandler.server.orbitalPeriodOverrides) {
				try {
					if(entry.isEmpty()) continue;
					String[] arr = entry.split(",");
					if(arr.length != 2) continue;
					String name = arr[0].trim();
					if(name.isEmpty()) continue;
					double mult = Double.parseDouble(arr[1].trim());
					
					orbitalPeriodOverrides.put(name, mult);
				}
				catch(Exception ignored) {}
			}
		}
		return orbitalPeriodOverrides.get(nameIn);
	}
	
	public static Double getOrbitalPeriodMults(String nameIn) {
		if(orbitalPeriodMults == null) {
			orbitalPeriodMults = new HashMap<>();
			for(String entry : ForgeConfigHandler.server.orbitalPeriodMults) {
				try {
					if(entry.isEmpty()) continue;
					String[] arr = entry.split(",");
					if(arr.length != 2) continue;
					String name = arr[0].trim();
					if(name.isEmpty()) continue;
					double mult = Double.parseDouble(arr[1].trim());
					
					orbitalPeriodMults.put(name, mult);
				}
				catch(Exception ignored) {}
			}
		}
		return orbitalPeriodMults.get(nameIn);
	}

	@Mod.EventBusSubscriber(modid = RLMixins.MODID)
	private static class EventHandler{
		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(RLMixins.MODID)) {
				ForgeConfigHandler.netherBaneMobs = null;
				ForgeConfigHandler.netherBaneWeapons = null;
				ForgeConfigHandler.particleCollisionClasses = null;
				ForgeConfigHandler.mineshaftBiomeNames = null;
				ForgeConfigHandler.mineshaftBiomeTypes = null;
				ForgeConfigHandler.dramaticTreeNonSolidList = null;
				ForgeConfigHandler.dramaticTreeNonSolidBreakableList = null;
				ForgeConfigHandler.dramaticTreeSolidBreakableList = null;
				ForgeConfigHandler.reskillablePerfectRecoverList = null;
				ForgeConfigHandler.dregoraArrowAllowedEntities = null;
				ForgeConfigHandler.silverImmunityBlacklistedPotionEffects = null;
				ForgeConfigHandler.orbitalPeriodMults = null;
				ForgeConfigHandler.orbitalPeriodOverrides = null;
				ConfigManager.sync(RLMixins.MODID, Config.Type.INSTANCE);
				refreshValues();
			}
		}

		//Include remaining ISeeDragons patches
		private static void refreshValues() {
			if(ForgeConfigHandler.mixinConfig.allowModifyGamma) RLMixins.PROXY.setGamma(ForgeConfigHandler.client.minGamma, ForgeConfigHandler.client.maxGamma);
			if(ForgeConfigHandler.server.allowEntityViewDistanceOverride) {
				try {
					Field regField = EntityRegistry.instance().getClass().getDeclaredField("entityClassRegistrations");
					regField.setAccessible(true);
					BiMap<Class<? extends Entity>, EntityRegistry.EntityRegistration> reg = (BiMap<Class<? extends Entity>, EntityRegistry.EntityRegistration>)regField.get(EntityRegistry.instance());
					for(EntityRegistry.EntityRegistration entity : reg.values()) {
						Optional<Integer> boost = getRenderBoost(entity.getRegistryName());
						if(boost.isPresent()) {
							Field rangeField = entity.getClass().getDeclaredField("trackingRange");
							rangeField.setAccessible(true);
							rangeField.set(entity, boost.get());
						}
					}
				}
				catch(Exception ignored) { }
			}
		}

		private static Optional<Integer> getRenderBoost(ResourceLocation id) {
			if(id == null) return Optional.empty();
			return Optional.ofNullable(ForgeConfigHandler.server.entityViewDistanceList.get(id.toString()));
		}
	}

	//This is jank, but easier than setting up a whole custom GUI config
	private static File configFile = null;
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