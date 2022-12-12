package rlmixins;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rlmixins.proxy.CommonProxy;
import rlmixins.handlers.ModRegistry;

@Mod(modid = RLMixins.MODID, version = RLMixins.VERSION, name = RLMixins.NAME)
public class RLMixins
{
    public static final String MODID = "rlmixins";
    public static final String VERSION = "1.0.4";
    public static final String NAME = "RLMixins";

    public static final Logger LOGGER = LogManager.getLogger();

    @SidedProxy(clientSide = "rlmixins.proxy.ClientProxy", serverSide = "rlmixins.proxy.CommonProxy")
    public static CommonProxy PROXY;
	
	@Instance(MODID)
	public static RLMixins instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModRegistry.init();
    }
}