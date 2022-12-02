package rlmixins.handlers;

import com.lycanitesmobs.core.entity.creature.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class LycanitesBBHandler {

    public static final Map<String, double[]> lookup;
    static {
        Map<String, double[]> map = new HashMap<>();//.grow([0], [1], [0]).offset(0, [2], 0)
        map.put(EntityAfrit.class.getSimpleName(), new double[]{0.5, 0.0, 0.5});
        map.put(EntityArix.class.getSimpleName(), new double[]{1.0, 0.2, 0.5});
        map.put(EntityBalayang.class.getSimpleName(), new double[]{3.0, 1.0, 0.0});
        map.put(EntityBarghest.class.getSimpleName(), new double[]{2.0, -0.5, 0.0});
        map.put(EntityCockatrice.class.getSimpleName(), new double[]{5.0, 2.0, 1.5});
        map.put(EntityIgnibus.class.getSimpleName(), new double[]{8.0, 2.0, 0.0});
        map.put(EntityMakaAlpha.class.getSimpleName(), new double[]{4.0, 0.5, 0.5});
        map.put(EntityMaka.class.getSimpleName(), new double[]{3.0, -0.5, 0.0});
        map.put(EntityQuetzodracl.class.getSimpleName(), new double[]{4.0, 0.0, 0.0});
        map.put(EntityQuillbeast.class.getSimpleName(), new double[]{0.5, 0.5, 0.0});
        map.put(EntityRaiko.class.getSimpleName(), new double[]{4.0, 1.0, 1.0});
        map.put(EntityRoc.class.getSimpleName(), new double[]{2.0, 1.0, 0.0});
        map.put(EntitySkylus.class.getSimpleName(), new double[]{2.0, 0.0, 0.0});
        map.put(EntityTriffid.class.getSimpleName(), new double[]{2.0, 0.5, 0.0});
        map.put(EntityUvaraptor.class.getSimpleName(), new double[]{2.0, 0.0, 0.5});
        map.put(EntityVentoraptor.class.getSimpleName(), new double[]{2.0, 0.0, 0.5});
        map.put(EntityYale.class.getSimpleName(), new double[]{2.0, 0.0, 0.5});
        map.put(EntityZoataur.class.getSimpleName(), new double[]{4.0, 2.0, 2.0});

        lookup = Collections.unmodifiableMap(map);
    }
}