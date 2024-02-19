package rlmixins.mixin.quark;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import vazkii.quark.client.feature.EnchantedBooksShowItems;
import vazkii.quark.misc.feature.AncientTomes;

import java.util.List;

@Mixin(EnchantedBooksShowItems.class)
public abstract class EnchantedBooksShowItemsMixin {

    @Shadow(remap = false)
    public static List<EnchantmentData> getEnchantedBookEnchantments(ItemStack stack) {
        return null;
    }

    @Shadow(remap = false)
    public static List<ItemStack> getItemsForEnchantment(Enchantment e) {
        return null;
    }

    @Unique
    private static final int rlmixins$perLine = 20;

    /**
     * @author fonnymunkey
     * @reason add line breaks to tooltips
     */
    @Overwrite(remap = false)
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void makeTooltip(ItemTooltipEvent event) {
        if(Minecraft.getMinecraft().player == null)
            return;

        ItemStack stack = event.getItemStack();
        if(stack.getItem() == Items.ENCHANTED_BOOK || stack.getItem() == AncientTomes.ancient_tome) {
            Minecraft mc = Minecraft.getMinecraft();
            List<String> tooltip = event.getToolTip();
            int tooltipIndex = 0;

            List<EnchantmentData> enchants = getEnchantedBookEnchantments(stack);
            for(EnchantmentData ed : enchants) {
                String match = ed.enchantment.getTranslatedName(ed.enchantmentLevel);

                for(; tooltipIndex < tooltip.size(); tooltipIndex++)
                    if(tooltip.get(tooltipIndex).equals(match)) {
                        List<ItemStack> items = getItemsForEnchantment(ed.enchantment);
                        if(!items.isEmpty()) {
                            int index = 0;
                            for(; index < items.size()/rlmixins$perLine; index++) {
                                int len = 3 + rlmixins$perLine * 9;
                                String spaces = "";
                                while(mc.fontRenderer.getStringWidth(spaces) < len)
                                    spaces += " ";

                                tooltip.add(tooltipIndex + 1 + index, spaces);
                            }
                            if(items.size()%rlmixins$perLine > 0) {
                                int len = 3 + items.size()%rlmixins$perLine * 9;
                                String spaces = "";
                                while(mc.fontRenderer.getStringWidth(spaces) < len)
                                    spaces += " ";

                                tooltip.add(tooltipIndex + 1 + index, spaces);
                            }
                        }

                        break;
                    }
            }
        }
    }

    /**
     * @author fonnymunkey
     * @reason add line breaks to tooltips and fix lighting
     */
    @Overwrite(remap = false)
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void renderTooltip(RenderTooltipEvent.PostText event) {
        ItemStack stack = event.getStack();

        if(stack.getItem() == Items.ENCHANTED_BOOK || stack.getItem() == AncientTomes.ancient_tome) {
            Minecraft mc = Minecraft.getMinecraft();
            List<String> tooltip = event.getLines();

            GlStateManager.pushMatrix();
            GlStateManager.translate(event.getX(), event.getY() + 12, 0);
            GlStateManager.scale(0.5, 0.5, 1.0);
            RenderHelper.enableGUIStandardItemLighting();

            List<EnchantmentData> enchants = getEnchantedBookEnchantments(stack);
            for(EnchantmentData ed : enchants) {
                String match = TextFormatting.getTextWithoutFormattingCodes(ed.enchantment.getTranslatedName(ed.enchantmentLevel));
                for(int tooltipIndex = 0; tooltipIndex < tooltip.size(); tooltipIndex++) {
                    String line = TextFormatting.getTextWithoutFormattingCodes(tooltip.get(tooltipIndex));
                    if(line != null && line.equals(match)) {
                        int drawn = 0;

                        List<ItemStack> items = getItemsForEnchantment(ed.enchantment);
                        for(ItemStack testStack : items) {
                            mc.getRenderItem().renderItemIntoGUI(testStack, 6 + (drawn%rlmixins$perLine) * 18, (tooltipIndex+(drawn/rlmixins$perLine)) * 20 - 2);
                            drawn++;
                        }

                        break;
                    }
                }
            }
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }
    }
}