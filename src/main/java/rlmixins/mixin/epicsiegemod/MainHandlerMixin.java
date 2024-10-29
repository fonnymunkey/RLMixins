package rlmixins.mixin.epicsiegemod;

import funwayguy.epicsiegemod.core.ESM_Settings;
import funwayguy.epicsiegemod.handlers.MainHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MainHandler.class)
public abstract class MainHandlerMixin  {

    @Shadow(remap = false) private static boolean hooksReady;

    @Inject(
            method = "onEntityConstruct",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_epicSiegeModMainHandler_onEntityConstruct(EntityJoinWorldEvent event, CallbackInfo ci) {
        if (hooksReady) {
            if (event.getEntity() instanceof EntityLiving) {
                EntityLiving entityLiving = (EntityLiving) event.getEntity();
                EntityEntry entry = EntityRegistry.getEntry(entityLiving.getClass());
                if (entry != null && ESM_Settings.diggerList.contains(entry.getRegistryName())) {
                    ItemStack stack = entityLiving.getHeldItemMainhand();
                    if (stack.isEmpty() || !(stack.getItem() instanceof ItemPickaxe)) {
                        ci.cancel();
                    }
                }
            }
        }
    }
}
