package rlmixins.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.item.tool.WeaponToolArmorBase;
import com.dhanantry.scapeandrunparasites.util.config.SRPConfig;
import com.dhanantry.scapeandrunparasites.util.handlers.SRPEventHandlerBus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SRPEventHandlerBus.class)
public abstract class SRPEventHandlerBusFireMixin {

    @Inject(
            method = "entityHurt",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/entity/living/LivingHurtEvent;getAmount()F", ordinal = 2),
            cancellable = true,
            remap = false
    )
    public void rlmixins_srparasitesSRPEventHandlerBus_entityHurtGeneral(LivingHurtEvent event, CallbackInfo ci) {
        if(event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            int pieces = 0;
            for(ItemStack itemStack : player.inventory.armorInventory) {
                if(itemStack.getItem() instanceof WeaponToolArmorBase) pieces++;
            }
            if(pieces > 0) return;
        }
        ci.cancel();
    }
    @Redirect(
            method = "entityHurt",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/entity/living/LivingHurtEvent;setAmount(F)V", ordinal = 1),
            remap = false
    )
    public void rlmixins_srparasitesSRPEventHandlerBus_entityHurtFire(LivingHurtEvent instance, float amount) {
        if (instance.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) instance.getEntityLiving();
            float base = amount / SRPConfig.firemultyplier;
            float step = SRPConfig.firemultyplier / 4.0F;
            int pieces = 0;
            for(ItemStack itemStack : player.inventory.armorInventory) {
                if(itemStack.getItem() instanceof WeaponToolArmorBase) pieces++;
            }
            instance.setAmount(base * (1.0F + (step * (float)pieces)));
        }
    }
}
