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
		@Config.Comment("Print information about material attributes when they are registered.")
		@Config.Name("Print Material Info")
		public boolean printInfo = false;
	}

	public static class ClientConfig {
		@Config.Comment("Currently targetted entity")
		@Config.Name("Target Entity")
		public String targetEntity = "";

		@Config.Comment("Grow X")
		@Config.Name("Grow X")
		public double growX = 0.0;

		@Config.Comment("Grow Y")
		@Config.Name("Grow Y")
		public double growY = 0.0;

		@Config.Comment("Grow Z")
		@Config.Name("Grow Z")
		public double growZ = 0.0;

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