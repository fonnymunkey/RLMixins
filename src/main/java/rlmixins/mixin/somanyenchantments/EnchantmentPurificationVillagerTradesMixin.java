package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_1_0.EnchantmentPurification;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.DifficultyInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.handlers.vanilla.ZombieTraderInterface;

@Mixin(EnchantmentPurification.class)
public abstract class EnchantmentPurificationVillagerTradesMixin {

    @Unique
    EntityLivingBase rlmixins_target;

    @Inject(
            method = "repeat",
            at = @At(value = "HEAD"),
            remap = false
    )
    void rlmixins_saveZombieVillager(EntityLivingBase eBase, CallbackInfoReturnable<Boolean> cir){
        rlmixins_target =  eBase;
    }

    @Redirect(
            method = "repeat",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/EntityVillager;finalizeMobSpawn(Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/entity/IEntityLivingData;Z)Lnet/minecraft/entity/IEntityLivingData;")
    )
    IEntityLivingData rlmixins_getOldTrades(EntityVillager villager, DifficultyInstance difficultyInstance, IEntityLivingData entityLivingData, boolean doSetProfession){
        IEntityLivingData returnValue = villager.finalizeMobSpawn(difficultyInstance, entityLivingData, doSetProfession);

        if(!rlmixins_target.getEntityData().hasKey("villagerTags")) return returnValue;
        if(!(rlmixins_target instanceof EntityZombieVillager)) return returnValue;

        NBTTagCompound villagerTags = (NBTTagCompound) rlmixins_target.getEntityData().getTag("villagerTags");
        ZombieTraderInterface curedVillager = (ZombieTraderInterface) villager;
        curedVillager.rlmixins_readTradesFromNBT(villagerTags);

        return returnValue;
    }
}