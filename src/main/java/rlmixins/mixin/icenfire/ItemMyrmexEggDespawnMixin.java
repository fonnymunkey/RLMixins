package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexEgg;
import com.github.alexthe666.iceandfire.item.ItemMyrmexEgg;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemMyrmexEgg.class)
public abstract class ItemMyrmexEggDespawnMixin {

    @Redirect(
            method = "onItemUse",
            at = @At(value = "INVOKE", target = "Lcom/github/alexthe666/iceandfire/entity/EntityMyrmexEgg;onPlayerPlace(Lnet/minecraft/entity/player/EntityPlayer;)V", remap = false)
    )
    public void rlmixins_iceAndFireItemMyrmexEgg_onItemUse(EntityMyrmexEgg instance, EntityPlayer player) {
        instance.onPlayerPlace(player);
        instance.enablePersistence();
    }
}