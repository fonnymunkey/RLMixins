package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.entity.EntityHippocampus;
import com.github.alexthe666.iceandfire.entity.StoneEntityProperties;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityHippocampus.class)
public abstract class EntityHippocampusMixin extends EntityTameable {

    @Shadow(remap = false) public EntityHippocampus.HippocampusInventory hippocampusInventory;

    public EntityHippocampusMixin(World worldIn) {
        super(worldIn);
    }

    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
        if(this.hippocampusInventory != null && !this.world.isRemote) {
            StoneEntityProperties properties = (StoneEntityProperties) EntityPropertiesHandler.INSTANCE.getProperties(this, StoneEntityProperties.class);
            if(properties != null && properties.isStone) return;
            for(int i = 0; i < this.hippocampusInventory.getSizeInventory(); ++i) {
                ItemStack itemstack = this.hippocampusInventory.getStackInSlot(i);
                if (!itemstack.isEmpty()) {
                    this.entityDropItem(itemstack, 0.0F);
                }
            }
        }
    }

    @Inject(
            method = "dropArmor",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_iceAndFireEntityHippocampus_dropArmor(CallbackInfo ci) {
        StoneEntityProperties properties = (StoneEntityProperties) EntityPropertiesHandler.INSTANCE.getProperties(this, StoneEntityProperties.class);
        if(properties != null && properties.isStone) ci.cancel();
    }
}