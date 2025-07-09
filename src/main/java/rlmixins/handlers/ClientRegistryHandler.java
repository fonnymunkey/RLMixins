package rlmixins.handlers;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import rlmixins.RLMixins;

@Mod.EventBusSubscriber(modid = RLMixins.MODID, value = Side.CLIENT)
public class ClientRegistryHandler {
	
	@SubscribeEvent
	public static void modelRegisterEvent(ModelRegistryEvent event) {
		registerModels(RegistryHandler.scarliteHelmet, RegistryHandler.scarliteChestplate, RegistryHandler.scarliteLeggings, RegistryHandler.scarliteBoots);
		registerModels(RegistryHandler.steelHelmet, RegistryHandler.steelChestplate, RegistryHandler.steelLeggings, RegistryHandler.steelBoots);
		registerModels(RegistryHandler.cleansingTalisman);
		registerModels(RegistryHandler.antimagicTalisman);
	}
	
	private static void registerModels(Item... values) {
		for(Item entry : values) {
			ModelLoader.setCustomModelResourceLocation(entry, 0, new ModelResourceLocation(entry.getRegistryName(), "inventory"));
		}
	}
}