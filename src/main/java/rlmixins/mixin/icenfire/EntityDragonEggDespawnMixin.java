package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.entity.EntityDragonEgg;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityDragonEgg.class)
public abstract class EntityDragonEggDespawnMixin extends EntityLiving {
    public EntityDragonEggDespawnMixin(World worldIn) {
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
