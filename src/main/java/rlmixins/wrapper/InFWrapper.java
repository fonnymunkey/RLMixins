package rlmixins.wrapper;

import com.github.alexthe666.iceandfire.core.ModItems;
import com.github.alexthe666.iceandfire.entity.*;
import com.github.alexthe666.iceandfire.item.*;
import de.Whitedraco.switchbow.helper.ArrowItemStackEqual;
import de.Whitedraco.switchbow.helper.QuiverArrowHelper;
import de.Whitedraco.switchbow.helper.SwitchBowHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public abstract class InFWrapper {

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

    public static boolean isDragon(Entity entity) { return isFireDragon(entity) || isIceDragon(entity); }

    public static boolean isCyclops(Entity entity) { return entity instanceof EntityCyclops; }

    public static boolean canDismountDragon(Entity entity) {
        EntityDragonBase dragon = (EntityDragonBase)entity;
        return !(dragon.getAnimation() == EntityDragonBase.ANIMATION_SHAKEPREY && dragon.getAnimationTick() <= 60);
    }

    public static boolean canDismountCyclops(Entity entity) {
        EntityCyclops cyclops = (EntityCyclops)entity;
        return !(cyclops.getAnimation() == EntityCyclops.ANIMATION_EATPLAYER && cyclops.getAnimationTick() < 32);
    }

    public static ItemStack getDragonBowAmmo(EntityPlayer player) {
        if(isArrow(player.getHeldItem(EnumHand.OFF_HAND))) return player.getHeldItem(EnumHand.OFF_HAND);
        else if(isArrow(player.getHeldItem(EnumHand.MAIN_HAND))) return player.getHeldItem(EnumHand.MAIN_HAND);
        else {
            for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = player.inventory.getStackInSlot(i);
                if(isArrow(itemstack)) return itemstack;
            }
            return ItemStack.EMPTY;
        }
    }

    private static boolean isArrow(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == ModItems.dragonbone_arrow;
    }
}