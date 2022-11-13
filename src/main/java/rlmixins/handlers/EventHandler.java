package rlmixins.handlers;
/*
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.RLMixins;

@Mod.EventBusSubscriber(modid = RLMixins.MODID)
public class EventHandler {

    @SubscribeEvent
    public static void renderWorldLastEvent(RenderWorldLastEvent event) {
        if (Minecraft.getMinecraft().objectMouseOver.entityHit != null) {
            //renderEntityBox(Minecraft.getMinecraft().objectMouseOver.entityHit, Minecraft.getMinecraft().player, event.getPartialTicks());
            renderEntityBox(Minecraft.getMinecraft().objectMouseOver.entityHit, event.getPartialTicks());
        }
    }

    //static void renderEntityBox(Entity entity, EntityPlayer player, float PartialTicks) {
    static void renderEntityBox(Entity entity, float PartialTicks) {

        AxisAlignedBB boundingBox = entity.getEntityBoundingBox();

        double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)PartialTicks;
        double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)PartialTicks;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)PartialTicks;
        Tessellator.getInstance().getBuffer().setTranslation(-d0, -d1, -d2);
        //GlStateManager.translate(entity.posX - player.posX, entity.posY - player.posY, entity.posZ - player.posZ);
        RenderGlobal.drawSelectionBoundingBox(boundingBox, 1.0F,1.0F,1.0F,1.0F);
        Tessellator.getInstance().getBuffer().setTranslation(0,0,0);
    }
}
*/