package rlmixins.handlers;

import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomConfigHandler {
    /*
    private static File configFolder = null;
    private static File toolAttributeFile = null;
    private static File toolRepairFile = null;
    private static File armorAttributeFile = null;
    private static File armorRepairFile = null;

    private static File itemOverrideRepairFile = null;

    private static final Map<String, ToolAttributeEntry> toolAttributeMap = new HashMap<>();
    private static final Map<String, String[]> toolRepairMap = new HashMap<>();
    private static final Map<String, ArmorAttributeEntry> armorAttributeMap = new HashMap<>();
    private static final Map<String, String[]> armorRepairMap = new HashMap<>();

    private static final Map<String, String[]> itemOverrideRepairMap = new HashMap<>();

    public static void forceInitializeLazyFiles() {
        getToolAttributes("foo");
        getToolRepairs("foo");
        getArmorAttributes("foo");
        getArmorRepairs("foo");
        getItemOverrideRepairs("foo");
    }

    @Nullable
    public static ToolAttributeEntry getToolAttributes(String name) {
        if(toolAttributeFile==null && !initToolAttributes()) return null;
        return toolAttributeMap.get(name);
    }

    @Nullable
    public static String[] getToolRepairs(String name) {
        if(toolRepairFile==null && !initToolRepairs()) return null;
        return toolRepairMap.get(name);
    }

    @Nullable
    public static ArmorAttributeEntry getArmorAttributes(String name) {
        if(armorAttributeFile==null && !initArmorAttributes()) return null;
        return armorAttributeMap.get(name);
    }

    @Nullable
    public static String[] getArmorRepairs(String name) {
        if(armorRepairFile==null && !initArmorRepairs()) return null;
        return armorRepairMap.get(name);
    }

    @Nullable
    public static String[] getItemOverrideRepairs(String name) {
        if(itemOverrideRepairFile==null && !initItemOverrideRepairs()) return null;
        return itemOverrideRepairMap.get(name);
    }

    private static boolean initDirectory() {
        configFolder = new File("config", MaterialTweaker.MODID);
        if(!configFolder.exists() || !configFolder.isDirectory()) {
            if(!configFolder.mkdir()) {
                MaterialTweaker.LOGGER.log(Level.ERROR, MaterialTweaker.MODID + ": " + "Could not create the folder for configuration.");
                return false;
            }
        }
        return true;
    }

    private static boolean initToolAttributes() {
        if(configFolder==null && !initDirectory()) return false;
        toolAttributeFile = new File(configFolder, "toolattributes.cfg");
        try {
            if(!toolAttributeFile.exists()) {
                if(!toolAttributeFile.createNewFile()) MaterialTweaker.LOGGER.log(Level.ERROR, MaterialTweaker.MODID + ": " + "Failed to create new tool attribute file.");
                else Files.write(toolAttributeFile.toPath(),
                        (       "//List of Tool Materials and their attributes to be changed, one entry per line.\n" +
                                "//String materialName, int harvestLevel, int maxUses, float efficiency, float damage, int enchantability\n" +
                                "//Example:\n" +
                                "//DIAMOND,5,2000,10.0,6.0,15\n"
                        ).getBytes(StandardCharsets.UTF_8));
            }
            else {
                List<String> list = Files.lines(toolAttributeFile.toPath())
                        .map(String::trim)
                        .filter(s -> !s.startsWith("//"))
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
                for(String entry : list) {
                    String[] entryArray = Arrays.stream(entry.split(",")).map(String::trim).toArray(String[]::new);
                    toolAttributeMap.put(entryArray[0],
                            new ToolAttributeEntry(
                                    Integer.parseInt(entryArray[1]),
                                    Integer.parseInt(entryArray[2]),
                                    Float.parseFloat(entryArray[3]),
                                    Float.parseFloat(entryArray[4]),
                                    Integer.parseInt(entryArray[5])
                    ));
                }
            }
            return true;
        }
        catch(Exception ex) {
            MaterialTweaker.LOGGER.log(Level.ERROR, MaterialTweaker.MODID + ": " + "Failed to initialize tool attribute file: " + ex);
            return false;
        }
    }

    private static boolean initToolRepairs() {
        if(configFolder==null && !initDirectory()) return false;
        toolRepairFile = new File(configFolder, "toolrepairs.cfg");
        try {
            if(!toolRepairFile.exists()) {
                if(!toolRepairFile.createNewFile()) MaterialTweaker.LOGGER.log(Level.ERROR, MaterialTweaker.MODID + ": " + "Failed to create new tool repair file.");
                else Files.write(toolRepairFile.toPath(),
                        (       "//List of Tool Materials and their replacement repair material, one entry per line.\n" +
                                "//String materialName, String repairItem, int repairItemMetadata (* for any)\n" +
                                "//Example:\n" +
                                "//IRON,minecraft:stone,*\n"
                        ).getBytes(StandardCharsets.UTF_8));
            }
            else {
                List<String> list = Files.lines(toolRepairFile.toPath())
                        .map(String::trim)
                        .filter(s -> !s.startsWith("//"))
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
                for(String entry : list) {
                    String[] entryArray = Arrays.stream(entry.split(",")).map(String::trim).toArray(String[]::new);
                    toolRepairMap.put(entryArray[0], new String[]{entryArray[1],entryArray[2]});
                }
            }
            return true;
        }
        catch(Exception ex) {
            MaterialTweaker.LOGGER.log(Level.ERROR, MaterialTweaker.MODID + ": " + "Failed to initialize tool repair file: " + ex);
            return false;
        }
    }

    private static boolean initArmorAttributes() {
        if(configFolder==null && !initDirectory()) return false;
        armorAttributeFile = new File(configFolder, "armorattributes.cfg");
        try {
            if(!armorAttributeFile.exists()) {
                if(!armorAttributeFile.createNewFile()) MaterialTweaker.LOGGER.log(Level.ERROR, MaterialTweaker.MODID + ": " + "Failed to create new armor attribute file.");
                else Files.write(armorAttributeFile.toPath(),
                        (       "//List of Armor Materials and their attributes to be changed, one entry per line.\n" +
                                "//String materialName, int durabilityFactor, int damageReductionFeet, int damageReductionLegs, int damageReductionChest, int damageReductionHead, int enchantability, float toughness\n" +
                                "//Example:\n" +
                                "//LEATHER,5,1,3,5,4,5,1.0\n"
                        ).getBytes(StandardCharsets.UTF_8));
            }
            else {
                List<String> list = Files.lines(armorAttributeFile.toPath())
                        .map(String::trim)
                        .filter(s -> !s.startsWith("//"))
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
                for(String entry : list) {
                    String[] entryArray = Arrays.stream(entry.split(",")).map(String::trim).toArray(String[]::new);
                    armorAttributeMap.put(entryArray[0],
                            new ArmorAttributeEntry(
                                    Integer.parseInt(entryArray[1]),
                                    Integer.parseInt(entryArray[2]),
                                    Integer.parseInt(entryArray[3]),
                                    Integer.parseInt(entryArray[4]),
                                    Integer.parseInt(entryArray[5]),
                                    Integer.parseInt(entryArray[6]),
                                    Float.parseFloat(entryArray[7])
                            ));
                }
            }
            return true;
        }
        catch(Exception ex) {
            MaterialTweaker.LOGGER.log(Level.ERROR, MaterialTweaker.MODID + ": " + "Failed to initialize armor attribute file: " + ex);
            return false;
        }
    }

    private static boolean initArmorRepairs() {
        if(configFolder==null && !initDirectory()) return false;
        armorRepairFile = new File(configFolder, "armorrepairs.cfg");
        try {
            if(!armorRepairFile.exists()) {
                if(!armorRepairFile.createNewFile()) MaterialTweaker.LOGGER.log(Level.ERROR, MaterialTweaker.MODID + ": " + "Failed to create new armor repair file.");
                else Files.write(armorRepairFile.toPath(),
                        (       "//List of Armor Materials and their replacement repair material, one entry per line.\n" +
                                "//String materialName, String repairItem, int repairItemMetadata (* for any)\n" +
                                "//Example:\n" +
                                "//DIAMOND,minecraft:leather,*\n"
                        ).getBytes(StandardCharsets.UTF_8));
            }
            else {
                List<String> list = Files.lines(armorRepairFile.toPath())
                        .map(String::trim)
                        .filter(s -> !s.startsWith("//"))
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
                for(String entry : list) {
                    String[] entryArray = Arrays.stream(entry.split(",")).map(String::trim).toArray(String[]::new);
                    armorRepairMap.put(entryArray[0], new String[]{entryArray[1],entryArray[2]});
                }
            }
            return true;
        }
        catch(Exception ex) {
            MaterialTweaker.LOGGER.log(Level.ERROR, MaterialTweaker.MODID + ": " + "Failed to initialize armor repair file: " + ex);
            return false;
        }
    }

    private static boolean initItemOverrideRepairs() {
        if(configFolder==null && !initDirectory()) return false;
        itemOverrideRepairFile = new File(configFolder, "itemoverriderepairs.cfg");
        try {
            if(!itemOverrideRepairFile.exists()) {
                if(!itemOverrideRepairFile.createNewFile()) MaterialTweaker.LOGGER.log(Level.ERROR, MaterialTweaker.MODID + ": " + "Failed to create new item override repair file.");
                else Files.write(itemOverrideRepairFile.toPath(),
                        (       "//List of individual items and their repair item, one entry per line.\n" +
                                "//String targetItemName, String repairItemName, int repairItemMetadata (* for any)\n" +
                                "//Example:\n" +
                                "//minecraft:wooden_sword,minecraft:iron_ingot,*\n"
                        ).getBytes(StandardCharsets.UTF_8));
            }
            else {
                List<String> list = Files.lines(itemOverrideRepairFile.toPath())
                        .map(String::trim)
                        .filter(s -> !s.startsWith("//"))
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
                for(String entry : list) {
                    String[] entryArray = Arrays.stream(entry.split(",")).map(String::trim).toArray(String[]::new);
                    itemOverrideRepairMap.put(entryArray[0], new String[]{entryArray[1],entryArray[2]});
                }
            }
            return true;
        }
        catch(Exception ex) {
            MaterialTweaker.LOGGER.log(Level.ERROR, MaterialTweaker.MODID + ": " + "Failed to initialize item override repair file: " + ex);
            return false;
        }
    }
     */
}
