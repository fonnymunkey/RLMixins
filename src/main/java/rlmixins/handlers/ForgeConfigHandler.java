package rlmixins.handlers;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.RLMixins;

@Config(modid = RLMixins.MODID)
public class ForgeConfigHandler {
	
	@Config.Comment("Server Config")
	@Config.Name("Server")
	public static final ServerConfig server = new ServerConfig();

	@Config.Comment("Client Config")
	@Config.Name("Client")
	public static final ClientConfig client = new ClientConfig();
	
	public static class ServerConfig {
		@Config.Comment("Item Blacklist for the Hungry Farmer trait.")
		@Config.Name("Hungry Farmer Blacklist")
		public String[] hungryFarmerBlacklist = {""};

	}

	public static class ClientConfig {
		@Config.Comment("Currently targetted entity")
		@Config.Name("Target Entity")
		public String targetEntity = "";

		@Config.Comment("Grow X/Z")
		@Config.Name("Grow X/Z")
		public double growXZ = 0.0;

		@Config.Comment("Grow Y")
		@Config.Name("Grow Y")
		public double growY = 0.0;

		@Config.Comment("Offset Y")
		@Config.Name("Offset Y")
		public double offsetY = 0.0;

	}
	
	@Mod.EventBusSubscriber(modid = RLMixins.MODID)
	private static class EventHandler{
		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(RLMixins.MODID)) ConfigManager.sync(RLMixins.MODID, Config.Type.INSTANCE);
		}
	}
}