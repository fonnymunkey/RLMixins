package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.StoneEntityProperties;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityDragonBase.class)
public abstract class EntityDragonBaseDupeMixin extends EntityTameable {

    public EntityDragonBaseDupeMixin(World worldIn) {
        super(worldIn);
    }

    @Inject(
            method = "onDeath",
            at = @At(value = "INVOKE", target = "Lcom/github/alexthe666/iceandfire/entity/EntityDragonBase;entityDropItem(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/item/EntityItem;", shift = At.Shift.BEFORE),
            cancellable = true
    )
    public void rlmixins_iceAndFireEntityDragonBaseDupe_onDeath(DamageSource cause, CallbackInfo ci) {
        StoneEntityProperties properties = (StoneEntityProperties) EntityPropertiesHandler.INSTANCE.getProperties(this, StoneEntityProperties.class);
        if(properties != null && properties.isStone) ci.cancel();
    }
}