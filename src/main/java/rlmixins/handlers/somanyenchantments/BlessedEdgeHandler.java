package rlmixins.handlers.somanyenchantments;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.Shultrea.Rin.Enchantments_Sector.Smc_010;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.RLMixins;

public class BlessedEdgeHandler {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void modifyAttackDamagePost(RLCombatModifyDamageEvent.Post event) {
        if(event.getEntityPlayer() == null || event.getStack().isEmpty() || event.getTarget() == null || !(event.getTarget() instanceof EntityLivingBase)) return;

        EntityLivingBase target = (EntityLivingBase)event.getTarget();
        EntityPlayer player = event.getEntityPlayer();
        int level = EnchantmentHelper.getEnchantmentLevel(Smc_010.BlessedEdge, event.getStack());
        if(level > 0 && target.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD && event.getCooledStrength() > 0.9F) {
            player.heal((float)level / 2.0F);
            event.setDamageModifier(event.getDamageModifier() + level);
        }
    }
}
