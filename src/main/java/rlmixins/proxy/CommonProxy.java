package rlmixins.proxy;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.item.Item;
import rlmixins.entity.flare.EntityFlareNonAlbedo;

import java.util.Map;

public class CommonProxy {

    public void preInit() { }

    public void initParasiteModels() { }
    public void playSoundFlare(EntityFlareNonAlbedo flare) { }

    public Map<Item, ModelBiped> getLivingArmor() { return null; }

    public Map<Item, ModelBiped> getSentientArmor() { return null; }

    public Map<Item, ModelBiped> getSteelArmor() {
        return null;
    }

    public Map<Item, ModelBiped> getScarliteArmor() {
        return null;
    }
}