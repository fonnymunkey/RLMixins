package rlmixins.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.proxy.ClientProxy;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.entity.flare.EntityFlareNonAlbedo;
import rlmixins.entity.flare.RenderFlareNonAlbedo;

@Mixin(ClientProxy.class)
public abstract class ClientProxyMixin {

    /**
     * Register new rendering handler for new Flare
     */
    @Redirect(
            method = "registerEntityRenderingHandlers",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/client/registry/RenderingRegistry;registerEntityRenderingHandler(Ljava/lang/Class;Lnet/minecraftforge/fml/client/registry/IRenderFactory;)V"),
            remap = false
    )
    public void rlmixins_bountifulBaublesClientProxy_registerEntityRenderingHandlers(Class entityClass, IRenderFactory renderFactory) {
        RenderingRegistry.registerEntityRenderingHandler(EntityFlareNonAlbedo.class, RenderFlareNonAlbedo::new);
    }
}
