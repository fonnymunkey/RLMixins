package rlmixins.mixin.vanilla.zombietrades;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityZombie.class)
public abstract class EntityZombieMixin {

    @WrapOperation(
            method = "onKillEntity",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z")
    )
    public boolean rlmixins_vanillaEntityZombie_spawnEntity(World world, Entity newZombieVillager, Operation<Boolean> original, @Local(name = "entityvillager") EntityVillager deadVillager) {
        NBTTagCompound villagerTags = new NBTTagCompound();
        deadVillager.writeToNBT(villagerTags);

        NBTTagCompound keptTags = new NBTTagCompound();
        keptTags.setInteger("Profession", villagerTags.getInteger("Profession"));
        keptTags.setString("ProfessionName", villagerTags.getString("ProfessionName"));
        keptTags.setInteger("Riches", villagerTags.getInteger("Riches"));
        keptTags.setInteger("Career", villagerTags.getInteger("Career"));
        keptTags.setInteger("CareerLevel", villagerTags.getInteger("CareerLevel"));
        keptTags.setTag("Offers", villagerTags.getTag("Offers"));

        newZombieVillager.getEntityData().setTag("villagerTags", keptTags);
        return original.call(world, newZombieVillager);
    }
}