package rlmixins.mixin.icenfire.boundingbox;

import com.github.alexthe666.iceandfire.entity.EntityDeathWorm;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityDeathWorm.class)
public abstract class EntityDeathwormRenderMixin extends Entity {

    public EntityDeathwormRenderMixin(World worldIn) { super(worldIn); }

    /**
     * Fix undersized render bounding box
     */
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        //if(ForgeConfigHandler.client.targetEntity.equals("deathworm")) return this.getEntityBoundingBox().grow((this.getEntityBoundingBox().maxX-this.getEntityBoundingBox().minX)*ForgeConfigHandler.client.growXZ, ForgeConfigHandler.client.growY, (this.getEntityBoundingBox().maxZ-this.getEntityBoundingBox().minZ)*ForgeConfigHandler.client.growXZ).offset(0.0, ForgeConfigHandler.client.offsetY, 0.0);
        //else return super.getRenderBoundingBox();
        return this.getEntityBoundingBox().grow((this.getEntityBoundingBox().maxX-this.getEntityBoundingBox().minX)*7, 0, (this.getEntityBoundingBox().maxZ-this.getEntityBoundingBox().minZ)*7);
    }
}