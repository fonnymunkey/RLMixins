package rlmixins.mixin.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.wrapper.IEntityVillagerMixin;

@Mixin(EntityZombieVillager.class)
public abstract class EntityZombieVillagerTradesMixin extends Entity {

    public EntityZombieVillagerTradesMixin(World worldIn) {
        super(worldIn);
    }

    @Redirect(
            method = "finishConversion",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z")
    )
    public boolean rlmixins_vanillaEntityZombieVillager_finishConversion(World instance, Entity entity) {
        if(!this.getEntityData().hasKey("villagerTags")) return instance.spawnEntity(entity);
        NBTTagCompound villagerTags = (NBTTagCompound)this.getEntityData().getTag("villagerTags");
        ((IEntityVillagerMixin)entity).rlmixins$readTradesFromNBT(villagerTags);
        return instance.spawnEntity(entity);
    }
}