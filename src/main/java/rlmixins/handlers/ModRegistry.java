package rlmixins.handlers;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;
import rlmixins.RLMixins;

@Mod.EventBusSubscriber(modid = RLMixins.MODID)
public class ModRegistry {

        public static SoundEvent FLARE_SHOOT;
        public static SoundEvent FLARE_BURN;

        public static void init() {
                FLARE_SHOOT = new SoundEvent(new ResourceLocation(RLMixins.MODID, "flare_shoot")).setRegistryName("flare_shoot");
                FLARE_BURN = new SoundEvent(new ResourceLocation(RLMixins.MODID, "flare_burn")).setRegistryName("flare_burn");
        }

        @SubscribeEvent
        public static void registerSoundEvent(RegistryEvent.Register<SoundEvent> event) {
                event.getRegistry().register(FLARE_SHOOT);
                event.getRegistry().register(FLARE_BURN);
        }
}

