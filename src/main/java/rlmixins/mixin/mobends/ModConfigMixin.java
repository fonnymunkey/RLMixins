package rlmixins.mixin.mobends;

import goblinbob.mobends.core.util.WildcardPattern;
import goblinbob.mobends.standard.AttackActionType;
import goblinbob.mobends.standard.UseActionType;
import goblinbob.mobends.standard.main.ModConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(ModConfig.class)
public abstract class ModConfigMixin {

    @Shadow(remap = false)
    public static String[] keepEntityAsVanilla;
    @Shadow(remap = false)
    private static Map<Item, Boolean> keepArmorAsVanillaCache;
    @Shadow(remap = false)
    private static Map<Item, UseActionType> itemUseClassificationCache;
    @Shadow(remap = false)
    private static Map<Item, AttackActionType> itemAttackClassificationCache;

    private static Map<Class<? extends Entity>, Boolean> keepEntityClassAsVanillaCache;

    @Shadow(remap = false)
    private static List<Map<?, ?>> caches = Arrays.asList(
            keepArmorAsVanillaCache = new HashMap<>(),
            keepEntityClassAsVanillaCache = new HashMap<>(),
            itemUseClassificationCache = new HashMap<>(),
            itemAttackClassificationCache = new HashMap<>()
    );

    /**
     * @author fonnymunkey
     * @reason fix memory leak found by Meldexun
     */
    @Overwrite(remap = false)
    public static boolean shouldKeepEntityAsVanilla(Entity entity) {
        return keepEntityClassAsVanillaCache.computeIfAbsent(entity.getClass(), e -> {
            ResourceLocation location = EntityList.getKey(e);
            return location != null && checkPattern(location, keepEntityAsVanilla);
        });
    }

    private static boolean checkPattern(ResourceLocation resourceLocation, String[] patterns) {
        String resourceDomain = resourceLocation.getNamespace();
        String resourcePath = resourceLocation.getPath();

        for(String pattern : patterns) {
            ResourceLocation patternLocation = new ResourceLocation(pattern);
            if(resourceLocation.equals(patternLocation)) return true;

            WildcardPattern domainPattern = new WildcardPattern(patternLocation.getNamespace());
            WildcardPattern pathPattern = new WildcardPattern(patternLocation.getPath());
            if(domainPattern.matches(resourceDomain) && pathPattern.matches(resourcePath)) return true;
        }
        return false;
    }
}