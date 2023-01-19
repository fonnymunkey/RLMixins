package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_4_5.EnchantmentPandora;
import com.Shultrea.Rin.Enchantments_Sector.Smc_040;
import com.Shultrea.Rin.Main_Sector.somanyenchantments;
import com.Shultrea.Rin.Utility_Sector.EnchantmentLister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import rlmixins.handlers.ModRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Mixin(EnchantmentPandora.class)
public abstract class EnchantmentPandoraMixin extends Enchantment {

    protected EnchantmentPandoraMixin(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }

    /*
    @ModifyArg(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lcom/Shultrea/Rin/Enchantment_Base_Sector/EnchantmentBase;<init>(Lnet/minecraft/enchantment/Enchantment$Rarity;Lnet/minecraft/enchantment/EnumEnchantmentType;[Lnet/minecraft/inventory/EntityEquipmentSlot;)V")
    )
    private static Enchantment.Rarity rlmixins_soManyEnchantmentsEnchantmentPandora_initRarity(Enchantment.Rarity rarity) {
        return Rarity.RARE;
    }
     */

    /**
     * @author fonnymunkey
     * @reason rehandle pandora curse to fix performance and bugs
     */
    @SubscribeEvent
    @Overwrite(remap = false)
    public void HandleEnchant(TickEvent.PlayerTickEvent e) {
        if(e.phase != TickEvent.Phase.END || e.player == null || e.player.world.isRemote) return;

        if(e.player.ticksExisted%600 == 0) {
            Random rand = e.player.world.rand;
            InventoryPlayer inv = e.player.inventory;
            ItemStack cursed = null;
            int curseLevel = 0;
            List<ItemStack> candidates = new ArrayList<ItemStack>();

            for(ItemStack cur : inv.offHandInventory) {
                if(!cur.isEmpty() && !(cur.getItem() instanceof ItemEnchantedBook)) {
                    int lvl = EnchantmentHelper.getEnchantmentLevel(Smc_040.Pandora, cur);
                    if(lvl > 0) {
                        cursed = cur;
                        curseLevel = lvl;
                    }
                    else if(cur.getMaxStackSize() == 1) candidates.add(cur);
                }
            }
            for(ItemStack cur : inv.armorInventory) {
                if(!cur.isEmpty() && !(cur.getItem() instanceof ItemEnchantedBook)) {
                    int lvl = EnchantmentHelper.getEnchantmentLevel(Smc_040.Pandora, cur);
                    if(lvl > 0) {
                        cursed = cur;
                        curseLevel = lvl;
                    }
                    else if(cur.getMaxStackSize() == 1) candidates.add(cur);
                }
            }
            for(ItemStack cur : inv.mainInventory) {
                if(!cur.isEmpty() && !(cur.getItem() instanceof ItemEnchantedBook)) {
                    int lvl = EnchantmentHelper.getEnchantmentLevel(Smc_040.Pandora, cur);
                    if(lvl > 0) {
                        cursed = cur;
                        curseLevel = lvl;
                    }
                    else if(cur.getMaxStackSize() == 1) candidates.add(cur);
                }
            }

            if(cursed == null || candidates.isEmpty()) return;

            int origCurseLevel = curseLevel;

            List<Enchantment> curses = EnchantmentLister.CURSE;
            for(ItemStack cur : candidates) {
                if(curseLevel <= 5 && rand.nextInt(8) < 1) {
                    Enchantment curse = curses.get(rand.nextInt(curses.size()));
                    if(curse != Smc_040.Pandora && curse.canApply(cur)) {
                        boolean compat = true;
                        for(Enchantment ench : EnchantmentHelper.getEnchantments(cur).keySet()) {
                            if(!ench.isCompatibleWith(curse)) {
                                compat = false;
                                break;
                            }
                        }
                        if(compat) {
                            curseLevel++;
                            cur.addEnchantment(curse, rand.nextInt(curse.getMaxLevel()) + 1);
                        }
                    }
                }
            }

            if(curseLevel != origCurseLevel || curseLevel > 5) {
                Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(cursed);
                if(curseLevel > 5) {
                    enchants.remove(Smc_040.Pandora);
                    e.player.world.playSound(null, e.player.posX, e.player.posY, e.player.posZ, ModRegistry.PANDORA_REMOVAL, SoundCategory.PLAYERS, 0.8F, (e.player.world.rand.nextFloat()-e.player.world.rand.nextFloat())*0.1F+1.4F);
                }
                else {
                    enchants.put(Smc_040.Pandora, curseLevel);
                }
                EnchantmentHelper.setEnchantments(enchants, cursed);
            }
        }
    }

    /**
     * @author fonnymunkey
     * @reason fix pandora hiding itself
     */
    @Overwrite
    public String getTranslatedName(int level) {
        String s = I18n.translateToLocal(this.getName());
        s = TextFormatting.DARK_RED + s;
        EntityPlayer player = somanyenchantments.proxy.getClientPlayer();
        return level <= 2 && player != null && !player.isCreative() ? "" : s;
    }
}