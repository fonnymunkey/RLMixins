package rlmixins.mixin.dshud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.orecruncher.dshuds.Environment;
import org.orecruncher.dshuds.ModOptions;
import org.orecruncher.dshuds.hud.CompassHUD;
import org.orecruncher.lib.ItemStackUtil;
import org.orecruncher.lib.PlayerUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Locale;

@Mixin(CompassHUD.class)
public abstract class CompassHUDMixin {

    @Shadow(remap = false)
    private boolean showCompass;

    @Shadow(remap = false)
    @Final
    private static Item COMPASS;

    @Unique
    private static Item rlmixins$BAROMETER;

    @Unique
    private static Item rlmixins$PHOTOMETER;

    @Inject(
            method = "doTick",
            at = @At(value = "INVOKE", target = "Lorg/orecruncher/dshuds/hud/CompassHUD;showSeason()Z", shift = At.Shift.BEFORE),
            locals = LocalCapture.CAPTURE_FAILHARD,
            remap = false
    )
    public void rlmixins_dsHudCompassHUD_doTick_inject(int tickRef, CallbackInfo ci, List<String> text) {
        if(rlmixins$PHOTOMETER == null) {
            rlmixins$PHOTOMETER = ForgeRegistries.ITEMS.getValue(new ResourceLocation("inspirations:photometer"));
            if(rlmixins$PHOTOMETER == null) rlmixins$PHOTOMETER = Items.AIR;
        }
        if(rlmixins$BAROMETER == null) {
            rlmixins$BAROMETER = ForgeRegistries.ITEMS.getValue(new ResourceLocation("inspirations:barometer"));
            if(rlmixins$BAROMETER == null) rlmixins$BAROMETER = Items.AIR;
        }
        EntityPlayer player = Environment.getPlayer();
        if(player == null) return;
        Entity e = PlayerUtils.entityImLookingAt(player);

        if(!this.showCompass && ModOptions.compassHUD.enable) {
            if(e instanceof EntityItemFrame) {
                ItemStack stack = ((EntityItemFrame)e).getDisplayedItem();
                if(ItemStackUtil.isValidItemStack(stack) && stack.getItem() == COMPASS) {
                    BlockPos pos = new BlockPos(e);
                    text.add(TextFormatting.AQUA + String.format(Locale.getDefault(), ModOptions.compassHUD.coordFormat, pos.getX(), pos.getY(), pos.getZ()));
                    text.add(TextFormatting.GOLD + Environment.getWorld().getBiome(pos).getBiomeName());
                }
            }
        }
        if(rlmixins$PHOTOMETER != Items.AIR) {
            if(PlayerUtils.isHolding(player, rlmixins$PHOTOMETER)) {
                BlockPos pos = null;
                RayTraceResult trace = Minecraft.getMinecraft().objectMouseOver;
                if(trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK) {
                    pos = trace.getBlockPos();
                    if(player.world.isBlockFullCube(pos)) {
                        pos = pos.offset(trace.sideHit);
                    }
                }
                if(pos == null) pos = new BlockPos(player);

                int level = player.world.getLightFromNeighborsFor(EnumSkyBlock.BLOCK, pos);
                text.add(TextFormatting.YELLOW + I18n.format("rlmixins.dshuds.photometer.light_level", level));
            }
            else if(e instanceof EntityItemFrame) {
                ItemStack stack = ((EntityItemFrame)e).getDisplayedItem();
                if(ItemStackUtil.isValidItemStack(stack) && stack.getItem() == rlmixins$PHOTOMETER) {
                    int level = player.world.getLightFromNeighborsFor(EnumSkyBlock.BLOCK, new BlockPos(e));
                    text.add(TextFormatting.YELLOW + I18n.format("rlmixins.dshuds.photometer.light_level", level));
                }
            }
        }
        if(rlmixins$BAROMETER != Items.AIR) {
            if(PlayerUtils.isHolding(player, rlmixins$BAROMETER)) {
                BlockPos pos = Environment.getPlayerPosition();
                text.add(TextFormatting.AQUA + String.format(Locale.getDefault(), "y: %1$d", pos.getY()));
            }
            else if(e instanceof EntityItemFrame) {
                ItemStack stack = ((EntityItemFrame)e).getDisplayedItem();
                if(ItemStackUtil.isValidItemStack(stack) && stack.getItem() == rlmixins$BAROMETER) {
                    BlockPos pos = new BlockPos(e);
                    text.add(TextFormatting.AQUA + String.format(Locale.getDefault(), "y: %1$d", pos.getY()));
                }
            }
        }
    }
}