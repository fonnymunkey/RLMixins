package rlmixins.mixin.mobends;

import goblinbob.mobends.standard.client.renderer.entity.ArrowTrail;
import goblinbob.mobends.standard.client.renderer.entity.ArrowTrailManager;
import net.minecraft.entity.projectile.EntityArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import rlmixins.mixin.vanilla.EntityArrowAccessor;

@Mixin(ArrowTrailManager.class)
public abstract class ArrowTrailManagerMixin {

    @Shadow(remap = false)
    public static ArrowTrail getOrMake(EntityArrow arrow) {
        return null;
    }

    /**
     * @author fonnymunkey
     * @reason fix arrows in ground or not added to world being added to trail rendering
     */
    @Overwrite(remap = false)
    public static void renderTrail(EntityArrow entity, double x, double y, double z, float partialTicks) {
        if(entity != null && !entity.isDead && !((EntityArrowAccessor)entity).getInGround() && entity.isAddedToWorld()) {
            getOrMake(entity).render(x, y, z, partialTicks);
        }
    }
}