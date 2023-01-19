package rlmixins.handlers.iceandfire;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.wrapper.InFWrapper;

public class WeaponModifierHandler {

    /**
     * Handle improperly applied IceAndFire weapon attributes
     */
    @SubscribeEvent
    public static void modifyAttackDamagePre(RLCombatModifyDamageEvent.Pre event) {
        EntityPlayer player = event.getEntityPlayer();
        Entity target = event.getTarget();
        if(player == null || target == null || event.getStack().isEmpty()) return;

        Item item = event.getStack().getItem();
        if(InFWrapper.isModifierClass(item)) {
            EnumCreatureAttribute attribute = target instanceof EntityLivingBase ? ((EntityLivingBase)target).getCreatureAttribute() : EnumCreatureAttribute.UNDEFINED;
            if(attribute == EnumCreatureAttribute.UNDEAD && InFWrapper.isSilverWeapon(item)) {
                event.setDamageModifier(event.getDamageModifier() + 2.0F);
            }
            else if(InFWrapper.isMyrmexWeapon(item)) {
                if(attribute != EnumCreatureAttribute.ARTHROPOD) {
                    event.setDamageModifier(event.getDamageModifier() + 4.0F);
                }
                else if(InFWrapper.isDeathworm(target)) {
                    event.setDamageModifier(event.getDamageModifier() + 4.0F);
                }
            }
            else if(InFWrapper.isFireDragon(target) && InFWrapper.isIcedWeapon(item)) {
                event.setDamageModifier(event.getDamageModifier() + 8.0F);
            }
            else if(InFWrapper.isIceDragon(target) && InFWrapper.isFlamedWeapon(item)) {
                event.setDamageModifier(event.getDamageModifier() + 8.0F);
            }
        }
    }
}