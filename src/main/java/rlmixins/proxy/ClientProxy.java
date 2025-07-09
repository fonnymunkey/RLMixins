package rlmixins.proxy;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import rlmixins.handlers.ConfigHandler;
import rlmixins.handlers.RegistryHandler;
import rlmixins.handlers.chunkanimator.ChunkAnimationHandler;
import rlmixins.client.model.ModelLivingArmor;
import rlmixins.client.model.ModelScarliteArmor;
import rlmixins.client.model.ModelSentientArmor;
import rlmixins.client.model.ModelSteelArmor;
import rlmixins.util.ModLoadedUtil;
import rlmixins.wrapper.SRParasitesWrapper;

import java.util.HashMap;
import java.util.Map;

public class ClientProxy extends CommonProxy {
    
    private static final ModelSteelArmor steelArmor = new ModelSteelArmor(1.0F);
    private static final ModelSteelArmor steelArmorLegs = new ModelSteelArmor(0.5F);
    private static final ModelScarliteArmor scarliteArmor = new ModelScarliteArmor(1.0F);
    private static final ModelScarliteArmor scarliteArmorLegs = new ModelScarliteArmor(0.5F);
    
    private static Map<Item, ModelBiped> steelArmorModels = null;
    private static Map<Item, ModelBiped> scarliteArmorModels = null;
    
    private static final ModelLivingArmor livingArmor = new ModelLivingArmor(1.0F);
    private static final ModelLivingArmor livingArmorLegs = new ModelLivingArmor(0.5F);
    private static final ModelSentientArmor sentientArmor = new ModelSentientArmor(1.0F);
    private static final ModelSentientArmor sentientArmorLegs = new ModelSentientArmor(0.5F);
    
    private static Map<Item, ModelBiped> livingArmorModels = null;
    private static Map<Item, ModelBiped> sentientArmorModels = null;
    
    @Override
    public void registerSubscribers() {
        if(ConfigHandler.CHUNKANIMATOR_CONFIG.chunkAnimationXRayPatch && ModLoadedUtil.isChunkAnimatorLoaded()) {
            MinecraftForge.EVENT_BUS.register(ChunkAnimationHandler.class);
        }
    }
    
    @Override
    public Map<Item, ModelBiped> getSteelArmor() {
        if(steelArmorModels == null) {
            steelArmorModels = new HashMap<>();
            steelArmorModels.put(RegistryHandler.steelHelmet, steelArmor);
            steelArmorModels.put(RegistryHandler.steelChestplate, steelArmor);
            steelArmorModels.put(RegistryHandler.steelLeggings, steelArmorLegs);
            steelArmorModels.put(RegistryHandler.steelBoots, steelArmor);
        }
        return steelArmorModels;
    }
    
    @Override
    public Map<Item, ModelBiped> getScarliteArmor() {
        if(scarliteArmorModels == null) {
            scarliteArmorModels = new HashMap<>();
            scarliteArmorModels.put(RegistryHandler.scarliteHelmet, scarliteArmor);
            scarliteArmorModels.put(RegistryHandler.scarliteChestplate, scarliteArmor);
            scarliteArmorModels.put(RegistryHandler.scarliteLeggings, scarliteArmorLegs);
            scarliteArmorModels.put(RegistryHandler.scarliteBoots, scarliteArmor);
        }
        return scarliteArmorModels;
    }
    
    @Override
    public Map<Item, ModelBiped> getLivingArmor() {
        if(livingArmorModels == null) {
            livingArmorModels = new HashMap<>();
            livingArmorModels.put(SRParasitesWrapper.getLivingHelmet(), livingArmor);
            livingArmorModels.put(SRParasitesWrapper.getLivingChestplate(), livingArmor);
            livingArmorModels.put(SRParasitesWrapper.getLivingLeggings(), livingArmorLegs);
            livingArmorModels.put(SRParasitesWrapper.getLivingBoots(), livingArmor);
        }
        return livingArmorModels;
    }
    
    @Override
    public Map<Item, ModelBiped> getSentientArmor() {
        if(sentientArmorModels == null) {
            sentientArmorModels = new HashMap<>();
            sentientArmorModels.put(SRParasitesWrapper.getSentientHelmet(), sentientArmor);
            sentientArmorModels.put(SRParasitesWrapper.getSentientChestplate(), sentientArmor);
            sentientArmorModels.put(SRParasitesWrapper.getSentientLeggings(), sentientArmorLegs);
            sentientArmorModels.put(SRParasitesWrapper.getSentientBoots(), sentientArmor);
        }
        return sentientArmorModels;
    }
}