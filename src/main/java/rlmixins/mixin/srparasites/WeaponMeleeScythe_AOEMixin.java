package rlmixins.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.item.tool.WeaponMeleeScythe;
import com.dhanantry.scapeandrunparasites.item.tool.WeaponToolMeleeBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(WeaponMeleeScythe.class)
public abstract class WeaponMeleeScythe_AOEMixin extends WeaponToolMeleeBase {
	
	public WeaponMeleeScythe_AOEMixin(ToolMaterial material, String name, double attackspeed, float range, float attackD, boolean fear, byte id) {
		super(material, name, attackspeed, range, attackD, fear, id);
	}
	
	/**
	 * @author fonnymunkey
	 * @reason rehandle with RLCombat
	 */
	@Overwrite
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		return super.hitEntity(stack, target, attacker);
	}
}