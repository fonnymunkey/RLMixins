package rlmixins;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rlmixins.handlers.ForgeConfigHandler;
import rlmixins.handlers.bettersurvival.PenetrationHandler;
import rlmixins.handlers.bountifulbaubles.BrokenHeartBaubleHandler;
import rlmixins.handlers.bountifulbaubles.CobaltShieldBaubleHandler;
import rlmixins.handlers.bountifulbaubles.FireResistanceBaubleHandler;
import rlmixins.handlers.charm.MagneticHandler;
import rlmixins.handlers.chunkanimator.ChunkAnimatorCooldownHandler;
import rlmixins.handlers.forgottenitems.VeinPickaxeHandler;
import rlmixins.handlers.iceandfire.WeaponModifierHandler;
import rlmixins.handlers.quark.ChestBoatHandler;
import rlmixins.handlers.quark.PacketHandler;
import rlmixins.handlers.quark.RightClickSignEditHandler;
import rlmixins.handlers.inspirations.MilkCooldownHandler;
import rlmixins.handlers.reskillable.UndershirtHandler;
import rlmixins.handlers.rlmixins.LesserFireResistanceHandler;
import rlmixins.handlers.rlmixins.NetherBaneEffectHandler;
import rlmixins.handlers.somanyenchantments.*;
import rlmixins.handlers.srparasite.ArmorEffectHandler;
import rlmixins.handlers.srparasite.ScytheSweepHandler;
import rlmixins.proxy.CommonProxy;
import rlmixins.handlers.ModRegistry;

@Mod(modid = RLMixins.MODID, version = RLMixins.VERSION, name = RLMixins.NAME)
public class RLMixins
{
    public static final String MODID = "rlmixins";
    public static final String VERSION = "1.2.14";
    public static final String NAME = "RLMixins";
    public static final Logger LOGGER = LogManager.getLogger();

    @SidedProxy(clientSide = "rlmixins.proxy.ClientProxy", serverSide = "rlmixins.proxy.CommonProxy")
    public static CommonProxy PROXY;
	
	@Instance(MODID)
	public static RLMixins instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModRegistry.init();
        RLMixins.PROXY.preInit();

        if(ForgeConfigHandler.mixinConfig.brokenHeartRework) MinecraftForge.EVENT_BUS.register(BrokenHeartBaubleHandler.class);
        if(ForgeConfigHandler.mixinConfig.obsidianResistanceRework) MinecraftForge.EVENT_BUS.register(FireResistanceBaubleHandler.class);
        if(ForgeConfigHandler.mixinConfig.chunkAnimatorXRay) MinecraftForge.EVENT_BUS.register(ChunkAnimatorCooldownHandler.class);
        if(ForgeConfigHandler.mixinConfig.cancelIceAndFireBonusHandling) MinecraftForge.EVENT_BUS.register(WeaponModifierHandler.class);
        if(ForgeConfigHandler.mixinConfig.undershirtRework) MinecraftForge.EVENT_BUS.register(UndershirtHandler.class);
        if(ForgeConfigHandler.mixinConfig.overhaulEnchants) {
            MinecraftForge.EVENT_BUS.register(ArcSlashHandler.class);
            MinecraftForge.EVENT_BUS.register(AtomicDeconstructorHandler.class);
            MinecraftForge.EVENT_BUS.register(BlessedEdgeHandler.class);
            MinecraftForge.EVENT_BUS.register(ButcheringHandler.class);
            MinecraftForge.EVENT_BUS.register(CriticalStrikeHandler.class);
            MinecraftForge.EVENT_BUS.register(CursedEdgeHandler.class);
            MinecraftForge.EVENT_BUS.register(CurseOfDecayHandler.class);
            MinecraftForge.EVENT_BUS.register(CurseOfPossesionHandler.class);
            MinecraftForge.EVENT_BUS.register(DefusionHandler.class);
            MinecraftForge.EVENT_BUS.register(FieryEdgeHandler.class);
            MinecraftForge.EVENT_BUS.register(LuckMagnificationHandler.class);
            MinecraftForge.EVENT_BUS.register(ReviledBladeHandler.class);
            MinecraftForge.EVENT_BUS.register(RunePiercingCapabilitiesHandler.class);
            MinecraftForge.EVENT_BUS.register(SpellBreakerHandler.class);
            MinecraftForge.EVENT_BUS.register(SwifterSlashesHandler.class);
            MinecraftForge.EVENT_BUS.register(WaterAspectHandler.class);
        }
        if(ForgeConfigHandler.mixinConfig.fixRightClickSignEdit) {
            PacketHandler.init();
            MinecraftForge.EVENT_BUS.register(RightClickSignEditHandler.class);
        }
        if(ForgeConfigHandler.mixinConfig.rehandleSentientScythe) MinecraftForge.EVENT_BUS.register(ScytheSweepHandler.class);
        if(ForgeConfigHandler.mixinConfig.quarkChestBoatDupe) MinecraftForge.EVENT_BUS.register(ChestBoatHandler.class);
        if(ForgeConfigHandler.mixinConfig.veinPickaxePatch) MinecraftForge.EVENT_BUS.register(VeinPickaxeHandler.class);
        if(ForgeConfigHandler.mixinConfig.magneticDupePatch) MinecraftForge.EVENT_BUS.register(MagneticHandler.class);
        if(ForgeConfigHandler.mixinConfig.penetrationFix) MinecraftForge.EVENT_BUS.register(PenetrationHandler.class);

        if(ForgeConfigHandler.server.registerLesserFireResistance) MinecraftForge.EVENT_BUS.register(LesserFireResistanceHandler.class);
        if(ForgeConfigHandler.server.enableNetherBane) MinecraftForge.EVENT_BUS.register(NetherBaneEffectHandler.class);
        if(ForgeConfigHandler.server.cobaltShieldCancelsKnockback) MinecraftForge.EVENT_BUS.register(CobaltShieldBaubleHandler.class);
        if(ForgeConfigHandler.server.milkingFix) MinecraftForge.EVENT_BUS.register(MilkCooldownHandler.class);
        if(ForgeConfigHandler.server.parasiteArmorFearCuring || ForgeConfigHandler.server.parasiteArmorViralCuring) MinecraftForge.EVENT_BUS.register(ArmorEffectHandler.class);
    }
}