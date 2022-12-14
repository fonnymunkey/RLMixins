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
        public static SoundEvent ATOMIC_DECONSTRUCT;

        public static void init() {
                FLARE_SHOOT = new SoundEvent(new ResourceLocation(RLMixins.MODID, "flare_shoot")).setRegistryName("flare_shoot");
                FLARE_BURN = new SoundEvent(new ResourceLocation(RLMixins.MODID, "flare_burn")).setRegistryName("flare_burn");
                CRITICAL_STRIKE = new SoundEvent(new ResourceLocation(RLMixins.MODID, "critical_strike")).setRegistryName("critical_strike");
                ATOMIC_DECONSTRUCT = new SoundEvent(new ResourceLocation(RLMixins.MODID, "atomic_deconstruct")).setRegistryName("atomic_deconstruct");
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