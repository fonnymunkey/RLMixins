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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Config(modid = RLMixins.MODID)
public class ForgeConfigHandler {

	private static List<String> netherBaneMobs;
	private static List<String> netherBaneWeapons;
	
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

		@Config.Comment("Force Thorn and Arthropod enchantment methods to check for offhand packets, as well as Fire Aspect and Knockback with SME compat.")
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

		@Config.Comment("Replaces SRParasites Living and Sentient armor models with custom models")
		@Config.Name("Replace Parasite Armor Models (SRParasites)")
		@Config.RequiresMcRestart
		public boolean replaceParasiteArmorModel = false;

		@Config.Comment("Attempts to stop the ability to cheese dragons on the edge of render distance")
		@Config.Name("Enable AntiDragon Cheese (IceAndFire)")
		@Config.RequiresMcRestart
		public boolean enableInFDragonCheese = false;

		@Config.Comment("Makes dragons bite the player for the same amount as they bite other mobs, and heal on bites")
		@Config.Name("Enable Better Dragon Bites (IceAndFire)")
		@Config.RequiresMcRestart
		public boolean enableBetterDragonBites = false;

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

		@Config.Comment("Makes dragons start flying if they're stuck in water while they have a target")
		@Config.Name("Enable Dragon Water Flying (IceAndFire)")
		@Config.RequiresMcRestart
		public boolean enableDragonWaterFlight = false;

		@Config.Comment("Stops the player from being able to dismount dragons and cyclops while they are being shaken.")
		@Config.Name("Enable Dragon Dismount Fix (Vanilla/IceAndFire)")
		@Config.RequiresMcRestart
		public boolean enableDragonDismountFix = false;

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

		@Config.Comment("Fixes BetterNether's food bowls from deleting whole stacks when eaten.")
		@Config.Name("Stalagnate Bowl Fix (BetterNether)")
		@Config.RequiresMcRestart
		public boolean stalagnateBowlFix = false;

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

		@Config.Comment("Fixes some dupe bugs with stoned InF mobs")
		@Config.Name("Tamed Mob Stone Dupe (IceAndFire)")
		@Config.RequiresMcRestart
		public boolean tameStoneDupe = false;

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

		@Config.Comment("Rehandles the Sentient Scythe's AOE effect to make it less ridiculous and more compatible with other effects")
		@Config.Name("Rehandle Sentient Scythe Effect (SRParasites/RLCombat)")
		@Config.RequiresMcRestart
		public boolean rehandleSentientScythe = false;

		@Config.Comment("Makes Strays and Husks ignore whether or not they can see the sky when spawning")
		@Config.Name("Stray/Husk Sky Spawning Fix (Vanilla)")
		@Config.RequiresMcRestart
		public boolean strayHuskSpawningFix = false;

		@Config.Comment("Makes curing Zombie Villagers count as a kill for Mob Spawner Control spawners")
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

		@Config.Comment("Fixes multiple InF entities such as eggs and dragons randomly despawning")
		@Config.Name("InF Entity Despawn Patch (IceAndFire)")
		@Config.RequiresMcRestart
		public boolean infDespawn = false;

		@Config.Comment("Fixes dupe issues with the Locks keyring")
		@Config.Name("Locks Keyring GUI Dupe Patch (Locks)")
		@Config.RequiresMcRestart
		public boolean locksGuiDupe = false;

		@Config.Comment("Fixes dupe issues with the ToolBelt belt")
		@Config.Name("ToolBelt Belt GUI Dupe Patch (ToolBelt)")
		@Config.RequiresMcRestart
		public boolean toolbeltGuiDupe = false;

		@Config.Comment("Fixes dragons being breedable when still a child or not tamed")
		@Config.Name("Dragon OverBreeding Patch (IceAndFire)")
		@Config.RequiresMcRestart
		public boolean dragonBreedingPatch = false;

		@Config.Comment("Enabled the \"Spooky Dragons\" config option in client config")
		@Config.Name("Spooky Dragons Mixin (IceAndFire)")
		@Config.RequiresMcRestart
		public boolean spookyDragonsMixin = false;

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

		@Config.Comment("Fix ForgottenItems vein pickaxe mining tile entities and bypassing protection")
		@Config.Name("Vein Pickaxe Patch (ForgottenItems)")
		@Config.RequiresMcRestart
		public boolean veinPickaxePatch = false;

		@Config.Comment("Fix Hippogrpyhs being breedable with wheat")
		@Config.Name("Hippogrpyh Wheat Breeding Fix (IceAndFire)")
		@Config.RequiresMcRestart
		public boolean hippogrpyhWheatBreeding = false;

		@Config.Comment("Fix Reskillable cancelling indirect self damage")
		@Config.Name("Reskillable Indirect Self Damage Patch (Reskillable)")
		@Config.RequiresMcRestart
		public boolean reskillableIndirectSelfDamage = false;

		@Config.Comment("Replace the effect from the Scarlite Sword with a config-able effect")
		@Config.Name("Scarlite Sword Config Effect (DefiledLands)")
		@Config.RequiresMcRestart
		public boolean scarliteSwordConfigEffect = false;

		@Config.Comment("Fixes abusable Myrmex trades resulting in duping and infinite xp")
		@Config.Name("Myrmex Abusable Trade Dupe (IceAndFire)")
		@Config.RequiresMcRestart
		public boolean myrmexTradeDupe = false;

		@Config.Comment("Fixes abusable Gorgon head using offhand swapping")
		@Config.Name("Gorgon Head Offhand Abuse Patch (IceAndFire)")
		@Config.RequiresMcRestart
		public boolean gorgonHeadOffhandAbuse = false;

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

		@Config.Comment("Modifies the world selection/creation buttons to clarify creating a Vanilla world or a Dregora world")
		@Config.Name("OTG Create World Simplify Fix (OTG)")
		@Config.RequiresMcRestart
		public boolean otgCreateWorldFix = false;
	}

	public static class ServerConfig {
		@Config.Comment("Item Blacklist for the Hungry Farmer trait.")
		@Config.Name("Hungry Farmer Blacklist")
		public String[] hungryFarmerBlacklist = {""};

		@Config.Comment("Potion Blacklist for Tipped Arrows.")
		@Config.Name("Tipped Arrow Blacklist")
		public String[] tippedArrowBlacklist = {""};

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

		@Config.Comment("Add and register Steel armor with custom models")
		@Config.Name("Register Steel Armor")
		@Config.RequiresMcRestart
		public boolean registerSteelArmor = false;

		@Config.Comment("Add and register Scarlite armor with custom models")
		@Config.Name("Register Scarlite Armor")
		@Config.RequiresMcRestart
		public boolean registerScarliteArmor = false;

		@Config.Comment("Add and register Cleansing Talisman, a recipe for crafting a Curse Break book, and the Curse Break potion")
		@Config.Name("Register Cleansing Talisman (Charm)")
		@Config.RequiresMcRestart
		public boolean registerCleansingTalisman = false;

		@Config.Comment("Register the Lesser Fire Resistance potion effect")
		@Config.Name("Register Lesser Fire Resistance")
		@Config.RequiresMcRestart
		public boolean registerLesserFireResistance = false;

		@Config.Comment("Enables the Nether Bane weapon effect to deal bonus damage to nether mobs")
		@Config.Name("Enable Nether Bane (Requires RLCombat)")
		@Config.RequiresMcRestart
		public boolean enableNetherBane = false;

		@Config.Comment("List of mobs to be classed as nether-mobs for the Nether Bane effect")
		@Config.Name("Nether Bane Mob List")
		public String[] netherBaneMobs = { "minecraft:wither_skeleton", "minecraft:zombie_pigman", "minecraft:blaze", "minecraft:magma_cube", "minecraft:wither" };

		@Config.Comment("List of weapons to have the Nether Bane effect")
		@Config.Name("Nether Bane Weapon List")
		public String[] netherBaneWeapons = { "" };

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

		@Config.Comment("Makes Advanced Mending incompatible with Infinity (Requires SME Overhaul Mixin)")
		@Config.Name("Advanced Mending Infinity Restriction")
		@Config.RequiresMcRestart
		public boolean advMendingInfinityRestrict = true;

		@Config.Comment("Makes Advanced Mending prioritize damaged items first (Requires SME Overhaul Mixin)")
		@Config.Name("Advanced Mending Damaged Priority")
		@Config.RequiresMcRestart
		public boolean advMendingDamagedPriority = true;

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
	}

	public static class ClientConfig {

		@Config.Comment("Requires Spooky Dragons Mixin to be enabled")
		@Config.Name("Spooky Dragons")
		public boolean spookyDragons = false;
	}

	public static List<String> getNetherBaneMobs() {
		if(ForgeConfigHandler.netherBaneMobs == null) ForgeConfigHandler.netherBaneMobs = Arrays.asList(ForgeConfigHandler.server.netherBaneMobs);
		return ForgeConfigHandler.netherBaneMobs;
	}

	public static List<String> getNetherBaneWeapons() {
		if(ForgeConfigHandler.netherBaneWeapons == null) ForgeConfigHandler.netherBaneWeapons = Arrays.asList(ForgeConfigHandler.server.netherBaneWeapons);
		return ForgeConfigHandler.netherBaneWeapons;
	}

	@Mod.EventBusSubscriber(modid = RLMixins.MODID)
	private static class EventHandler{
		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(RLMixins.MODID)) {
				ForgeConfigHandler.netherBaneMobs = null;
				ForgeConfigHandler.netherBaneWeapons = null;
				ConfigManager.sync(RLMixins.MODID, Config.Type.INSTANCE);
			}
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