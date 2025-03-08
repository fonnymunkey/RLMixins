package rlmixins.handlers.reskillable;

import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import codersafterdark.reskillable.lib.LibMisc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SafePort32Handler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void  onSafePortAnyTeleport(EnderTeleportEvent event){
        if(event.isCanceled()) return;
        if(event.getEntity() == null || event.getEntity().world.isRemote) return;
        if(!(event.getEntity() instanceof EntityPlayer)) return;

        EntityPlayer player = (EntityPlayer)event.getEntity();
        PlayerSkillInfo magicSkillData = PlayerDataHandler.get(player).getSkillInfo(ReskillableRegistries.SKILLS.getValue(new ResourceLocation(LibMisc.MOD_ID, "magic")));
        if(magicSkillData.isUnlocked(
                ReskillableRegistries.UNLOCKABLES.getValue(new ResourceLocation(LibMisc.MOD_ID, "safe_port"))) &&
                magicSkillData.getLevel() >= 32){
            player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 600, 2));
        }
    }
}
