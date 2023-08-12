package rlmixins.mixin.dynamictrees;

import com.ferreusveritas.dynamictrees.entities.EntityFallingTree;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.PrintStream;

@Mixin(EntityFallingTree.class)
public abstract class EntityFallingTreeMixin {

    @Redirect(
            method = "setData",
            at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V"),
            remap = false
    )
    public void rlmixins_dynamicTreesEntityFallingTree_setData_println(PrintStream instance, String s) {
        //noop
    }

    @Redirect(
            method = "setData",
            at = @At(value = "INVOKE", target = "Ljava/lang/Exception;printStackTrace()V"),
            remap = false
    )
    public void rlmixins_dynamicTreesEntityFallingTree_setData_printStackTrace(Exception instance) {
        //noop
    }
}