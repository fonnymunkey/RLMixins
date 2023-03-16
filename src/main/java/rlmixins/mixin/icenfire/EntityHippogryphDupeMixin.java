package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.entity.EntityHippogryph;
import com.github.alexthe666.iceandfire.entity.StoneEntityProperties;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityHippogryph.class)
public abstract class EntityHippogryphDupeMixin extends EntityTameable {

    public EntityHippogryphDupeMixin(World worldIn) {
        super(worldIn);
    }

    @Inject(
            method = "onDeath",
            at = @At(value = "INVOKE", target = "Lcom/github/alexthe666/iceandfire/entity/EntityHippogryph$HippogryphInventory;getSizeInventory()I"),
            cancellable = true
    )
    public void rlmixins_iceAndFireEntityHippogryph_onDeath(DamageSource cause, CallbackInfo ci) {
        StoneEntityProperties properties = (StoneEntityProperties) EntityPropertiesHandler.INSTANCE.getProperties(this, StoneEntityProperties.class);
        if(properties != null && properties.isStone) ci.cancel();
    }

    @Inject(
            method = "dropArmor",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_iceAndFireEntityHippogryph_dropArmor(CallbackInfo ci) {
        StoneEntityProperties properties = (StoneEntityProperties) EntityPropertiesHandler.INSTANCE.getProperties(this, StoneEntityProperties.class);
        if(properties != null && properties.isStone) ci.cancel();
    }
}