package rlmixins.mixin.mobends;

import goblinbob.mobends.core.bender.EntityBender;
import goblinbob.mobends.core.bender.EntityBenderRegistry;
import goblinbob.mobends.standard.main.ModConfig;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.Map;

@Mixin(EntityBenderRegistry.class)
public abstract class EntityBenderRegistryMixin {

    @Shadow(remap = false)
    @Final
    private Map<Class<? extends EntityLivingBase>, EntityBender<?>> entityClassToBenderMap;

    private final Map<Class<? extends EntityLivingBase>, EntityBender<?>> entityClassToBenderMapCache = new HashMap<>();

    /**
     * @author fonnymunkey
     * @reason fix memory leak found by Meldexun
     */
    @SuppressWarnings("unchecked")
    @Overwrite(remap = false)
    public <E extends EntityLivingBase> EntityBender<E> getForEntity(E entity) {
        return (EntityBender<E>)this.entityClassToBenderMapCache.computeIfAbsent(entity.getClass(), key -> {
            if(ModConfig.shouldKeepEntityAsVanilla(entity)) {
                return null;
            }
            else {
                for(EntityBender<?> entityBender : this.entityClassToBenderMap.values()) {
                    if(entityBender.entityClass.equals(key)) {
                        return entityBender;
                    }
                }
                for(EntityBender<?> entityBender : this.entityClassToBenderMap.values()) {
                    if(entityBender.entityClass.isInstance(key)) {
                        return entityBender;
                    }
                }
                return null;
            }
        });
    }

    /**
     * @author fonnymunkey
     * @reason fix memory leak found by Meldexun
     */
    @Overwrite(remap = false)
    public <E extends EntityLivingBase> void clearCache(E entity) {
        this.entityClassToBenderMapCache.clear();
    }

    /**
     * @author fonnymunkey
     * @reason fix memory leak found by Meldexun
     */
    @Overwrite(remap = false)
    public void clearCache() {
        this.entityClassToBenderMapCache.clear();
    }
}