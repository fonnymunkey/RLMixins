package rlmixins.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.item.ItemFlareGun;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.entity.flare.EntityFlareNonAlbedo;
import rlmixins.handlers.ModRegistry;

@Mixin(ItemFlareGun.class)
public abstract class ItemFlareGunMixin {

    private float velocity = 1.25F;
    private float inaccuracy = 1.0F;

    /**
     * Shoot new Flare entity not old flare entity, and play new sound effect
     */
    @Inject(
            method = "onItemRightClick",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;isRemote:Z"),
            cancellable = true
    )
    public void rlmixins_itemFlareGun_onItemRightClick(World world, EntityPlayer player, EnumHand hand, CallbackInfoReturnable<ActionResult<ItemStack>> cir) {
        if(!world.isRemote) {
            EntityFlareNonAlbedo flare = new EntityFlareNonAlbedo(world, player);
            flare.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, velocity, inaccuracy);
            world.spawnEntity(flare);
            world.playSound(null, player.posX, player.posY, player.posZ, ModRegistry.FLARE_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.rand.nextFloat() * 0.4F + 1.2F)* 2.0F);
        }
		cir.setReturnValue(new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand)));
    }
}
