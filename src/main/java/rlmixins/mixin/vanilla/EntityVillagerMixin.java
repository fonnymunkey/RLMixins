package rlmixins.mixin.vanilla;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import rlmixins.wrapper.IEntityVillagerMixin;

import javax.annotation.Nullable;

@Mixin(EntityVillager.class)
public abstract class EntityVillagerMixin implements IEntityVillagerMixin {
    
    @Shadow public abstract void setProfession(int professionId);
    
    @Shadow(remap = false) public abstract void setProfession(VillagerRegistry.VillagerProfession prof);
    
    @Shadow private int wealth;
    
    @Shadow private int careerId;
    
    @Shadow private int careerLevel;
    
    @Shadow @Nullable private MerchantRecipeList buyingList;
    
    @Unique
    @Override
    public void rlmixins$readTradesFromNBT(NBTTagCompound compound) {
        this.setProfession(compound.getInteger("Profession"));
        if(compound.hasKey("ProfessionName")) {
            VillagerRegistry.VillagerProfession p = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation(compound.getString("ProfessionName")));
            if(p == null) p = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:farmer"));
            this.setProfession(p);
        }
        this.wealth = compound.getInteger("Riches");
        this.careerId = compound.getInteger("Career");
        this.careerLevel = compound.getInteger("CareerLevel");

        if(compound.hasKey("Offers", 10)) {
            NBTTagCompound nbttagcompound = compound.getCompoundTag("Offers");
            this.buyingList = new MerchantRecipeList(nbttagcompound);
        }
    }
}