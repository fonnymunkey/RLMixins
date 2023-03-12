package rlmixins.handlers;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.RLMixins;
import rlmixins.item.ItemScarliteArmor;
import rlmixins.item.ItemSteelArmor;
import rlmixins.potion.*;
import rlmixins.recipe.RecipeCurseBreak;
import rlmixins.recipe.RecipeFlameIceWeapon;

@Mod.EventBusSubscriber(modid = RLMixins.MODID)
public class ModRegistry {

        public static ItemArmor.ArmorMaterial STEEL_ARMOR = EnumHelper.addArmorMaterial("steel_armor", RLMixins.MODID + ":steel_armor", 26, new int[]{3,5,7,3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
        public static ItemArmor.ArmorMaterial SCARLITE_ARMOR = EnumHelper.addArmorMaterial("scarlite_armor", RLMixins.MODID + ":scarlite_armor", 36, new int[]{3,6,8,3}, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);

        public static Item steelHelmet = new ItemSteelArmor("steel_helmet", STEEL_ARMOR, 2, EntityEquipmentSlot.HEAD);
        public static Item steelChestplate = new ItemSteelArmor("steel_chestplate", STEEL_ARMOR, 1, EntityEquipmentSlot.CHEST);
        public static Item steelLeggings = new ItemSteelArmor("steel_leggings", STEEL_ARMOR, 2, EntityEquipmentSlot.LEGS);
        public static Item steelBoots = new ItemSteelArmor("steel_boots", STEEL_ARMOR, 1, EntityEquipmentSlot.FEET);
        public static Item scarliteHelmet = new ItemScarliteArmor("scarlite_helmet", SCARLITE_ARMOR, 2, EntityEquipmentSlot.HEAD);
        public static Item scarliteChestplate = new ItemScarliteArmor("scarlite_chestplate", SCARLITE_ARMOR, 1, EntityEquipmentSlot.CHEST);
        public static Item scarliteLeggings = new ItemScarliteArmor("scarlite_leggings", SCARLITE_ARMOR, 2, EntityEquipmentSlot.LEGS);
        public static Item scarliteBoots = new ItemScarliteArmor("scarlite_boots", SCARLITE_ARMOR, 1, EntityEquipmentSlot.FEET);

        public static Item cleansingTalisman = (new Item()).setRegistryName(RLMixins.MODID, "cleansing_talisman").setTranslationKey("cleansing_talisman").setCreativeTab(CreativeTabs.MATERIALS);

        public static PotionType curseBreakPotion = new PotionType("curse_break", new PotionEffect(PotionCurseBreak.INSTANCE)).setRegistryName(new ResourceLocation(RLMixins.MODID, "curse_break"));

        public static SoundEvent FLARE_SHOOT;
        public static SoundEvent FLARE_BURN;
        public static SoundEvent CRITICAL_STRIKE;
        public static SoundEvent ATOMIC_DECONSTRUCT;
        public static SoundEvent PANDORA_REMOVAL;
        public static SoundEvent COW;

        public static void init() {
                FLARE_SHOOT = new SoundEvent(new ResourceLocation(RLMixins.MODID, "flare_shoot")).setRegistryName("flare_shoot");
                FLARE_BURN = new SoundEvent(new ResourceLocation(RLMixins.MODID, "flare_burn")).setRegistryName("flare_burn");
                CRITICAL_STRIKE = new SoundEvent(new ResourceLocation(RLMixins.MODID, "critical_strike")).setRegistryName("critical_strike");
                ATOMIC_DECONSTRUCT = new SoundEvent(new ResourceLocation(RLMixins.MODID, "atomic_deconstruct")).setRegistryName("atomic_deconstruct");
                PANDORA_REMOVAL = new SoundEvent(new ResourceLocation(RLMixins.MODID, "pandora_removal")).setRegistryName("pandora_removal");
                COW = new SoundEvent(new ResourceLocation(RLMixins.MODID, "cow")).setRegistryName("cow");
        }

        @SubscribeEvent
        public static void registerItemEvent(RegistryEvent.Register<Item> event) {
                if(ForgeConfigHandler.server.registerSteelArmor) event.getRegistry().registerAll(
                        steelHelmet,
                        steelChestplate,
                        steelLeggings,
                        steelBoots);
                if(ForgeConfigHandler.server.registerScarliteArmor) event.getRegistry().registerAll(
                        scarliteHelmet,
                        scarliteChestplate,
                        scarliteLeggings,
                        scarliteBoots);
                if(ForgeConfigHandler.server.registerCleansingTalisman) event.getRegistry().register(
                        cleansingTalisman
                );
        }

        @SubscribeEvent
        public static void registerRecipeEvent(RegistryEvent.Register<IRecipe> event) {
                if(ForgeConfigHandler.server.registerCleansingTalisman) event.getRegistry().register(new RecipeCurseBreak().setRegistryName(new ResourceLocation(RLMixins.MODID, "cursebreak")));
                if(ForgeConfigHandler.server.registerEnchantmentSensitiveFlameIceWeapon) event.getRegistry().register(new RecipeFlameIceWeapon().setRegistryName(new ResourceLocation(RLMixins.MODID, "flameiceweapon")));
        }

        @SubscribeEvent
        public static void registerSoundEvent(RegistryEvent.Register<SoundEvent> event) {
                event.getRegistry().register(FLARE_SHOOT);
                event.getRegistry().register(FLARE_BURN);
                event.getRegistry().register(CRITICAL_STRIKE);
                event.getRegistry().register(ATOMIC_DECONSTRUCT);
                event.getRegistry().register(PANDORA_REMOVAL);
                event.getRegistry().register(COW);
        }

        @SubscribeEvent
        public static void registerPotionEvent(RegistryEvent.Register<Potion> event) {
                if(ForgeConfigHandler.server.registerEncumbered) event.getRegistry().register(PotionEncumbered.INSTANCE);
                if(ForgeConfigHandler.mixinConfig.delayedLaunch) event.getRegistry().register(PotionDelayedLaunch.INSTANCE);
                if(ForgeConfigHandler.server.registerLesserFireResistance) event.getRegistry().register(PotionLesserFireResistance.INSTANCE);
                if(ForgeConfigHandler.server.registerCleansingTalisman) event.getRegistry().register(PotionCurseBreak.INSTANCE);
                if(ForgeConfigHandler.server.registerCowPotion) event.getRegistry().register(PotionCow.INSTANCE);
        }

        @SubscribeEvent
        public static void registerPotionTypeEvent(RegistryEvent.Register<PotionType> event) {
                if(ForgeConfigHandler.server.registerCleansingTalisman) {
                        event.getRegistry().register(curseBreakPotion);
                        PotionHelper.addMix(PotionTypes.THICK, ModRegistry.cleansingTalisman, ModRegistry.curseBreakPotion);
                }
        }
}