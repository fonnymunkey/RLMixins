package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityDragonBase.class)
public abstract class EntityDragonBaseDespawnMixin extends EntityTameable {
    public EntityDragonBaseDespawnMixin(World worldIn) {
        super(worldIn);
    }

    @Override
    public boolean canDespawn() { return false; }

    @Override
    public boolean isNoDespawnRequired()
    {
        return true;
    }
}
