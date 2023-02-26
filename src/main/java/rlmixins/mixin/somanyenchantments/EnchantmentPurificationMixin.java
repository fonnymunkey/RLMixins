package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_1_0.EnchantmentPurification;
import com.Shultrea.Rin.Enchantment_Base_Sector.EnchantmentBase;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.handlers.ForgeConfigHandler;
import rlmixins.wrapper.SMEWrapper;
import rlmixins.wrapper.SpawnerControlWrapper;

@Mixin(EnchantmentPurification.class)
public abstract class EnchantmentPurificationMixin extends EnchantmentBase {

    public EnchantmentPurificationMixin(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @ModifyArg(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lcom/Shultrea/Rin/Enchantment_Base_Sector/EnchantmentBase;<init>(Lnet/minecraft/enchantment/Enchantment$Rarity;Lnet/minecraft/enchantment/EnumEnchantmentType;[Lnet/minecraft/inventory/EntityEquipmentSlot;)V")
    )
    private static EnumEnchantmentType rlmixins_soManyEnchantmentsEnchantmentPurification_init(EnumEnchantmentType type) {
        return SMEWrapper.getCombatAxeType();
    }

    @Override
    public boolean canApply(ItemStack fTest) {
        return super.canApply(fTest);
    }

    @Inject(
            method = "repeat",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;setSilent(Z)V", shift = At.Shift.BEFORE, ordinal = 0)
    )
    public void rlmixins_soManyEnchantmentsEnchantmentPurification_repeat0(EntityLivingBase eBase, CallbackInfoReturnable<Boolean> cir) {
        if(ForgeConfigHandler.mixinConfig.curingTicksSpawners) SpawnerControlWrapper.increaseSpawnerCount(eBase);
    }

    @Inject(
            method = "repeat",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;setSilent(Z)V", shift = At.Shift.BEFORE, ordinal = 1)
    )
    public void rlmixins_soManyEnchantmentsEnchantmentPurification_repeat1(EntityLivingBase eBase, CallbackInfoReturnable<Boolean> cir) {
        if(ForgeConfigHandler.mixinConfig.curingTicksSpawners) SpawnerControlWrapper.increaseSpawnerCount(eBase);
    }

    @Inject(
            method = "repeat",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;setSilent(Z)V", shift = At.Shift.BEFORE, ordinal = 2)
    )
    public void rlmixins_soManyEnchantmentsEnchantmentPurification_repeat2(EntityLivingBase eBase, CallbackInfoReturnable<Boolean> cir) {
        if(ForgeConfigHandler.mixinConfig.curingTicksSpawners) SpawnerControlWrapper.increaseSpawnerCount(eBase);
    }
}