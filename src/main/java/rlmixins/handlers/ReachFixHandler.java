package rlmixins.handlers;

import com.mujmajnkraft.bettersurvival.ICustomWeapon;
import com.oblivioussp.spartanweaponry.api.IWeaponPropertyContainer;
import com.oblivioussp.spartanweaponry.api.weaponproperty.WeaponProperty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ReachFixHandler {

    /**
     * Basically reimplement ReachFix's handling except make it respect offhand
     * Credit to Meldexun for the class this is based on https://github.com/Meldexun/ReachFix/blob/1.12/src/main/java/meldexun/reachfix/util/ReachFixUtil.java
     */


    public static double getEntityReach(EntityPlayer player, boolean offhand) {
        return Math.max(getBlockReach(player, offhand) - 1.5D, 0.0D);
    }

    public static double getBlockReach(EntityPlayer player, boolean offhand) {
        double reach = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
        reach += getReachBonusSpartanWeaponry(player, offhand);
        reach += getReachBonusBetterSurvival(player, offhand);
        return reach;
    }

    public static double getReachBonusBetterSurvival(EntityPlayer player, boolean offhand) {
        ItemStack stack = (offhand ? player.getHeldItemOffhand() : player.getHeldItemMainhand());
        Item item = stack.getItem();
        if(!(item instanceof ICustomWeapon)) return 0.0D;
        ICustomWeapon customWeapon = (ICustomWeapon) item;
        float reach = customWeapon.getReach();
        if(reach <= 5.0F) return 0.0D;
        return reach - 5.0D;
    }

    public static double getReachBonusSpartanWeaponry(EntityPlayer player, boolean offhand) {
        ItemStack stack = (offhand ? player.getHeldItemOffhand() : player.getHeldItemMainhand());
        Item item = stack.getItem();
        if(!(item instanceof IWeaponPropertyContainer<?>)) return 0.0D;
        IWeaponPropertyContainer<?> propertyContainer = (IWeaponPropertyContainer<?>) item;
        WeaponProperty reachProperty = propertyContainer.getFirstWeaponPropertyWithType("reach");
        if(reachProperty == null) return 0.0D;
        return reachProperty.getMagnitude() - 5.0D;
    }
}