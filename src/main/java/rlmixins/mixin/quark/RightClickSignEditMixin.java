package rlmixins.mixin.quark;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.IGuiHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.quark.RightClickSignEditHandler;
import vazkii.quark.base.Quark;
import vazkii.quark.tweaks.feature.RightClickSignEdit;

@Mixin(RightClickSignEdit.class)
public abstract class RightClickSignEditMixin implements IGuiHandler {
    /**
     * Fix Quark configuration for Right Click Sign Edit.
     * This is done by syncing the configuration with the client using packets.
     */
    @Redirect(
            method = "onInteract",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;openGui(Ljava/lang/Object;ILnet/minecraft/world/World;III)V",
                    remap = true),
            remap = false
    )
    private void m_quarkRightClickSignEdit_getClientGuiElement(EntityPlayer instance, Object mod, int modGuiId, World world, int x, int y, int z) {
        if (RightClickSignEditHandler.isEnabled) {
            instance.openGui(Quark.instance, 0, world, x, y, z);
        }
    }
}