package rlmixins.mixin.lycanites;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.lycanitesmobs.core.entity.creature.EntityAsmodeus;
import com.lycanitesmobs.core.entity.creature.EntityAstaroth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(EntityAsmodeus.class)
public abstract class EntityAsmodeusHellshieldMinionMixin {

    @Shadow(remap = false)
    public List<EntityAstaroth> astarothMinions;

    /*
     *   Additionally check for at least one living astaroth
     */
    @ModifyExpressionValue(
            method = "isBlocking",
            at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"),
            remap = false
    )
    public boolean rlmixins_lycanitesMobsEntityAsmodeus_isBlockingMinionAlive(boolean original){
        // compared value is inverted due to !
        if (!this.astarothMinions.isEmpty()) {
            for(EntityAstaroth minion : this.astarothMinions){
                if(minion.isEntityAlive()) return false;
            }
        }
        return true;
    }
}
