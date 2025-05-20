package rlmixins.mixin.vanilla;

import net.minecraft.entity.item.EntityItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityItem.class)
public interface EntityItemAccessor {
    @Accessor(value = "age")
    int getAgeServerSide();
}