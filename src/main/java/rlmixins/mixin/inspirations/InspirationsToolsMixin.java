package rlmixins.mixin.inspirations;

import knightminer.inspirations.tools.InspirationsTools;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InspirationsTools.class)
public abstract class InspirationsToolsMixin {

    @Redirect(
            method = "registerEnchantments",
            at = @At(value = "INVOKE", target = "Lknightminer/inspirations/tools/InspirationsTools;register(Lnet/minecraftforge/registries/IForgeRegistry;Lnet/minecraftforge/registries/IForgeRegistryEntry;Lnet/minecraft/util/ResourceLocation;)Lnet/minecraftforge/registries/IForgeRegistryEntry;", ordinal = 0),
            remap = false
    )
    public IForgeRegistryEntry rlmixins_inspirationsInspirationsTools_registerEnchantments_0(IForgeRegistry iForgeRegistry, IForgeRegistryEntry iForgeRegistryEntry, ResourceLocation resourceLocation) {
        return null;
    }
    @Redirect(
            method = "registerEnchantments",
            at = @At(value = "INVOKE", target = "Lknightminer/inspirations/tools/InspirationsTools;register(Lnet/minecraftforge/registries/IForgeRegistry;Lnet/minecraftforge/registries/IForgeRegistryEntry;Lnet/minecraft/util/ResourceLocation;)Lnet/minecraftforge/registries/IForgeRegistryEntry;", ordinal = 1),
            remap = false
    )
    public IForgeRegistryEntry rlmixins_inspirationsInspirationsTools_registerEnchantments_1(IForgeRegistry iForgeRegistry, IForgeRegistryEntry iForgeRegistryEntry, ResourceLocation resourceLocation) {
        return null;
    }
    @Redirect(
            method = "registerEnchantments",
            at = @At(value = "INVOKE", target = "Lknightminer/inspirations/tools/InspirationsTools;register(Lnet/minecraftforge/registries/IForgeRegistry;Lnet/minecraftforge/registries/IForgeRegistryEntry;Lnet/minecraft/util/ResourceLocation;)Lnet/minecraftforge/registries/IForgeRegistryEntry;", ordinal = 2),
            remap = false
    )
    public IForgeRegistryEntry rlmixins_inspirationsInspirationsTools_registerEnchantments_2(IForgeRegistry iForgeRegistry, IForgeRegistryEntry iForgeRegistryEntry, ResourceLocation resourceLocation) {
        return null;
    }
    @Redirect(
            method = "registerEnchantments",
            at = @At(value = "INVOKE", target = "Lknightminer/inspirations/tools/InspirationsTools;register(Lnet/minecraftforge/registries/IForgeRegistry;Lnet/minecraftforge/registries/IForgeRegistryEntry;Lnet/minecraft/util/ResourceLocation;)Lnet/minecraftforge/registries/IForgeRegistryEntry;", ordinal = 3),
            remap = false
    )
    public IForgeRegistryEntry rlmixins_inspirationsInspirationsTools_registerEnchantments_3(IForgeRegistry iForgeRegistry, IForgeRegistryEntry iForgeRegistryEntry, ResourceLocation resourceLocation) {
        return null;
    }
}