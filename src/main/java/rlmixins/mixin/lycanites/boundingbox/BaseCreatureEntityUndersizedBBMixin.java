package rlmixins.mixin.lycanites.boundingbox;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.creature.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import rlmixins.handlers.ForgeConfigHandler;
import rlmixins.handlers.LycanitesBBHandler;

import javax.annotation.Nullable;
import java.util.function.Function;

@Mixin(value = {
        EntityAfrit.class,
        EntityArix.class,
        EntityBalayang.class,
        EntityBarghest.class,
        EntityCockatrice.class,
        EntityIgnibus.class,
        EntityMakaAlpha.class,
        EntityMaka.class,
        EntityQuetzodracl.class,
        EntityQuillbeast.class,
        EntityRaiko.class,
        EntityRoc.class,
        EntitySkylus.class,
        EntityTriffid.class,
        EntityUvaraptor.class,
        EntityVentoraptor.class,
        EntityYale.class,
        EntityZoataur.class})
public abstract class BaseCreatureEntityUndersizedBBMixin extends BaseCreatureEntity {

    public BaseCreatureEntityUndersizedBBMixin(World world) { super(world); }

    /**
     * Fix undersized render bounding box
     */
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if(ForgeConfigHandler.client.targetEntity.equals(this.getClass().getSimpleName())) return this.getEntityBoundingBox().grow(ForgeConfigHandler.client.growXZ, ForgeConfigHandler.client.growY, ForgeConfigHandler.client.growXZ).offset(0.0, ForgeConfigHandler.client.offsetY, 0.0);
        return execNullable(
                LycanitesBBHandler.lookup.get(this.getClass().getSimpleName()),
                e -> this.getEntityBoundingBox().grow(e[0], e[1], e[0]).offset(0, e[2], 0),
                this.getEntityBoundingBox());
    }

    /**
     * Based on method from BetterCombat
     */
    private static <T, R> R execNullable(@Nullable T obj, Function<T, R> onNonNull, R orElse) {
        if(obj != null) return onNonNull.apply(obj);
        return orElse;
    }
}