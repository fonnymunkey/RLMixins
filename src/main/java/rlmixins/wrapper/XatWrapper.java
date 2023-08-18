package rlmixins.wrapper;

import xzeroair.trinkets.util.config.ConfigHelper;

import java.util.HashMap;

public abstract class XatWrapper {
    private static final HashMap<String, ConfigHelper.AttributeEntry> entryMap = new HashMap<>();

    public static HashMap<String, ConfigHelper.AttributeEntry> getMap() { return entryMap; }
    public static void refreshMap() { entryMap.clear(); }
}