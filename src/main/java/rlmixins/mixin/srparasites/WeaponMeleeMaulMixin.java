package rlmixins.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.item.tool.WeaponMeleeMaul;
import com.dhanantry.scapeandrunparasites.item.tool.WeaponToolMeleeBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WeaponMeleeMaul.class)
public abstract class WeaponMeleeMaulMixin extends WeaponToolMeleeBase {

    public WeaponMeleeMaulMixin(ToolMaterial material, String name, double attackspeed, float range, float attackD, boolean fear, byte id) {
        super(material, name, attackspeed, range, attackD, fear, id);
    }

    /**
     * @author fonnymunkey (copied from scythe by nischhelm)
     * @reason handle maul AOE effect in handler with RLCombat event handling
     */
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        return super.hitEntity(stack, target, attacker);
    }
}