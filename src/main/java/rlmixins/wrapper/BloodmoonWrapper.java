package rlmixins.wrapper;

import lumien.bloodmoon.client.ClientBloodmoonHandler;

public abstract class BloodmoonWrapper {

    private static Boolean hasOptifine = null;

    private static boolean isOptifineLoaded() {
        if(hasOptifine == null) {
            try {
                Class.forName("optifine.OptiFineTweaker");
                hasOptifine = true;
            }
            catch(Exception ignored) {}
            if(hasOptifine == null) hasOptifine = false;
            System.out.println("hasOptifine: " + hasOptifine);
        }
        return hasOptifine;
    }

    public static int manipulateRed(int position, int originalValue) {
        if(isOptifineLoaded()) return ClientBloodmoonHandler.INSTANCE.manipulateRed(position, originalValue);
        return originalValue;
    }

    public static int manipulateGreen(int position, int originalValue) {
        if(isOptifineLoaded()) return ClientBloodmoonHandler.INSTANCE.manipulateGreen(position, originalValue);
        return originalValue;
    }

    public static int manipulateBlue(int position, int originalValue) {
        if(isOptifineLoaded()) return ClientBloodmoonHandler.INSTANCE.manipulateBlue(position, originalValue);
        return originalValue;
    }
}