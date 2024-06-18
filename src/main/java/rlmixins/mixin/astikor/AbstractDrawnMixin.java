package rlmixins.mixin.astikor;

import de.mennomax.astikorcarts.entity.AbstractDrawn;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.ForgeConfigHandler;

@Mixin(AbstractDrawn.class)
public abstract class AbstractDrawnMixin extends Entity {

    public AbstractDrawnMixin(World worldIn) {
        super(worldIn);
    }

    @Redirect(
            method = "shouldRemovePulling",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isRiding()Z")
    )
    public boolean rlmixins_astikorCartsAbstractDrawn_shouldRemovePulling(Entity instance) {
        double x = this.posX - instance.posX;
        double z = this.posZ - instance.posZ;
        return instance.isRiding() || (x*x + z*z) > ForgeConfigHandler.server.cartDistanceLimit*ForgeConfigHandler.server.cartDistanceLimit;
    }
}