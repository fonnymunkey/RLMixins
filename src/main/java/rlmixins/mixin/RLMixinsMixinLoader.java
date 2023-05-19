package rlmixins.mixin;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModClassLoader;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import rlmixins.RLMixins;
import rlmixins.handlers.ForgeConfigHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads non-vanilla and non-coremod mixins late in order to prevent ClassNotFound exceptions
 * Code based on original MIT Licensed code:
 * https://github.com/DimensionalDevelopment/JustEnoughIDs/blob/master/src/main/java/org/dimdev/jeid/mixin/init/JEIDMixinLoader.java
 */
@Mixin(Loader.class)
public class RLMixinsMixinLoader {

    private static final Map<String, String> configMap = setupMap();

    private static Map<String, String> setupMap() {
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
        map.put("IceAndFire Render Box (IceAndFire)", "mixins.rlmixins.infrender.json");
        map.put("InF Multipart Handling (IceAndFire)", "mixins.rlmixins.infmultipart.json");
        map.put("Explosion Particle Reduction (IceAndFire)", "mixins.rlmixins.explosionparticle.json");
        map.put("InF Bonus Handling (IceAndFire)", "mixins.rlmixins.infbonus.json");
        map.put("InF Offhand Items (IceAndFire)", "mixins.rlmixins.infoffhand.json");
        map.put("Food Stack Size (FoodExpansion)", "mixins.rlmixins.foodstack.json");
        map.put("Nether Wart Soup Crash (FoodExpansion)", "mixins.rlmixins.wartsoup.json");
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
        map.put("Enable Better Dragon Bites (IceAndFire)", "mixins.rlmixins.infdragonbite.json");
        map.put("Cancel Parasite Reach Packet (SRParasites)", "mixins.rlmixins.srppacket.json");
        map.put("Champion Death Message Tweak (Champions)", "mixins.rlmixins.championname.json");
        map.put("Champion Potion Invis (Champions)", "mixins.rlmixins.championpotion.json");
        map.put("Enable Dragon Water Flying (IceAndFire)", "mixins.rlmixins.infdragonwater.json");
        map.put("Prevent Revival Potion on Non-Players (PotionCore)", "mixins.rlmixins.potionrevival.json");
        map.put("Parasite Spawn Fix (SRParasites)", "mixins.rlmixins.srpspawning.json");
        map.put("Parasite Light Level (SRParasites)", "mixins.rlmixins.srplightlevel.json");
        map.put("Stalagnate Bowl Fix (BetterNether)", "mixins.rlmixins.stalagnatebowl.json");
        map.put("Cobalt Shield Increased Resistance (BountifulBaubles)", "mixins.rlmixins.cobaltshield.json");
        map.put("Stoned Chicken Feather Fix (Quark/IceAndFire)", "mixins.rlmixins.chickenfeather.json");
        map.put("Tamed Mob Stone Dupe (IceAndFire)", "mixins.rlmixins.inftamedupe.json");
        map.put("Education Tweak (BetterSurvival)", "mixins.rlmixins.educationtweak.json");
        map.put("Remove Shield Protection Enchant (Inspirations)", "mixins.rlmixins.inspirationsshield.json");
        map.put("ScalingHealth Death Desync (ScalingHealth)", "mixins.rlmixins.scalinghealthdesync.json");
        map.put("Sync Sign Edit Config (Quark)", "mixins.rlmixins.quarksignedit.json");
        map.put("Rehandle Sentient Scythe Effect (SRParasites/RLCombat)", "mixins.rlmixins.sentientscythe.json");
        map.put("InF Bow Multishot patch (IceAndFire/BetterSurvival)", "mixins.rlmixins.dragonbow.json");
        map.put("Changeable Nunchaku Combo (BetterSurvival)", "mixins.rlmixins.nunchakucombo.json");
        map.put("Quark Chest Boat Dupe (Quark)", "mixins.rlmixins.chestboatdupe.json");
        map.put("Vampirism Cheese Patch (BetterSurvival)", "mixins.rlmixins.bsvampirism.json");
        map.put("InF Entity Despawn Patch (IceAndFire)", "mixins.rlmixins.infdespawn.json");
        map.put("Locks Keyring GUI Dupe Patch (Locks)", "mixins.rlmixins.locksguidupe.json");
        map.put("ToolBelt Belt GUI Dupe Patch (ToolBelt)", "mixins.rlmixins.toolbeltguidupe.json");
        map.put("Dragon OverBreeding Patch (IceAndFire)", "mixins.rlmixins.infbreedingpatch.json");
        map.put("Spooky Dragons Mixin (IceAndFire)", "mixins.rlmixins.spookydragons.json");
        map.put("Rain Collector Canteen Fix (SimpleDifficulty)", "mixins.rlmixins.raincollector.json");
        map.put("Clay Tool Enchant Patch (NoTreePunching)", "mixins.rlmixins.claytoolenchanting.json");
        map.put("Mattock Breaking Patch (NoTreePunching)", "mixins.rlmixins.mattockbreaking.json");
        map.put("Switchbow Quiver Patch (Switchbow)", "mixins.rlmixins.switchbowquiver.json");
        map.put("Vein Pickaxe Patch (ForgottenItems)", "mixins.rlmixins.veinpickaxe.json");
        map.put("Hippogrpyh Wheat Breeding Fix (IceAndFire)", "mixins.rlmixins.hippogryphwheat.json");
        map.put("Reskillable Indirect Self Damage Patch (Reskillable)", "mixins.rlmixins.reskillableindirect.json");
        map.put("Scarlite Sword Config Effect (DefiledLands)", "mixins.rlmixins.scarlitesword.json");
        map.put("Myrmex Abusable Trade Dupe (IceAndFire)", "mixins.rlmixins.myrmextradedupe.json");
        map.put("Gorgon Head Offhand Abuse Patch (IceAndFire)", "mixins.rlmixins.gorgonheadabuse.json");
        map.put("Disenchanting Table Crash Patch (Disenchanter)", "mixins.rlmixins.disenchantcrash.json");
        map.put("Quark Chat Link NBT Crash (Quark)", "mixins.rlmixins.chatlinkcrash.json");
        map.put("Potion Amplifier Visibility (DSHuds)", "mixins.rlmixins.dshudpotion.json");
        map.put("Parasite Cleaver Effect Config (SRParasites)", "mixins.rlmixins.cleaverpotion.json");
        map.put("Better Foliage Chunk XRay (BetterFoliage/ChunkAnimator)", "mixins.rlmixins.betterfoliagechunkanimator.json");
        map.put("CarryOn Ungenerated Chest Patch (CarryOn)", "mixins.rlmixins.carryonunlooted.json");
        map.put("CarryOn Pig Saddle Patch (CarryOn)", "mixins.rlmixins.carryonpig.json");
        map.put("CorpseComplex XP Death Fix (CorpseComplex)", "mixins.rlmixins.corpsecomplexreturn.json");
        map.put("Magnetic Dupe Patch (Charm)", "mixins.rlmixins.magneticdupe.json");
        map.put("Penetration Fix (BetterSurvival/SpartanWeaponry/RLCombat)", "mixins.rlmixins.bspenetration.json");

        map.put("Flowering Oak DT Fix (DynamicTrees/BOP/DTBOP)", "mixins.rlmixins.floweringoakleaves.json");
        map.put("OTG Create World Simplify Fix (OTG)", "mixins.rlmixins.otgguibutton.json");

        map.put("ChunkAnimator XRay (Vanilla/ChunkAnimator)", "mixins.rlmixins.chunkanimator.json");

        return Collections.unmodifiableMap(map);
    }

    @Shadow(remap = false)
    private List<ModContainer> mods;
    @Shadow(remap = false)
    private ModClassLoader modClassLoader;

    /**
     * @reason Load all mods now and load mod support mixin configs. This can't be done later
     * since constructing mods loads classes from them.
     */
    @Inject(method = "loadMods", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/LoadController;transition(Lnet/minecraftforge/fml/common/LoaderState;Z)V", ordinal = 1), remap = false)
    private void beforeConstructingMods(List<String> nonMod, CallbackInfo ci) {
        // Add all mods to class loader
        for (ModContainer mod : mods) {
            try {
                modClassLoader.addFile(mod.getSource());
            } catch (MalformedURLException e) {

                throw new RuntimeException(e);
            }
        }

        // Add and reload mixin configs
        for (Map.Entry<String, String> entry : configMap.entrySet()) {
            if (ForgeConfigHandler.getBoolean(entry.getKey())) {
                RLMixins.LOGGER.log(Level.INFO, "RLMixins Late Loading: " + entry.getKey());
                Mixins.addConfiguration(entry.getValue());
            }
        }

        try {
            // This will very likely break on the next major mixin release.
            Class<?> proxyClass = Class.forName("org.spongepowered.asm.mixin.transformer.Proxy");
            Field transformerField = proxyClass.getDeclaredField("transformer");
            transformerField.setAccessible(true);
            Object transformer = transformerField.get(null);

            Class<?> mixinTransformerClass = Class.forName("org.spongepowered.asm.mixin.transformer.MixinTransformer");
            Field processorField = mixinTransformerClass.getDeclaredField("processor");
            processorField.setAccessible(true);
            Object processor = processorField.get(transformer);

            Class<?> mixinProcessorClass = Class.forName("org.spongepowered.asm.mixin.transformer.MixinProcessor");

            Field extensionsField = mixinProcessorClass.getDeclaredField("extensions");
            extensionsField.setAccessible(true);
            Object extensions = extensionsField.get(processor);

            Method selectConfigsMethod = mixinProcessorClass.getDeclaredMethod("selectConfigs", MixinEnvironment.class);
            selectConfigsMethod.setAccessible(true);
            selectConfigsMethod.invoke(processor, MixinEnvironment.getCurrentEnvironment());

            // Mixin 0.8.4+
            try {
                Method prepareConfigs = mixinProcessorClass.getDeclaredMethod("prepareConfigs", MixinEnvironment.class, Extensions.class);
                prepareConfigs.setAccessible(true);
                prepareConfigs.invoke(processor, MixinEnvironment.getCurrentEnvironment(), extensions);
                return;
            } catch (NoSuchMethodException ex) {
                // no-op
            }

            // Mixin 0.8+
            try {
                Method prepareConfigs = mixinProcessorClass.getDeclaredMethod("prepareConfigs", MixinEnvironment.class);
                prepareConfigs.setAccessible(true);
                prepareConfigs.invoke(processor, MixinEnvironment.getCurrentEnvironment());
                return;
            } catch (NoSuchMethodException ex) {
                // no-op
            }

            throw new UnsupportedOperationException("Unsupported Mixin");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}