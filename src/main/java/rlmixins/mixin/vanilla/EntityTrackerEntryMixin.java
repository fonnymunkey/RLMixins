package rlmixins.mixin.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTrackerEntry;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityTrackerEntry.class)
public abstract class EntityTrackerEntryMixin {

    @Shadow @Final private Entity trackedEntity;

    @Redirect(
            method = "createSpawnPacket",
            at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;)V", remap = false)
    )
    public void rlmixins_vanillaEntityTrackerEntry_createSpawnPacket(Logger instance, String s) {
        instance.warn(s + ", name: " + this.trackedEntity.getName() + " pos: {x:" + this.trackedEntity.posX + ",y:" + this.trackedEntity.posY + ",z:" + this.trackedEntity.posZ + "}");
    }
}