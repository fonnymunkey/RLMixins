package rlmixins.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.entity.ModEntities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.entity.flare.EntityFlareNonAlbedo;

@Mixin(ModEntities.class)
public abstract class ModEntitiesMixin {

    /**
     * Replace old Flare entity with new Flare entity
     */
    @Redirect(
            method = "registerEntities",
            at = @At(value = "INVOKE", target = "Lcursedflames/bountifulbaubles/entity/ModEntities;register(Ljava/lang/String;Ljava/lang/Class;I)V"),
            remap = false
    )
    private static void rlmixins_bountifulBaublesModEntities_registerEntities(String name, Class c, int id) {
        ModEntities.register(name, EntityFlareNonAlbedo.class, id);
    }
}