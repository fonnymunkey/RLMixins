package rlmixins.mixin.neat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.*;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import vazkii.neat.HealthBarRenderer;
import vazkii.neat.NeatConfig;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;

@Mixin(HealthBarRenderer.class)
public abstract class HealthBarRenderMixin {

    @Shadow(remap = false) public static Entity getEntityLookedAt(Entity e) { return null; }

    @Shadow(remap = false) public abstract void renderHealthBar(EntityLivingBase passedEntity, float partialTicks, Entity viewPoint);

    @Unique
    private static Field rlmixins$entityListField;

    /**
     * @author fonnymunkey
     * @reason fix rendering incompatiblity with Optifine
     */
    @SubscribeEvent
    @Overwrite(remap = false)
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if ((NeatConfig.renderInF1 || Minecraft.isGuiEnabled()) && NeatConfig.draw) {
            Entity cameraEntity = mc.getRenderViewEntity();
            BlockPos renderingVector = cameraEntity.getPosition();
            Frustum frustum = new Frustum();
            float partialTicks = mc.getRenderPartialTicks();
            double viewX = cameraEntity.lastTickPosX + (cameraEntity.posX - cameraEntity.lastTickPosX) * (double)partialTicks;
            double viewY = cameraEntity.lastTickPosY + (cameraEntity.posY - cameraEntity.lastTickPosY) * (double)partialTicks;
            double viewZ = cameraEntity.lastTickPosZ + (cameraEntity.posZ - cameraEntity.lastTickPosZ) * (double)partialTicks;
            frustum.setPosition(viewX, viewY, viewZ);
            if (NeatConfig.showOnlyFocused) {
                Entity focused = getEntityLookedAt(mc.player);
                if (focused instanceof EntityLivingBase && focused.isEntityAlive()) {
                    int oldProgram = GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM);
                    if(oldProgram != 0) GL20.glUseProgram(0);
                    this.renderHealthBar((EntityLivingBase)focused, partialTicks, cameraEntity);
                    if(oldProgram != 0) GL20.glUseProgram(oldProgram);
                }
            } else {
                WorldClient client = mc.world;
                Iterator<Entity> var15 = null;
                try {
                    if(rlmixins$entityListField == null) {
                        rlmixins$entityListField = ObfuscationReflectionHelper.findField(WorldClient.class, "field_73032_d");
                        rlmixins$entityListField.setAccessible(true);
                    }
                    var15 = ((Set<Entity>)rlmixins$entityListField.get(client)).iterator();
                }
                catch(Exception ignored) { }

                if(var15 == null) return;

                while(true) {
                    Entity entity;
                    do {
                        do {
                            do {
                                do {
                                    do {
                                        if (!var15.hasNext()) {
                                            return;
                                        }

                                        entity = (Entity)var15.next();
                                    } while(entity == null);
                                } while(!(entity instanceof EntityLivingBase));
                            } while(entity == mc.player);
                        } while(!entity.isInRangeToRender3d((double)renderingVector.getX(), (double)renderingVector.getY(), (double)renderingVector.getZ()));
                    } while(!entity.ignoreFrustumCheck && !frustum.isBoundingBoxInFrustum(entity.getEntityBoundingBox()));

                    if (entity.isEntityAlive() && entity.getRecursivePassengers().isEmpty()) {
                        int oldProgram = GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM);
                        if(oldProgram != 0) GL20.glUseProgram(0);
                        this.renderHealthBar((EntityLivingBase)entity, partialTicks, cameraEntity);
                        if(oldProgram != 0) GL20.glUseProgram(oldProgram);
                    }
                }
            }
        }
    }
}