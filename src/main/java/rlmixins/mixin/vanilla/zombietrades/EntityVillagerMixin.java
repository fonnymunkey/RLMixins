package rlmixins.mixin.vanilla.zombietrades;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import rlmixins.handlers.ConfigHandler;
import rlmixins.wrapper.IEntityVillagerMixin;
import rlmixins.wrapper.IMerchantRecipeMixin;

import javax.annotation.Nullable;

@Mixin(EntityVillager.class)
public abstract class EntityVillagerMixin extends EntityLivingBase implements IEntityVillagerMixin {
    public EntityVillagerMixin(World worldIn) {
        super(worldIn);
    }

    @Shadow public abstract void setProfession(int professionId);
    @Shadow(remap = false) public abstract void setProfession(VillagerRegistry.VillagerProfession prof);
    @Shadow private int wealth;
    @Shadow private int careerId;
    @Shadow private int careerLevel;
    @Shadow @Nullable private MerchantRecipeList buyingList;

    @Override
    public void rlmixins$setTradesFromNBT(NBTTagCompound compound) {
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

            //Make trades worse :)
            for(MerchantRecipe recipe : this.buyingList){
                if(this.getRNG().nextFloat() < ConfigHandler.VANILLA_CONFIG.zombiesKeepTrades_chancePriceIncrease)
                    ((IMerchantRecipeMixin) recipe).rlmixins$$increasePrices();
                if(this.getRNG().nextFloat() < ConfigHandler.VANILLA_CONFIG.zombiesKeepTrades_chancePriceIncrease)
                    ((IMerchantRecipeMixin) recipe).rlmixins$$denyXP();
            }
        }
    }
}