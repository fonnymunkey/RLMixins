package rlmixins.mixin.variedcommodities;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import noppes.vc.VCItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(VCItems.class)
public abstract class VCItemsMixin {
	
	@Inject(
			method = "registerItems",
			at = @At(value = "INVOKE", target = "Lnoppes/vc/VCItems;register(Lnet/minecraftforge/event/RegistryEvent$Register;Lnet/minecraft/item/Item;)V", ordinal = 8),
			locals = LocalCapture.CAPTURE_FAILHARD,
			remap = false
	)
	public void rlmixins_variedCommoditiesVCItems_registerItems(RegistryEvent.Register<Item> event, CallbackInfo ci, Item gem_sapphire, Item gem_ruby, Item gem_amethyst, Item ingot_bronze, Item ingot_steel, Item ingot_demonic, Item ingot_mithril) {
		gem_ruby.setTranslationKey("gem_ruby_vc");
	}
}