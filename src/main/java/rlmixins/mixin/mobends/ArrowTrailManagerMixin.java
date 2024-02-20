package rlmixins.mixin.mobends;

import goblinbob.mobends.standard.client.renderer.entity.ArrowTrailManager;
import net.minecraft.entity.projectile.EntityArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import rlmixins.mixin.vanilla.EntityArrowAccessor;

import static goblinbob.mobends.standard.client.renderer.entity.ArrowTrailManager.getOrMake;

@Mixin(ArrowTrailManager.class)
public abstract class ArrowTrailManagerMixin {

    /**
     * @author fonnymunkey
     * @reason fix arrows in ground
     */
    @Overwrite(remap = false)
    public static void renderTrail(EntityArrow entity, double x, double y, double z, float partialTicks) {
        if(entity != null && !entity.isDead && !((EntityArrowAccessor)entity).getInGround())
            getOrMake(entity).render(x, y, z, partialTicks);
    }
}