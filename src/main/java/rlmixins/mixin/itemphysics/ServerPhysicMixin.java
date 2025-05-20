package rlmixins.mixin.itemphysics;

import com.creativemd.itemphysic.physics.ServerPhysic;
import net.minecraft.entity.item.EntityItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.mixin.vanilla.EntityItemAccessor;

@Mixin(ServerPhysic.class)
public class ServerPhysicMixin {
    @Redirect(
            method = "onCollideWithPlayer(Lnet/minecraft/entity/item/EntityItem;Lnet/minecraft/entity/player/EntityPlayer;Z)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityItem;getAge()I")
    )
    private static int rlmixins_itemPhysicServerPhysic_onCollideWithPlayer(EntityItem instance){
        return ((EntityItemAccessor)instance).getAgeServerSide();
    }
}
