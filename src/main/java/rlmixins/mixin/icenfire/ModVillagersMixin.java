package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.core.ModItems;
import com.github.alexthe666.iceandfire.core.ModVillagers;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModVillagers.class)
public abstract class ModVillagersMixin {

    @Redirect(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/fml/common/registry/VillagerRegistry$VillagerCareer;addTrade(I[Lnet/minecraft/entity/passive/EntityVillager$ITradeList;)Lnet/minecraftforge/fml/common/registry/VillagerRegistry$VillagerCareer;",
            ordinal = 86),
            remap = false
    )
    public VillagerRegistry.VillagerCareer rlmixins_iceAndFireModVillagers_init_goldIngot0(VillagerRegistry.VillagerCareer career, int level, EntityVillager.ITradeList... trades) {
        return career.addTrade(
                2,
                new EntityMyrmexBase.BasicTrade(new ItemStack(Items.GOLD_INGOT), new ItemStack(ModItems.myrmex_desert_resin), new EntityVillager.PriceInfo(2, 4), new EntityVillager.PriceInfo(2, 4)));
    }

    @Redirect(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/fml/common/registry/VillagerRegistry$VillagerCareer;addTrade(I[Lnet/minecraft/entity/passive/EntityVillager$ITradeList;)Lnet/minecraftforge/fml/common/registry/VillagerRegistry$VillagerCareer;",
                    ordinal = 87),
            remap = false
    )
    public VillagerRegistry.VillagerCareer rlmixins_iceAndFireModVillagers_init_silverIngot0(VillagerRegistry.VillagerCareer career, int level, EntityVillager.ITradeList... trades) {
        return career.addTrade(
                2,
                new EntityMyrmexBase.BasicTrade(new ItemStack(ModItems.silverIngot), new ItemStack(ModItems.myrmex_desert_resin), new EntityVillager.PriceInfo(2, 4), new EntityVillager.PriceInfo(2, 4)));
    }

    @Redirect(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/fml/common/registry/VillagerRegistry$VillagerCareer;addTrade(I[Lnet/minecraft/entity/passive/EntityVillager$ITradeList;)Lnet/minecraftforge/fml/common/registry/VillagerRegistry$VillagerCareer;",
                    ordinal = 96),
            remap = false
    )
    public VillagerRegistry.VillagerCareer rlmixins_iceAndFireModVillagers_init_goldIngot1(VillagerRegistry.VillagerCareer career, int level, EntityVillager.ITradeList... trades) {
        return career.addTrade(
                2,
                new EntityMyrmexBase.BasicTrade(new ItemStack(Items.GOLD_INGOT), new ItemStack(ModItems.myrmex_jungle_resin), new EntityVillager.PriceInfo(2, 4), new EntityVillager.PriceInfo(2, 4)));
    }

    @Redirect(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/fml/common/registry/VillagerRegistry$VillagerCareer;addTrade(I[Lnet/minecraft/entity/passive/EntityVillager$ITradeList;)Lnet/minecraftforge/fml/common/registry/VillagerRegistry$VillagerCareer;",
                    ordinal = 97),
            remap = false
    )
    public VillagerRegistry.VillagerCareer rlmixins_iceAndFireModVillagers_init_silverIngot1(VillagerRegistry.VillagerCareer career, int level, EntityVillager.ITradeList... trades) {
        return career.addTrade(
                2,
                new EntityMyrmexBase.BasicTrade(new ItemStack(ModItems.silverIngot), new ItemStack(ModItems.myrmex_jungle_resin), new EntityVillager.PriceInfo(2, 4), new EntityVillager.PriceInfo(2, 4)));
    }
}