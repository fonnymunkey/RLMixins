package rlmixins.mixin.otg;

import com.pg85.otg.forge.gui.dimensions.OTGGuiDimensionSettingsList;
import com.pg85.otg.forge.gui.dimensions.base.IGuiListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;

@Mixin(OTGGuiDimensionSettingsList.class)
public abstract class OTGGuiDimensionSettingsListMixin {

    @Redirect(
            method = "refreshData(ZZZ)V",
            at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;add(Ljava/lang/Object;)Z", ordinal = 18),
            remap = false
    )
    public boolean rlmixins_otgOTGGuiDimensionSettingsList_refreshData_18(ArrayList<IGuiListEntry> instance, Object e) {
        return false;
    }

    @Redirect(
            method = "refreshData(ZZZ)V",
            at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;add(Ljava/lang/Object;)Z", ordinal = 19),
            remap = false
    )
    public boolean rlmixins_otgOTGGuiDimensionSettingsList_refreshData_19(ArrayList<IGuiListEntry> instance, Object e) {
        return false;
    }

    @Redirect(
            method = "refreshData(ZZZ)V",
            at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;add(Ljava/lang/Object;)Z", ordinal = 20),
            remap = false
    )
    public boolean rlmixins_otgOTGGuiDimensionSettingsList_refreshData_20(ArrayList<IGuiListEntry> instance, Object e) {
        return false;
    }

    @Redirect(
            method = "refreshData(ZZZ)V",
            at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;add(Ljava/lang/Object;)Z", ordinal = 21),
            remap = false
    )
    public boolean rlmixins_otgOTGGuiDimensionSettingsList_refreshData_21(ArrayList<IGuiListEntry> instance, Object e) {
        return false;
    }

    @Redirect(
            method = "refreshData(ZZZ)V",
            at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;add(Ljava/lang/Object;)Z", ordinal = 22),
            remap = false
    )
    public boolean rlmixins_otgOTGGuiDimensionSettingsList_refreshData_22(ArrayList<IGuiListEntry> instance, Object e) {
        return false;
    }

    @Redirect(
            method = "refreshData(ZZZ)V",
            at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;add(Ljava/lang/Object;)Z", ordinal = 23),
            remap = false
    )
    public boolean rlmixins_otgOTGGuiDimensionSettingsList_refreshData_23(ArrayList<IGuiListEntry> instance, Object e) {
        return false;
    }

    @Redirect(
            method = "refreshData(ZZZ)V",
            at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;add(Ljava/lang/Object;)Z", ordinal = 24),
            remap = false
    )
    public boolean rlmixins_otgOTGGuiDimensionSettingsList_refreshData_24(ArrayList<IGuiListEntry> instance, Object e) {
        return false;
    }

    @Redirect(
            method = "refreshData(ZZZ)V",
            at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;add(Ljava/lang/Object;)Z", ordinal = 25),
            remap = false
    )
    public boolean rlmixins_otgOTGGuiDimensionSettingsList_refreshData_25(ArrayList<IGuiListEntry> instance, Object e) {
        return false;
    }

    @Redirect(
            method = "refreshData(ZZZ)V",
            at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;add(Ljava/lang/Object;)Z", ordinal = 35),
            remap = false
    )
    public boolean rlmixins_otgOTGGuiDimensionSettingsList_refreshData_35(ArrayList<IGuiListEntry> instance, Object e) {
        return false;
    }

    @Redirect(
            method = "refreshData(ZZZ)V",
            at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;add(Ljava/lang/Object;)Z", ordinal = 36),
            remap = false
    )
    public boolean rlmixins_otgOTGGuiDimensionSettingsList_refreshData_36(ArrayList<IGuiListEntry> instance, Object e) {
        return false;
    }
}