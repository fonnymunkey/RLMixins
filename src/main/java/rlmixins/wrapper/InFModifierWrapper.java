package rlmixins.wrapper;

import com.github.alexthe666.iceandfire.core.ModItems;
import com.github.alexthe666.iceandfire.entity.EntityDeathWorm;
import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import com.github.alexthe666.iceandfire.item.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;

public abstract class InFModifierWrapper {

    /**
     * Directly check instances of classes/items instead of material, Spartan stuff already handles materials correctly, don't want to double apply
     */

    public static boolean isModifierClass(Item item) {
        return item instanceof ItemModSword ||
                item instanceof ItemAlchemySword ||
                item instanceof ItemModAxe ||
                item instanceof ItemModShovel ||
                item instanceof ItemModPickaxe ||
                item instanceof ItemModHoe;
    }

    public static boolean isSilverWeapon(Item item) {
        return item == ModItems.silver_sword ||
                item == ModItems.silver_axe ||
                item == ModItems.silver_shovel ||
                item == ModItems.silver_pickaxe ||
                item == ModItems.silver_hoe;
    }

    public static boolean isFlamedWeapon(Item item) {
        return item == ModItems.dragonbone_sword_fire;
    }

    public static boolean isIcedWeapon(Item item) {
        return item == ModItems.dragonbone_sword_ice;
    }

    public static boolean isMyrmexWeapon(Item item) {
        return isDesertMyrmexWeapon(item) || isJungleMyrmexWeapon(item);
    }

    public static boolean isDesertMyrmexWeapon(Item item) {
        return item == ModItems.myrmex_desert_sword_venom ||
                item == ModItems.myrmex_desert_sword ||
                item == ModItems.myrmex_desert_axe ||
                item == ModItems.myrmex_desert_shovel ||
                item == ModItems.myrmex_desert_pickaxe ||
                item == ModItems.myrmex_desert_hoe;
    }

    public static boolean isJungleMyrmexWeapon(Item item) {
        return item == ModItems.myrmex_jungle_sword_venom ||
                item == ModItems.myrmex_jungle_sword ||
                item == ModItems.myrmex_jungle_axe ||
                item == ModItems.myrmex_jungle_shovel ||
                item == ModItems.myrmex_jungle_pickaxe ||
                item == ModItems.myrmex_jungle_hoe;
    }

    public static boolean isDeathworm(Entity entity) {
        return entity instanceof EntityDeathWorm;
    }

    public static boolean isFireDragon(Entity entity) {
        return entity instanceof EntityFireDragon;
    }

    public static boolean isIceDragon(Entity entity) {
        return entity instanceof EntityIceDragon;
    }
}