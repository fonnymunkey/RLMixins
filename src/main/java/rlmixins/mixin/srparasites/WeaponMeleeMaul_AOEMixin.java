package rlmixins.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.item.tool.WeaponMeleeMaul;
import com.dhanantry.scapeandrunparasites.item.tool.WeaponToolMeleeBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(WeaponMeleeMaul.class)
public abstract class WeaponMeleeMaul_AOEMixin extends WeaponToolMeleeBase {
	
	public WeaponMeleeMaul_AOEMixin(ToolMaterial material, String name, double attackspeed, float range, float attackD, boolean fear, byte id) {
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