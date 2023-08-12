package rlmixins.mixin.mobends;

import goblinbob.mobends.core.bender.EntityBenderRegistry;
import goblinbob.mobends.core.bender.PreviewHelper;
import goblinbob.mobends.core.data.EntityData;
import goblinbob.mobends.core.data.EntityDatabase;
import goblinbob.mobends.core.data.IEntityDataFactory;
import goblinbob.mobends.core.data.LivingEntityData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

@Mixin(EntityDatabase.class)
public abstract class EntityDatabaseMixin {

    protected final Map<Entity, LivingEntityData<?>> entryMapNew = new WeakHashMap<>();

    /**
     * @author fonnymunkey
     * @reason Fix mem leak found by Meldexun
     */
    @Overwrite(remap = false)
    @SuppressWarnings("unchecked")
    public <T extends LivingEntityData<E>, E extends EntityLivingBase> T get(E entity) {
        return (T)this.entryMapNew.get(entity);
    }

    /**
     * @author fonnymunkey
     * @reason Fix mem leak found by Meldexun
     */
    @Overwrite(remap = false)
    @SuppressWarnings("unchecked")
    public <T extends LivingEntityData<E>, E extends EntityLivingBase> T getOrMake(IEntityDataFactory<E> dataCreationFunction, E entity) {
        T data = this.get(entity);
        if(data == null) {
            data = (T)dataCreationFunction.createEntityData(entity);
            this.add(entity, data);
        }
        return data;
    }

    /**
     * @author fonnymunkey
     * @reason Fix mem leak found by Meldexun
     */
    @Overwrite(remap = false)
    public void add(Entity entity, LivingEntityData<?> data) {
        this.entryMapNew.put(entity, data);
    }

    /**
     * @author fonnymunkey
     * @reason Fix mem leak found by Meldexun
     */
    @Overwrite(remap = false)
    public void updateClient() {
        Iterator<Map.Entry<Entity, LivingEntityData<?>>> it = this.entryMapNew.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry<Entity, LivingEntityData<?>> entry = it.next();
            LivingEntityData<?> entityData = entry.getValue();
            EntityLivingBase entityInData = entityData.getEntity();
            Entity entity = entry.getKey();
            if(PreviewHelper.isPreviewEntity(entityInData) || entity != null && entityInData == entity) {
                entityData.updateClient();
            }
            else {
                EntityBenderRegistry.instance.clearCache(entityInData);
                it.remove();
            }
        }
    }

    /**
     * @author fonnymunkey
     * @reason Fix mem leak found by Meldexun
     */
    @Overwrite(remap = false)
    public void updateRender(float partialTicks) {
        for(EntityData<?> entityData : this.entryMapNew.values()) {
            entityData.update(partialTicks);
        }
    }

    /**
     * @author fonnymunkey
     * @reason Fix mem leak found by Meldexun
     */
    @Overwrite(remap = false)
    public void refresh() {
        this.entryMapNew.clear();
    }

    /**
     * @author fonnymunkey
     * @reason Fix mem leak found by Meldexun
     */
    @Overwrite(remap = false)
    public void onTicksRestart() {
        this.entryMapNew.values().forEach(EntityData::onTicksRestart);
    }
}