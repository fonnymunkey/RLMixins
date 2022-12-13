package rlmixins.handlers;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.RLMixins;
import rlmixins.potion.PotionDelayedLaunch;
import rlmixins.potion.PotionEncumbered;

@Mod.EventBusSubscriber(modid = RLMixins.MODID)
public class ModRegistry {

        public static SoundEvent FLARE_SHOOT;
        public static SoundEvent FLARE_BURN;
        public static SoundEvent CRITICAL_STRIKE;

        public static void init() {
                FLARE_SHOOT = new SoundEvent(new ResourceLocation(RLMixins.MODID, "flare_shoot")).setRegistryName("flare_shoot");
                FLARE_BURN = new SoundEvent(new ResourceLocation(RLMixins.MODID, "flare_burn")).setRegistryName("flare_burn");
                CRITICAL_STRIKE = new SoundEvent(new ResourceLocation(RLMixins.MODID, "critical_strike")).setRegistryName("critical_strike");
        }

        @SubscribeEvent
        public static void registerSoundEvent(RegistryEvent.Register<SoundEvent> event) {
                event.getRegistry().register(FLARE_SHOOT);
                event.getRegistry().register(FLARE_BURN);
                event.getRegistry().register(CRITICAL_STRIKE);
        }

        @SubscribeEvent
        public static void registerPotionEvent(RegistryEvent.Register<Potion> event) {
                event.getRegistry().register(PotionEncumbered.INSTANCE);
                event.getRegistry().register(PotionDelayedLaunch.INSTANCE);
        }
}