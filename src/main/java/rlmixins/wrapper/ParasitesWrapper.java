package rlmixins.wrapper;

import com.dhanantry.scapeandrunparasites.init.SRPItems;
import net.minecraft.item.Item;

public abstract class ParasitesWrapper {

    public static Item getLivingHelmet() { return SRPItems.armor_helmet; }
    public static Item getLivingChestplate() { return SRPItems.armor_chest; }
    public static Item getLivingLeggings() { return SRPItems.armor_pants; }
    public static Item getLivingBoots() { return SRPItems.armor_boots; }

    public static Item getSentientHelmet() { return SRPItems.armor_helmetSentient; }
    public static Item getSentientChestplate() { return SRPItems.armor_chestSentient; }
    public static Item getSentientLeggings() { return SRPItems.armor_pantsSentient; }
    public static Item getSentientBoots() { return SRPItems.armor_bootsSentient; }
}