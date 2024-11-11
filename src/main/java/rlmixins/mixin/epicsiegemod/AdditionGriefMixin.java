package rlmixins.mixin.epicsiegemod;

import funwayguy.epicsiegemod.ai.additions.AdditionGrief;
import funwayguy.epicsiegemod.api.ITaskAddition;
import net.minecraft.entity.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AdditionGrief.class)
public abstract class AdditionGriefMixin implements ITaskAddition {

    @Override
    public boolean isValid(EntityLiving entityLiving) {
        return false;
    }
}
