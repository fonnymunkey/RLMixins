package rlmixins.handlers.inspirations;

import knightminer.inspirations.common.network.InspirationsNetwork;
import knightminer.inspirations.common.network.MilkablePacket;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MilkCooldownHandler {
    public static final String TAG_MILKCOOLDOWN = "milk_cooldown";

    @SubscribeEvent
    public static void updateMilkCooldown(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.getEntityWorld();
        if(!world.isRemote && entity.ticksExisted%20 == 0) {
            if((entity instanceof EntityCow && !entity.isChild())
                    || (entity instanceof EntitySquid)) {
                NBTTagCompound tags = entity.getEntityData();
                short cooldown = tags.getShort(TAG_MILKCOOLDOWN);
                if(cooldown > 0) {
                    tags.setShort(TAG_MILKCOOLDOWN, (short)(cooldown - 1));
                    if(cooldown == 1) InspirationsNetwork.sendToClients(world, entity.getPosition(), new MilkablePacket(entity, true));
                }
            }
        }
    }
}