package rlmixins.potion;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;

import java.util.*;

public class PotionCurseBreak extends PotionBase {
    public static final PotionCurseBreak INSTANCE = new PotionCurseBreak();

    public PotionCurseBreak() {
        super("curse_break", false, 0x349CE1);
    }

    @Override
    public boolean isInstant() { return true; }

    @Override
    public boolean isReady(int duration, int amplifier) { return duration == 1; }

    @Override
    public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) {
        this.performEffect(entityLivingBaseIn, amplifier);
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        if(!(entityLivingBaseIn instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer)entityLivingBaseIn;
        InventoryPlayer inv = player.inventory;
        List<ItemStack> candidates = new ArrayList<ItemStack>();

        for(ItemStack cur : inv.offHandInventory) {
            if(!cur.isEmpty() && !(cur.getItem() instanceof ItemEnchantedBook) && cur.getTagCompound() != null) {
                candidates.add(cur);
            }
        }
        for(ItemStack cur : inv.armorInventory) {
            if(!cur.isEmpty() && !(cur.getItem() instanceof ItemEnchantedBook) && cur.getTagCompound() != null) {
                candidates.add(cur);
            }
        }
        for(ItemStack cur : inv.mainInventory) {
            if(!cur.isEmpty() && !(cur.getItem() instanceof ItemEnchantedBook) && cur.getTagCompound() != null) {
                candidates.add(cur);
            }
        }

        if(candidates.isEmpty()) return;

        Collections.shuffle(candidates);

        for(ItemStack cur : candidates) {
            Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(cur);
            Iterator<Enchantment> iter = enchants.keySet().iterator();
            boolean removed = false;
            while(iter.hasNext()) {
                Enchantment ench = iter.next();
                if(ench != null && ench.isCurse()) {
                    iter.remove();
                    removed = true;
                    break;
                }
            }
            if(removed) {
                EnchantmentHelper.setEnchantments(enchants, cur);
                return;
            }
        }
    }
}