package rlmixins.wrapper;

import net.minecraft.nbt.NBTTagCompound;

public interface IEntityVillagerMixin {
    void rlmixins$readTradesFromNBT(NBTTagCompound compound);
}