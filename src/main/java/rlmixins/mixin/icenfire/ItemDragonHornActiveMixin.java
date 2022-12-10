package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.core.ModItems;
import com.github.alexthe666.iceandfire.item.ItemDragonHornActive;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemDragonHornActive.class)
public abstract class ItemDragonHornActiveMixin {

    /**
     * Stop the dragon horn from trying to set the held item using the wrong slot
     */
    @Inject(
            method = "onPlayerStoppedUsing",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTTagCompound;setBoolean(Ljava/lang/String;Z)V")
    )
    public void rlmixins_iceAndFireItemDragonHorn_onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft, CallbackInfo ci) {
        entityLiving.setHeldItem(entityLiving.getActiveHand(), new ItemStack(ModItems.dragon_horn));
    }
}