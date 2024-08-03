package rlmixins.mixin.vanilla;


import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityGiantZombie.class)
public class EntityGiantZombieMixin extends EntityMob {

    public EntityGiantZombieMixin(World p_i1738_1_) {
        super(p_i1738_1_);
    }

    @Override
    public boolean getCanSpawnHere() {
        IBlockState state = this.world.getBlockState((new BlockPos(this)).down());
        return state.canEntitySpawn(this);
    }
}
