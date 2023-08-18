package rlmixins.mixin.xat;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import rlmixins.wrapper.XatWrapper;
import xzeroair.trinkets.util.config.ConfigHelper;
import xzeroair.trinkets.util.helpers.StringUtils;

import static xzeroair.trinkets.util.config.ConfigHelper.cleanConfigEntry;

@Mixin(ConfigHelper.class)
public abstract class ConfigHelperMixin {

    @Shadow(remap = false) private static String doubleRegex;

    private static final ConfigHelper.AttributeEntry NULL_ENTRY = new ConfigHelper.AttributeEntry("null", 0, 0, false);

    /**
     * @author fonnymunkey
     * @reason fix performance/ram alloc waste
     */
    @Overwrite(remap = false)
    public static ConfigHelper.AttributeEntry getAttributeEntry(String string) {
        ConfigHelper.AttributeEntry temp = XatWrapper.getMap().computeIfAbsent(string, ConfigHelperMixin::getEntry);
        return temp == NULL_ENTRY ? null : temp;
    }

    private static ConfigHelper.AttributeEntry getEntry(String string) {
        String configEntry = cleanConfigEntry(
                string.replaceFirst("[nN][aA][mM][eE][:]", "")
                        .replaceFirst("[aA][mM][oO][uU][nN][tT][:]", "")
                        .replaceFirst("[oO][pP][eE][rR][aA][tT][iI][oO][nN][:]", "")
        );
        String[] vars = configEntry.split(";");
        String arg1 = StringUtils.getStringFromArray(vars, 0);
        String arg2 = StringUtils.getStringFromArray(vars, 1);
        String arg3 = StringUtils.getStringFromArray(vars, 2);
        String arg4 = StringUtils.getStringFromArray(vars, 3);
        String attributeRegex = "([a-zA-Z0-9_.]*)";
        String amountRegex = doubleRegex;
        String opRegex = "([0-2])";
        String boolRegex = "(true)|(false)";
        String attribute = "";
        double amount = 0.0;
        int op = 0;
        boolean saved = false;
        boolean isAttribute = false;
        if (!arg1.isEmpty() && arg1.matches(attributeRegex)) {
            attribute = arg1;
            if (!arg2.isEmpty() && arg2.matches(amountRegex)) {
                try {
                    amount = Double.parseDouble(arg2.replace("+", ""));
                    isAttribute = true;
                } catch (Exception var19) {
                    var19.printStackTrace();
                }

                if (!arg3.isEmpty() && arg3.matches(opRegex)) {
                    try {
                        op = Integer.parseInt(arg3);
                    } catch (Exception var18) {
                        var18.printStackTrace();
                    }
                }

                if (!arg4.isEmpty() && arg4.toLowerCase().matches(boolRegex) && arg4.equalsIgnoreCase("true")) {
                    saved = true;
                }
            }
        }

        return isAttribute && !attribute.isEmpty() && amount != 0.0 ? new ConfigHelper.AttributeEntry(attribute, amount, op, saved) : NULL_ENTRY;
    }
}