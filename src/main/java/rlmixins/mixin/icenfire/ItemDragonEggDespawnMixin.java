package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.entity.EntityDragonEgg;
import com.github.alexthe666.iceandfire.item.ItemDragonEgg;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemDragonEgg.class)
public abstract class ItemDragonEggDespawnMixin {

    @Redirect(
            method = "onItemUse",
            at = @At(value = "INVOKE", target = "Lcom/github/alexthe666/iceandfire/entity/EntityDragonEgg;onPlayerPlace(Lnet/minecraft/entity/player/EntityPlayer;)V", remap = false)
    )
    public void rlmixins_iceAndFireItemDragonEgg_onItemUse(EntityDragonEgg instance, EntityPlayer player) {
        instance.onPlayerPlace(player);
        instance.enablePersistence();
    }
}