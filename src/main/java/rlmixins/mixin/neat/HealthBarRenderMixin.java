package rlmixins.mixin.neat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import vazkii.neat.HealthBarRenderer;
import vazkii.neat.NeatConfig;

import java.awt.*;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

@Mixin(HealthBarRenderer.class)
public abstract class HealthBarRenderMixin {

    @Shadow(remap = false)
    public static Entity getEntityLookedAt(Entity e) { return null; }

    /**
     * @author fonnymunkey
     * @reason change the subscribed event to render better with shaders
     */
    @Overwrite(remap = false)
    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        //noop
    }

    @Unique
    @SubscribeEvent
    public void rlmixins_onRenderLivingSpecial(RenderLivingEvent.Specials.Pre<? extends EntityLivingBase> event) {
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
                if (focused != null && focused instanceof EntityLivingBase && focused.isEntityAlive()) {
                    this.renderHealthBar((EntityLivingBase)focused, partialTicks, cameraEntity);
                }
            } else {
                WorldClient client = mc.world;
                Set<Entity> entities = (Set)ReflectionHelper.getPrivateValue(WorldClient.class, client, new String[]{"entityList", "field_73032_d", "J"});
                Iterator var15 = entities.iterator();

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
                        this.renderHealthBar((EntityLivingBase)entity, partialTicks, cameraEntity);
                    }
                }
            }

        }
    }

    /**
     * @author fonnymunkey
     * @reason tweak rendering to make it render better while using shaders
     */
    @Overwrite(remap = false)
    public void renderHealthBar(EntityLivingBase passedEntity, float partialTicks, Entity viewPoint) {
        Stack<EntityLivingBase> ridingStack = new Stack();
        EntityLivingBase entity = passedEntity;
        ridingStack.push(passedEntity);

        while(entity.getRidingEntity() != null && entity.getRidingEntity() instanceof EntityLivingBase) {
            entity = (EntityLivingBase)entity.getRidingEntity();
            ridingStack.push(entity);
        }

        Minecraft mc = Minecraft.getMinecraft();
        float pastTranslate = 0.0F;

        while(true) {
            boolean boss;
            String entityID;
            double x;
            double y;
            double z;
            float scale;
            float maxHealth;
            float health;
            do {
                do {
                    do {
                        do {
                            float distance;
                            do {
                                do {
                                    do {
                                        if (ridingStack.isEmpty()) {
                                            return;
                                        }

                                        entity = (EntityLivingBase)ridingStack.pop();
                                        boss = !entity.isNonBoss();
                                        entityID = EntityList.getEntityString(entity);
                                    } while(NeatConfig.blacklist.contains(entityID));

                                    distance = passedEntity.getDistance(viewPoint);
                                } while(distance > (float)NeatConfig.maxDistance);
                            } while(!passedEntity.canEntityBeSeen(viewPoint));
                        } while(entity.isInvisible());
                    } while(!NeatConfig.showOnBosses && !boss);
                } while(!NeatConfig.showOnPlayers && entity instanceof EntityPlayer);

                x = passedEntity.lastTickPosX + (passedEntity.posX - passedEntity.lastTickPosX) * (double)partialTicks;
                y = passedEntity.lastTickPosY + (passedEntity.posY - passedEntity.lastTickPosY) * (double)partialTicks;
                z = passedEntity.lastTickPosZ + (passedEntity.posZ - passedEntity.lastTickPosZ) * (double)partialTicks;
                scale = 0.026666673F;
                maxHealth = entity.getMaxHealth();
                health = Math.min(maxHealth, entity.getHealth());
            } while(maxHealth <= 0.0F);

            float percent = (float)((int)(health / maxHealth * 100.0F));
            RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)(x - renderManager.viewerPosX), (float)(y - renderManager.viewerPosY + (double)passedEntity.height + NeatConfig.heightAbove), (float)(z - renderManager.viewerPosZ));
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(-scale, -scale, scale);
            boolean lighting = GL11.glGetBoolean(2896);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            float padding = (float)NeatConfig.backgroundPadding;
            int bgHeight = NeatConfig.backgroundHeight;
            int barHeight = NeatConfig.barHeight;
            float size = (float)NeatConfig.plateSize;
            int r = 0;
            int g = 255;
            int b = 0;
            ItemStack stack = null;
            if (entity instanceof IMob) {
                r = 255;
                g = 0;
                EnumCreatureAttribute attr = entity.getCreatureAttribute();
                switch (attr) {
                    case ARTHROPOD:
                        stack = new ItemStack(Items.SPIDER_EYE);
                        break;
                    case UNDEAD:
                        stack = new ItemStack(Items.ROTTEN_FLESH);
                        break;
                    default:
                        stack = new ItemStack(Items.SKULL, 1, 4);
                }
            }

            if (boss) {
                stack = new ItemStack(Items.SKULL);
                size = (float)NeatConfig.plateSizeBoss;
                r = 128;
                g = 0;
                b = 128;
            }

            int armor = entity.getTotalArmorValue();
            boolean useHue = !NeatConfig.colorByType;
            float s;
            if (useHue) {
                s = Math.max(0.0F, health / maxHealth / 3.0F - 0.07F);
                Color color = Color.getHSBColor(s, 1.0F, 1.0F);
                r = color.getRed();
                g = color.getGreen();
                b = color.getBlue();
            }

            GlStateManager.translate(0.0F, pastTranslate, 0.0F);
            s = 0.5F;
            String name = I18n.format(entity.getDisplayName().getFormattedText(), new Object[0]);
            if (entity instanceof EntityLiving && ((EntityLiving)entity).hasCustomName()) {
                name = TextFormatting.ITALIC + ((EntityLiving)entity).getCustomNameTag();
            } else if (entity instanceof EntityVillager) {
                name = I18n.format("entity.Villager.name");
            }

            float namel = (float)mc.fontRenderer.getStringWidth(name) * s;
            if (namel + 20.0F > size * 2.0F) {
                size = namel / 2.0F + 10.0F;
            }

            float healthSize = size * (health / maxHealth);
            if (NeatConfig.drawBackground) {
                buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                buffer.pos((double)(-size - padding), (double)(-bgHeight), 0.0).color(0, 0, 0, 64).endVertex();
                buffer.pos((double)(-size - padding), (double)((float)barHeight + padding), 0.0).color(0, 0, 0, 64).endVertex();
                buffer.pos((double)(size + padding), (double)((float)barHeight + padding), 0.0).color(0, 0, 0, 64).endVertex();
                buffer.pos((double)(size + padding), (double)(-bgHeight), 0.0).color(0, 0, 0, 64).endVertex();
                tessellator.draw();
            }

            buffer.begin(6, DefaultVertexFormats.POSITION_COLOR);
            buffer.pos((double)(-size), 0.0, 0.0).color(r, g, b, 127).endVertex();
            buffer.pos((double)(-size), (double)barHeight, 0.0).color(r, g, b, 127).endVertex();
            buffer.pos((double)(healthSize * 2.0F - size), (double)barHeight, 0.0).color(r, g, b, 127).endVertex();
            buffer.pos((double)(healthSize * 2.0F - size), 0.0, 0.0).color(r, g, b, 127).endVertex();
            tessellator.draw();

            GlStateManager.enableTexture2D();
            GlStateManager.pushMatrix();
            GlStateManager.translate(-size, -4.5F, 0.0F);
            GlStateManager.scale(s, s, s);
            mc.fontRenderer.drawString(name, 0, 0, 16777215);
            GlStateManager.pushMatrix();
            float s1 = 0.75F;
            GlStateManager.scale(s1, s1, s1);
            int h = NeatConfig.hpTextHeight;
            String maxHpStr = TextFormatting.BOLD + "" + (double)Math.round((double)maxHealth * 100.0) / 100.0;
            String hpStr = "" + (double)Math.round((double)health * 100.0) / 100.0;
            String percStr = (int)percent + "%";
            if (maxHpStr.endsWith(".0")) {
                maxHpStr = maxHpStr.substring(0, maxHpStr.length() - 2);
            }

            if (hpStr.endsWith(".0")) {
                hpStr = hpStr.substring(0, hpStr.length() - 2);
            }

            if (NeatConfig.showCurrentHP) {
                mc.fontRenderer.drawString(hpStr, 2, h, 16777215);
            }

            if (NeatConfig.showMaxHP) {
                mc.fontRenderer.drawString(maxHpStr, (int)(size / (s * s1) * 2.0F) - 2 - mc.fontRenderer.getStringWidth(maxHpStr), h, 16777215);
            }

            if (NeatConfig.showPercentage) {
                mc.fontRenderer.drawString(percStr, (int)(size / (s * s1)) - mc.fontRenderer.getStringWidth(percStr) / 2, h, -1);
            }

            if (NeatConfig.enableDebugInfo && mc.gameSettings.showDebugInfo) {
                mc.fontRenderer.drawString("ID: \"" + entityID + "\"", 0, h + 16, -1);
            }

            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            int off = 0;
            s1 = 0.5F;
            GlStateManager.scale(s1, s1, s1);
            GlStateManager.translate(size / (s * s1) * 2.0F - 16.0F, 0.0F, 0.0F);
            mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            if (stack != null && NeatConfig.showAttributes) {
                this.rlmixins_renderIcon(off, 0, stack, 16, 16);
                off -= 16;
            }

            if (armor > 0 && NeatConfig.showArmor) {
                int ironArmor = armor % 5;
                int diamondArmor = armor / 5;
                if (!NeatConfig.groupArmor) {
                    ironArmor = armor;
                    diamondArmor = 0;
                }

                stack = new ItemStack(Items.IRON_CHESTPLATE);

                int i;
                for(i = 0; i < ironArmor; ++i) {
                    this.rlmixins_renderIcon(off, 0, stack, 16, 16);
                    off -= 4;
                }

                stack = new ItemStack(Items.DIAMOND_CHESTPLATE);

                for(i = 0; i < diamondArmor; ++i) {
                    this.rlmixins_renderIcon(off, 0, stack, 16, 16);
                    off -= 4;
                }
            }

            GlStateManager.popMatrix();
            GlStateManager.disableBlend();
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            if (lighting) {
                GlStateManager.enableLighting();
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            pastTranslate -= (float)(bgHeight + barHeight) + padding;
        }
    }

    @Unique
    private void rlmixins_renderIcon(int vertexX, int vertexY, ItemStack stack, int intU, int intV) {
        try {
            IBakedModel iBakedModel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
            TextureAtlasSprite textureAtlasSprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(iBakedModel.getParticleTexture().getIconName());
            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            buffer.pos((double)vertexX, (double)(vertexY + intV), 0.0).tex((double)textureAtlasSprite.getMinU(), (double)textureAtlasSprite.getMaxV()).endVertex();
            buffer.pos((double)(vertexX + intU), (double)(vertexY + intV), 0.0).tex((double)textureAtlasSprite.getMaxU(), (double)textureAtlasSprite.getMaxV()).endVertex();
            buffer.pos((double)(vertexX + intU), (double)vertexY, 0.0).tex((double)textureAtlasSprite.getMaxU(), (double)textureAtlasSprite.getMinV()).endVertex();
            buffer.pos((double)vertexX, (double)vertexY, 0.0).tex((double)textureAtlasSprite.getMinU(), (double)textureAtlasSprite.getMinV()).endVertex();
            tessellator.draw();
        } catch (Exception var10) {
        }

    }
}