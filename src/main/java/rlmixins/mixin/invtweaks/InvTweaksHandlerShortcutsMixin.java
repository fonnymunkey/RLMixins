package rlmixins.mixin.invtweaks;

import com.Shultrea.Rin.Enchantments_Sector.Smc_040;
import invtweaks.InvTweaksHandlerShortcuts;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.stream.Stream;

@Mixin(InvTweaksHandlerShortcuts.class)
public abstract class InvTweaksHandlerShortcutsMixin {

    @Redirect(
            method = "dropAll",
            at = @At(value = "INVOKE", target = "Ljava/util/List;stream()Ljava/util/stream/Stream;"),
            remap = false
    )
    public Stream<Slot> rlmixins_inventorytweaksInvTweaksHandlerShortCuts_dropAll(List<Slot> instance) {
        return instance.stream().filter(slot -> slot.getHasStack() && !(EnchantmentHelper.getEnchantmentLevel(Smc_040.CurseofPossession, slot.getStack()) > 0));
    }
}