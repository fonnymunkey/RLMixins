package rlmixins.mixin.icenfire.boundingbox;

import com.github.alexthe666.iceandfire.entity.EntityCyclops;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityCyclops.class)
public abstract class EntityCyclopsRenderMixin extends Entity {

    public EntityCyclopsRenderMixin(World worldIn) { super(worldIn); }

    /**
     * Fix undersized render bounding box
     */
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        //if(ForgeConfigHandler.client.targetEntity.equals("cyclops")) return this.getEntityBoundingBox().grow(ForgeConfigHandler.client.growXZ, ForgeConfigHandler.client.growY, ForgeConfigHandler.client.growXZ).offset(0.0, ForgeConfigHandler.client.offsetY, 0.0);
        //else return super.getRenderBoundingBox();
        return this.getEntityBoundingBox().grow(1.0, 1.0, 1.0);
    }
}