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
import rlmixins.potion.PotionCurseBreak;
import rlmixins.potion.PotionDelayedLaunch;
import rlmixins.potion.PotionEncumbered;
import rlmixins.item.ItemScarliteArmor;
import rlmixins.item.ItemSteelArmor;
import rlmixins.potion.PotionCow;
import rlmixins.potion.PotionLesserFireResistance;
import rlmixins.recipe.RecipeAntimagic;
import rlmixins.recipe.RecipeCurseBreak;
import rlmixins.util.ModLoadedUtil;

@Mod.EventBusSubscriber(modid = RLMixins.MODID)
public class RegistryHandler {
	
	public static ItemArmor.ArmorMaterial STEEL_ARMOR = EnumHelper.addArmorMaterial("steel_armor", RLMixins.MODID + ":steel_armor", 26, new int[]{3, 5, 7, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
	public static ItemArmor.ArmorMaterial SCARLITE_ARMOR = EnumHelper.addArmorMaterial("scarlite_armor", RLMixins.MODID + ":scarlite_armor", 36, new int[]{3,6,8,3}, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
	
	public static Item steelHelmet = new ItemSteelArmor("steel_helmet", STEEL_ARMOR, 2, EntityEquipmentSlot.HEAD);
	public static Item steelChestplate = new ItemSteelArmor("steel_chestplate", STEEL_ARMOR, 1, EntityEquipmentSlot.CHEST);
	public static Item steelLeggings = new ItemSteelArmor("steel_leggings", STEEL_ARMOR, 2, EntityEquipmentSlot.LEGS);
	public static Item steelBoots = new ItemSteelArmor("steel_boots", STEEL_ARMOR, 1, EntityEquipmentSlot.FEET);
	
	public static Item scarliteHelmet = new ItemScarliteArmor("scarlite_helmet", SCARLITE_ARMOR, 2, EntityEquipmentSlot.HEAD);
	public static Item scarliteChestplate = new ItemScarliteArmor("scarlite_chestplate", SCARLITE_ARMOR, 1, EntityEquipmentSlot.CHEST);
	public static Item scarliteLeggings = new ItemScarliteArmor("scarlite_leggings", SCARLITE_ARMOR, 2, EntityEquipmentSlot.LEGS);
	public static Item scarliteBoots = new ItemScarliteArmor("scarlite_boots", SCARLITE_ARMOR, 1, EntityEquipmentSlot.FEET);
	
	public static Item antimagicTalisman = (new Item()).setRegistryName(RLMixins.MODID, "antimagic_talisman").setTranslationKey("antimagic_talisman").setCreativeTab(CreativeTabs.MATERIALS);
	public static Item cleansingTalisman = (new Item()).setRegistryName(RLMixins.MODID, "cleansing_talisman").setTranslationKey("cleansing_talisman").setCreativeTab(CreativeTabs.MATERIALS);
	
	public static PotionType curseBreakPotion = new PotionType("curse_break", new PotionEffect(PotionCurseBreak.INSTANCE)).setRegistryName(new ResourceLocation(RLMixins.MODID, "curse_break"));
	
	public static SoundEvent COW = new SoundEvent(new ResourceLocation(RLMixins.MODID, "cow")).setRegistryName("cow");
	
	@SubscribeEvent
	public static void registerItemEvent(RegistryEvent.Register<Item> event) {
		if(ConfigHandler.RLMIXINS_CONFIG.steelArmor) {
			event.getRegistry().registerAll(
					steelHelmet,
					steelChestplate,
					steelLeggings,
					steelBoots);
		}
		if(ConfigHandler.RLMIXINS_CONFIG.scarliteArmor) {
			event.getRegistry().registerAll(
					scarliteHelmet,
					scarliteChestplate,
					scarliteLeggings,
					scarliteBoots);
		}
		if(ConfigHandler.RLMIXINS_CONFIG.antimagicTalisman) {
			event.getRegistry().register(antimagicTalisman);
		}
		if(ConfigHandler.CHARM_CONFIG.cleansingTalisman && ModLoadedUtil.isCharmLoaded()) {
			event.getRegistry().register(cleansingTalisman);
		}
	}
	
	@SubscribeEvent
	public static void registerRecipeEvent(RegistryEvent.Register<IRecipe> event) {
		if(ConfigHandler.CHARM_CONFIG.cleansingTalisman && ModLoadedUtil.isCharmLoaded()) {
			event.getRegistry().register(new RecipeCurseBreak().setRegistryName(new ResourceLocation(RLMixins.MODID, "cursebreak")));
		}
		if(ConfigHandler.RLMIXINS_CONFIG.antimagicTalisman) {
			event.getRegistry().register(new RecipeAntimagic().setRegistryName(new ResourceLocation(RLMixins.MODID, "antimagic")));
		}
	}
	
	@SubscribeEvent
	public static void registerPotionEvent(RegistryEvent.Register<Potion> event) {
		if(ConfigHandler.CHARM_CONFIG.cleansingTalisman && ModLoadedUtil.isCharmLoaded()) {
			event.getRegistry().register(PotionCurseBreak.INSTANCE);
		}
		if(ConfigHandler.POTIONCORE_CONFIG.delayedLaunch && ModLoadedUtil.isPotionCoreLoaded()) {
			event.getRegistry().register(PotionDelayedLaunch.INSTANCE);
		}
		if(ConfigHandler.POTIONCORE_CONFIG.encumberedPotionEffect && ModLoadedUtil.isPotionCoreLoaded()) {
			event.getRegistry().register(PotionEncumbered.INSTANCE);
		}
		if(ConfigHandler.RLMIXINS_CONFIG.lesserFireResistancePotionEffect) {
			event.getRegistry().register(PotionLesserFireResistance.INSTANCE);
		}
		if(ConfigHandler.RLMIXINS_CONFIG.cowPotionEffect) {
			event.getRegistry().register(PotionCow.INSTANCE);
		}
	}
	
	@SubscribeEvent
	public static void registerPotionTypeEvent(RegistryEvent.Register<PotionType> event) {
		if(ConfigHandler.CHARM_CONFIG.cleansingTalisman && ModLoadedUtil.isCharmLoaded()) {
			event.getRegistry().register(curseBreakPotion);
			PotionHelper.addMix(PotionTypes.THICK, RegistryHandler.cleansingTalisman, RegistryHandler.curseBreakPotion);
		}
	}
	
	@SubscribeEvent
	public static void registerSoundEvent(RegistryEvent.Register<SoundEvent> event) {
		if(ConfigHandler.RLMIXINS_CONFIG.cowPotionEffect) {
			event.getRegistry().register(COW);
		}
	}
}