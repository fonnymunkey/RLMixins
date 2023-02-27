package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_4_0.EnchantmentSplitshot;
import com.Shultrea.Rin.Enchantment_Base_Sector.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.wrapper.BetterSurvivalWrapper;
import rlmixins.wrapper.InFWrapper;
import rlmixins.wrapper.SwitchbowWrapper;

@Mixin(EnchantmentSplitshot.class)
public abstract class EnchantmentSplitshotMixin extends EnchantmentBase {

    public EnchantmentSplitshotMixin(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Shadow(remap = false) protected abstract ItemStack findAmmo(EntityPlayer player);

    private ItemStack bow = ItemStack.EMPTY;
    private static final ResourceLocation SWITCHBOW_REGISTRY = new ResourceLocation("switchbow", "switchBow");
    private static final ResourceLocation DRAGONBOW_REGISTRY = new ResourceLocation("iceandfire", "dragonbone_bow");

    @Inject(
            method = "onEvent",
            at = @At(value = "INVOKE", target = "Lcom/Shultrea/Rin/Ench0_4_0/EnchantmentSplitshot;findAmmo(Lnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;", shift = At.Shift.BEFORE),
            remap = false
    )
    public void rlmixins_soManyEnchantmentsEnchantmentSplitshot_onEventInject(ArrowLooseEvent event, CallbackInfo ci) {
        bow = event.getBow();
    }

    @Redirect(
            method = "onEvent",
            at = @At(value = "INVOKE", target = "Lcom/Shultrea/Rin/Ench0_4_0/EnchantmentSplitshot;findAmmo(Lnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;"),
            remap = false
    )
    public ItemStack rlmixins_soManyEnchantmentsEnchantmentSplitshot_onEventRedirect(EnchantmentSplitshot instance, EntityPlayer i) {
        if(bow.getItem().getRegistryName().equals(SWITCHBOW_REGISTRY)) return SwitchbowWrapper.getAmmo(i, bow);
        if(bow.getItem().getRegistryName().equals(DRAGONBOW_REGISTRY)) return InFWrapper.getDragonBowAmmo(i);
        else return findAmmo(i);
    }

    @Override
    public boolean canApplyTogether(Enchantment enchant) {
        return super.canApplyTogether(enchant) && (!Loader.isModLoaded("mujmajnkraftsbettersurvival") || enchant != BetterSurvivalWrapper.getMultishot());
    }
}