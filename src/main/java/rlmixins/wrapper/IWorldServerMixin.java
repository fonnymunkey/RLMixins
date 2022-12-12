package rlmixins.wrapper;

import net.minecraft.entity.Entity;

public interface IWorldServerMixin {
    void prepareLeaveDimension(Entity entity);
}
