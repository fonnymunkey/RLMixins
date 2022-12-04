package rlmixins.mixin.icenfire.boundingbox;

import com.github.alexthe666.iceandfire.entity.EntityAmphithere;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityAmphithere.class)
public abstract class EntityAmphithereRenderMixin extends Entity {

    public EntityAmphithereRenderMixin(World worldIn) { super(worldIn); }

    /**
     * Fix undersized render bounding box
     */
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        //if(ForgeConfigHandler.client.targetEntity.equals("amphithere")) return this.getEntityBoundingBox().grow(ForgeConfigHandler.client.growXZ, ForgeConfigHandler.client.growY, ForgeConfigHandler.client.growXZ).offset(0.0, ForgeConfigHandler.client.offsetY, 0.0);
        //else return super.getRenderBoundingBox();
        return this.getEntityBoundingBox().grow(5.0, 3.0, 5.0);
    }
}
