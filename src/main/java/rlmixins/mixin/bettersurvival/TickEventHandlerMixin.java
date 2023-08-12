package rlmixins.mixin.bettersurvival;

import com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties.ArrowPropertiesProvider;
import com.mujmajnkraft.bettersurvival.capabilities.extendedarrowproperties.IArrowProperties;
import com.mujmajnkraft.bettersurvival.eventhandlers.TickEventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;
import java.util.List;

@Mixin(TickEventHandler.class)
public abstract class TickEventHandlerMixin {

    /**
     * @author fonnymunkey
     * @reason Modify handler to fix crash with TickDynamic
     */
    @Overwrite(remap = false)
    @SubscribeEvent(
            priority = EventPriority.NORMAL
    )
    public void onTick(TickEvent.WorldTickEvent event) {
        List<Entity> entities = event.world.loadedEntityList;
        ArrayList<EntityArrow> arrowstoexplode = new ArrayList<EntityArrow>();

        for(Entity entity : entities) {
            if (entity instanceof EntityArrow) {
                NBTTagCompound nbttagcompound = entity.writeToNBT(new NBTTagCompound());
                IArrowProperties cap = (IArrowProperties)entity.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
                if (cap != null && nbttagcompound.getByte("inGround") == 1 && cap.getExplosionPower() > 0.0F) {
                    arrowstoexplode.add((EntityArrow)entity);
                }
            }
        }

        for(EntityArrow arrow : arrowstoexplode) {
            IArrowProperties cap = (IArrowProperties)arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
            arrow.world.newExplosion(arrow.shootingEntity, arrow.posX, arrow.posY, arrow.posZ, cap.getExplosionPower(), arrow.isBurning(), false);
            if (arrow instanceof EntityTippedArrow) {
                NBTTagCompound compound = arrow.writeToNBT(new NBTTagCompound());
                ArrayList<PotionEffect> effects = new ArrayList();
                if (compound.hasKey("Potion", 8)) {
                    PotionType potion = PotionUtils.getPotionTypeFromNBT(compound);

                    for(PotionEffect potioneffect : potion.getEffects()) {
                        effects.add(
                                new PotionEffect(
                                        potioneffect.getPotion(),
                                        potioneffect.getDuration() / 8,
                                        potioneffect.getAmplifier(),
                                        potioneffect.getIsAmbient(),
                                        potioneffect.doesShowParticles()
                                )
                        );
                    }
                }

                effects.addAll(PotionUtils.getFullEffectsFromTag(compound));
                if (!effects.isEmpty()) {
                    EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(arrow.world, arrow.posX, arrow.posY, arrow.posZ);
                    entityareaeffectcloud.setRadius(2.5F * cap.getExplosionPower());
                    entityareaeffectcloud.setRadiusOnUse(-0.5F);
                    entityareaeffectcloud.setWaitTime(10);
                    entityareaeffectcloud.setDuration(entityareaeffectcloud.getDuration() / 2);
                    entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());

                    for(PotionEffect potioneffect : effects) {
                        entityareaeffectcloud.addEffect(new PotionEffect(potioneffect));
                    }

                    arrow.world.spawnEntity(entityareaeffectcloud);
                }
            }

            arrow.setDead();
        }
    }
}