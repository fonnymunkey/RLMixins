package rlmixins.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.item.tool.WeaponMeleeScythe;
import com.dhanantry.scapeandrunparasites.item.tool.WeaponToolMeleeBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(WeaponMeleeScythe.class)
public abstract class WeaponMeleeScytheMixin extends WeaponToolMeleeBase {

    public WeaponMeleeScytheMixin(ToolMaterial material, String name, double attackspeed, float range, float attackD, boolean fear, byte id) {
        super(material, name, attackspeed, range, attackD, fear, id);
    }

    /**
     * @author fonnymunkey
     * @reason handle scythe AOE effect in handler with RLCombat event handling
     */
    @Overwrite(remap = false)
    public boolean func_77644_a(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        return super.func_77644_a(stack, target, attacker);
    }
}