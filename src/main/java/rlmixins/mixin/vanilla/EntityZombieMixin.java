package rlmixins.mixin.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityZombie.class)
public abstract class EntityZombieMixin {

    @Unique
    NBTTagCompound rlmixins$killedVillagerTags = new NBTTagCompound();

    @Redirect(
            method = "onKillEntity",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;removeEntity(Lnet/minecraft/entity/Entity;)V")
    )
    public void rlmixins_vanillaEntityZombie_removeEntity(World instance, Entity entityIn){
        NBTTagCompound tmpVillagerTags = new NBTTagCompound();
        entityIn.writeToNBT(tmpVillagerTags);
        
        rlmixins$killedVillagerTags.setInteger("Profession", tmpVillagerTags.getInteger("Profession"));
        rlmixins$killedVillagerTags.setString("ProfessionName", tmpVillagerTags.getString("ProfessionName"));
        rlmixins$killedVillagerTags.setInteger("Riches", tmpVillagerTags.getInteger("Riches"));
        rlmixins$killedVillagerTags.setInteger("Career", tmpVillagerTags.getInteger("Career"));
        rlmixins$killedVillagerTags.setInteger("CareerLevel", tmpVillagerTags.getInteger("CareerLevel"));
        rlmixins$killedVillagerTags.setTag("Offers", tmpVillagerTags.getTag("Offers"));

        instance.removeEntity(entityIn);
    }

    @Redirect(
            method = "onKillEntity",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z")
    )
    public boolean rlmixins_vanillaEntityZombie_spawnEntity(World instance, Entity entity) {
        EntityZombieVillager zombieVillager = (EntityZombieVillager)entity;
        zombieVillager.getEntityData().setTag("villagerTags", rlmixins$killedVillagerTags);
        return instance.spawnEntity(entity);
    }
}