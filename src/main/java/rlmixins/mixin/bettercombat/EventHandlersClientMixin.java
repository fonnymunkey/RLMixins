package rlmixins.mixin.bettercombat;

import bettercombat.mod.client.handler.EventHandlersClient;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.ReachFixHandler;

import javax.annotation.Nullable;

@Mixin(EventHandlersClient.class)
public abstract class EventHandlersClientMixin {

    /**
     * Make BetterCombat utilize ReachFix's method
     */
    @Redirect(
            method = "onMouseLeftClick",
            at = @At(value = "INVOKE", target = "Lbettercombat/mod/client/handler/EventHandlersClient;getMouseOverExtended(D)Lnet/minecraft/util/math/RayTraceResult;"),
            remap = false
    )
    private static RayTraceResult rlmixins_betterCombatEventHandlersClient_onMouseLeftClick(double w) {
        return getMouseOverEntityInRange(false);
    }


    /**
     * Make BetterCombat utilize ReachFix's method
     */
    @Redirect(
            method = "onMouseRightClick",
            at = @At(value = "INVOKE", target = "Lbettercombat/mod/client/handler/EventHandlersClient;getMouseOverExtended(D)Lnet/minecraft/util/math/RayTraceResult;"),
            remap = false
    )
    private static RayTraceResult rlmixins_betterCombatEventHandlersClient_onMouseRightClick(double w) {
        return getMouseOverEntityInRange(true);
    }


    //TODO: Replace mc.objectMouseOver with ReachFix's EntityRendererHook.pointedObject when it gets updated
    /**
     * Returns the mouseover raytrace if entity is in range
     */
    @Nullable
    private static RayTraceResult getMouseOverEntityInRange(boolean offhand) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = (mc.getRenderViewEntity() instanceof EntityPlayer ? (EntityPlayer)mc.getRenderViewEntity() : null);
        Entity entity = mc.objectMouseOver.entityHit;
        if(entity != null) {
            return isEntityInRange(player, entity, offhand) ? mc.objectMouseOver : null;
        }
        return null;
    }

    //TODO: Replace with ReachFix's EntityRendererHook.pointedObject when it gets updated
    /**
     * Method modified from Meldexun's ReachFix
     * https://github.com/Meldexun/ReachFix/blob/1.12/src/main/java/meldexun/reachfix/hook/NetHandlerPlayServerHook.java
     *
     * Yea, yea, "shouldn't do this check clientside" blah blah
     * BetterCombat already does this check clientside anyways, so whatever
     */
    private static boolean isEntityInRange(EntityPlayer player, Entity entity, boolean offhand) {
        AxisAlignedBB aabb = entity.getEntityBoundingBox();
        if(entity.getCollisionBorderSize() != 0.0F) {
            aabb = aabb.grow(entity.getCollisionBorderSize());
        }
        double x = (aabb.maxX - aabb.minX) * 0.5D;
        double y = (aabb.maxY - aabb.minY) * 0.5D;
        double z = (aabb.maxZ - aabb.minZ) * 0.5D;
        double aabbRadius = Math.sqrt(x * x + y * y + z * z);
        double x1 = aabb.minX + x - player.posX;
        double y1 = aabb.minY + y - (player.posY + player.eyeHeight);
        double z1 = aabb.minZ + z - player.posZ;
        double distance = Math.sqrt(x1 * x1 + y1 * y1 + z1 * z1);
        double reach = ReachFixHandler.getEntityReach(player, offhand);
        return distance < reach + aabbRadius + 1.0D;
    }
}