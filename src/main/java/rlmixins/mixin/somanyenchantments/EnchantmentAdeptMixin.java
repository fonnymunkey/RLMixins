package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_4_0.EnchantmentAdept;
import com.Shultrea.Rin.Enchantment_Base_Sector.EnchantmentBase;
import com.Shultrea.Rin.Enchantments_Sector.Smc_040;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import rlmixins.wrapper.BetterSurvivalWrapper;
import rlmixins.wrapper.BlightWrapper;
import svenhjol.meson.helper.EnchantmentHelper;

@Mixin(EnchantmentAdept.class)
public abstract class EnchantmentAdeptMixin extends EnchantmentBase {

    public EnchantmentAdeptMixin(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }

    /**
     * @author fonnymunkey
     * @reason make adept work with looting but not education
     */
    @Overwrite
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && (!Loader.isModLoaded("mujmajnkraftsbettersurvival") || ench != BetterSurvivalWrapper.getEducation());
    }

    /**
     * @author fonnymunkey
     * @reason fix experience gain resetting other gains
     */
    @Overwrite(remap = false)
    @SubscribeEvent
    public void onDeath(LivingExperienceDropEvent event) {
        EntityPlayer player = event.getAttackingPlayer();
        if(player != null && event.getOriginalExperience() > 0 && event.getEntityLiving() != null) {
            int level = EnchantmentHelper.getEnchantmentLevel(Smc_040.Adept, player.getHeldItemMainhand());
            if(level <= 0) level = EnchantmentHelper.getEnchantmentLevel(Smc_040.Adept, player.getHeldItemOffhand());
            if(level > 0) {
                if(event.getEntityLiving().isNonBoss() || (Loader.isModLoaded("scalinghealth") && BlightWrapper.isEntityBlight(event.getEntityLiving()))) {
                    event.setDroppedExperience(level + (int)((float)event.getDroppedExperience() * (1.0F + 0.15F * (float)level)));
                }
                else {
                    event.setDroppedExperience(level + (int)((float)event.getDroppedExperience() * (1.0F + 0.5F * (float)level)));
                }
            }
        }
    }
}