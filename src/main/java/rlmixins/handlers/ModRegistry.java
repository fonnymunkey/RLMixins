package rlmixins.handlers;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.RLMixins;
import rlmixins.item.ItemScarliteArmor;
import rlmixins.item.ItemSteelArmor;
import rlmixins.potion.PotionDelayedLaunch;
import rlmixins.potion.PotionEncumbered;

@Mod.EventBusSubscriber(modid = RLMixins.MODID)
public class ModRegistry {

        public static ItemArmor.ArmorMaterial STEEL_ARMOR = EnumHelper.addArmorMaterial("steel_armor", RLMixins.MODID + ":steel_armor", 26, new int[]{3,5,7,3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
        public static ItemArmor.ArmorMaterial SCARLITE_ARMOR = EnumHelper.addArmorMaterial("scarlite_armor", RLMixins.MODID + ":scarlite_armor", 36, new int[]{3,6,8,3}, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);

        public static Item steelHelmet = new ItemSteelArmor("steel_helmet", STEEL_ARMOR, 2, EntityEquipmentSlot.HEAD);
        public static Item steelChestplate = new ItemSteelArmor("steel_chestplate", STEEL_ARMOR, 1, EntityEquipmentSlot.CHEST);
        public static Item steelLeggings = new ItemSteelArmor("steel_leggings", STEEL_ARMOR, 2, EntityEquipmentSlot.LEGS);
        public static Item steelBoots = new ItemSteelArmor("steel_boots", STEEL_ARMOR, 1, EntityEquipmentSlot.FEET);
        public static Item scarliteHelmet = new ItemScarliteArmor("scarlite_helmet", SCARLITE_ARMOR, 2, EntityEquipmentSlot.HEAD);
        public static Item scarliteChestplate = new ItemScarliteArmor("scarlite_chestplate", SCARLITE_ARMOR, 1, EntityEquipmentSlot.CHEST);
        public static Item scarliteLeggings = new ItemScarliteArmor("scarlite_leggings", SCARLITE_ARMOR, 2, EntityEquipmentSlot.LEGS);
        public static Item scarliteBoots = new ItemScarliteArmor("scarlite_boots", SCARLITE_ARMOR, 1, EntityEquipmentSlot.FEET);

        public static SoundEvent FLARE_SHOOT;
        public static SoundEvent FLARE_BURN;
        public static SoundEvent CRITICAL_STRIKE;
        public static SoundEvent ATOMIC_DECONSTRUCT;

        public static void init() {
                FLARE_SHOOT = new SoundEvent(new ResourceLocation(RLMixins.MODID, "flare_shoot")).setRegistryName("flare_shoot");
                FLARE_BURN = new SoundEvent(new ResourceLocation(RLMixins.MODID, "flare_burn")).setRegistryName("flare_burn");
                CRITICAL_STRIKE = new SoundEvent(new ResourceLocation(RLMixins.MODID, "critical_strike")).setRegistryName("critical_strike");
                ATOMIC_DECONSTRUCT = new SoundEvent(new ResourceLocation(RLMixins.MODID, "atomic_deconstruct")).setRegistryName("atomic_deconstruct");
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
        }

        @SubscribeEvent
        public static void registerSoundEvent(RegistryEvent.Register<SoundEvent> event) {
                event.getRegistry().register(FLARE_SHOOT);
                event.getRegistry().register(FLARE_BURN);
                event.getRegistry().register(CRITICAL_STRIKE);
                event.getRegistry().register(ATOMIC_DECONSTRUCT);
        }

        @SubscribeEvent
        public static void registerPotionEvent(RegistryEvent.Register<Potion> event) {
                if(ForgeConfigHandler.server.registerEncumbered) event.getRegistry().register(PotionEncumbered.INSTANCE);
                if(ForgeConfigHandler.mixinConfig.delayedLaunch) event.getRegistry().register(PotionDelayedLaunch.INSTANCE);
        }
}