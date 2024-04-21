package rlmixins.mixin.scalinghealth;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.silentchaos512.scalinghealth.ScalingHealth;
import net.silentchaos512.scalinghealth.potion.PotionBandaged;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PotionBandaged.class)
public abstract class PotionBandagedMixin extends Potion {
    
    @Unique
    private static final ResourceLocation rlmixins$BANDAGED_TEXTURE = new ResourceLocation(ScalingHealth.MOD_ID_LOWER, "textures/effects/bandaged.png");

    protected PotionBandagedMixin(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    public boolean hasStatusIcon() { return true; }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(rlmixins$BANDAGED_TEXTURE);
        Gui.drawModalRectWithCustomSizedTexture(x+6, y+7, 0, 0, 18, 18, 18, 18);
    }

    @Override
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        mc.getTextureManager().bindTexture(rlmixins$BANDAGED_TEXTURE);
        Gui.drawModalRectWithCustomSizedTexture(x+3, y+3, 0, 0, 18, 18, 18, 18);
    }
}