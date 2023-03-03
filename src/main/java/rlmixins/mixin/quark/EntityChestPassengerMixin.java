package rlmixins.mixin.quark;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import vazkii.quark.management.entity.EntityChestPassenger;

import javax.annotation.Nullable;

@Mixin(EntityChestPassenger.class)
public abstract class EntityChestPassengerMixin extends Entity implements IInventory {

    public EntityChestPassengerMixin(World worldIn) {
        super(worldIn);
    }

    private boolean dropContentsWhenDead = true;

    @Override
    @Nullable
    public Entity changeDimension(int dimensionIn, ITeleporter teleporter) {
        this.dropContentsWhenDead = false;
        return super.changeDimension(dimensionIn, teleporter);
    }

    /**
     * @author fonnymunkey
     * @reason fix dupe bug
     */
    @Overwrite
    public void setDropItemsWhenDead(boolean value) {
        this.dropContentsWhenDead = value;
    }

    /**
     * @author fonnymunkey
     * @reason fix dupe bug
     */
    @Overwrite
    public void setDead() {
        if(!this.world.isRemote && this.dropContentsWhenDead) {
            InventoryHelper.dropInventoryItems(this.world, this, this);
            InventoryHelper.spawnItemStack(this.world, this.posX, this.posY, this.posZ, ((EntityChestPassenger)(Object)this).getChestType());
        }
        super.setDead();
    }
}