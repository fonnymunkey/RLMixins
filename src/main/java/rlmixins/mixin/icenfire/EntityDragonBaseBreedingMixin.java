package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityDragonBase.class)
public abstract class EntityDragonBaseBreedingMixin {

    /**
     * Skips EntityAnimal.processInteract and just copies EntityAgeable.processInteract instead
     */
    @Redirect(
            method = "processInteract",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/EntityTameable;processInteract(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/EnumHand;)Z")
    )
    public boolean rlmixins_iceAndFireEntityDragonBase_processInteract(EntityTameable instance, EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if(itemstack.getItem() == Items.SPAWN_EGG) {
            if(!instance.world.isRemote) {
                Class<? extends Entity> oclass = EntityList.getClass(ItemMonsterPlacer.getNamedIdFrom(itemstack));

                if(oclass != null && instance.getClass() == oclass) {
                    EntityAgeable entityageable = instance.createChild(instance);

                    if(entityageable != null) {
                        entityageable.setGrowingAge(-24000);
                        entityageable.setLocationAndAngles(instance.posX, instance.posY, instance.posZ, 0.0F, 0.0F);
                        instance.world.spawnEntity(entityageable);

                        if(itemstack.hasDisplayName()) {
                            entityageable.setCustomNameTag(itemstack.getDisplayName());
                        }

                        if(!player.capabilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }
                    }
                }
            }
            return true;
        }
        else return false;
    }
}
