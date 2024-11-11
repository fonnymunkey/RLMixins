package rlmixins.mixin.vanilla;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import rlmixins.handlers.vanilla.ZombieTraderInterface;

@Mixin(EntityVillager.class)
public abstract class EntityVillagerMixin implements ZombieTraderInterface {

    @Shadow
    private int wealth;
    @Shadow
    private int careerId;
    @Shadow
    private int careerLevel;
    @Shadow
    private MerchantRecipeList buyingList;
    @Shadow
    public void setProfession(int professionId) {}
    @Shadow(remap = false)
    public void setProfession(VillagerRegistry.VillagerProfession prof){}

    @Override
    public void rlmixins_readTradesFromNBT(NBTTagCompound compound){
        this.setProfession(compound.getInteger("Profession"));
        if (compound.hasKey("ProfessionName"))
        {
            net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession p =
                    net.minecraftforge.fml.common.registry.ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new net.minecraft.util.ResourceLocation(compound.getString("ProfessionName")));
            if (p == null)
                p = net.minecraftforge.fml.common.registry.ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new net.minecraft.util.ResourceLocation("minecraft:farmer"));
            this.setProfession(p);
        }
        this.wealth = compound.getInteger("Riches");
        this.careerId = compound.getInteger("Career");
        this.careerLevel = compound.getInteger("CareerLevel");

        if (compound.hasKey("Offers", 10))
        {
            NBTTagCompound nbttagcompound = compound.getCompoundTag("Offers");
            this.buyingList = new MerchantRecipeList(nbttagcompound);
        }
    }
}