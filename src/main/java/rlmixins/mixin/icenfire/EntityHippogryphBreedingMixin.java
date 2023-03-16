package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.entity.EntityHippogryph;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityHippogryph.class)
public abstract class EntityHippogryphBreedingMixin extends EntityTameable {

    public EntityHippogryphBreedingMixin(World worldIn) {
        super(worldIn);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem() == Items.RABBIT_STEW;
    }
}