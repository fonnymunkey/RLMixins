package rlmixins.handlers.somanyenchantments;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.Shultrea.Rin.Enchantments_Sector.Smc_010;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.RLMixins;

public class DefusionHandler {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void modifyAttackDamagePre(RLCombatModifyDamageEvent.Pre event) {
        if(event.getEntityPlayer() == null || event.getStack().isEmpty() || event.getTarget() == null || !(event.getTarget() instanceof EntityLivingBase)) return;

        EntityLivingBase target = (EntityLivingBase)event.getTarget();
        int level = EnchantmentHelper.getEnchantmentLevel(Smc_010.Defusion, event.getStack());
        if(level > 0 && target instanceof EntityCreeper) {
            event.setDamageModifier(event.getDamageModifier() + 2.0F*(float)level);
            if(target.world.rand.nextFloat() < 0.05F*(float)level) {
                EntityCreeper creeper = (EntityCreeper)target;
                NBTTagCompound fuse = new NBTTagCompound();
                creeper.writeEntityToNBT(fuse);
                short fuseTime = 32767;
                fuse.setShort("Fuse", fuseTime);
                creeper.readEntityFromNBT(fuse);
            }
        }
    }
}