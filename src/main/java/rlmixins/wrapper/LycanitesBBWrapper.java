package rlmixins.wrapper;

import com.lycanitesmobs.core.entity.creature.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class LycanitesBBWrapper {

    public static final Map<String, double[]> lookup = createMap();
    private static Map<String, double[]> createMap() {
        Map<String, double[]> map = new HashMap<>();//.grow([0], [1], [0]).offset(0, [2], 0)
        map.put(EntityAbaia.class.getSimpleName(), new double[]{1.5, 0.0, 0.0});
        map.put(EntityAbtu.class.getSimpleName(), new double[]{0.5, -0.5, 0.0});
        map.put(EntityAegis.class.getSimpleName(), new double[]{0.8, 0.0, 0.0});
        map.put(EntityAfrit.class.getSimpleName(), new double[]{0.5, 0.0, 0.5});
        map.put(EntityAglebemu.class.getSimpleName(), new double[]{0.5, 0.0, 0.0});
        map.put(EntityArchvile.class.getSimpleName(), new double[]{0.5, 0.0, 0.0});
        map.put(EntityArgus.class.getSimpleName(), new double[]{2.0, 1.0, 0.0});
        map.put(EntityArix.class.getSimpleName(), new double[]{1.0, 0.2, 0.5});
        map.put(EntityAspid.class.getSimpleName(), new double[]{1.0, 0.5, 0.0});
        map.put(EntityAstaroth.class.getSimpleName(), new double[]{1.0, 2.0, 0.0});
        map.put(EntityArisaur.class.getSimpleName(), new double[]{4.0, 7.0, 0.0});
        map.put(EntityBalayang.class.getSimpleName(), new double[]{3.0, 1.0, 0.0});
        map.put(EntityBanshee.class.getSimpleName(), new double[]{0.5, 0.8, 0.0});
        map.put(EntityBarghest.class.getSimpleName(), new double[]{2.0, -0.5, 0.0});
        map.put(EntityBeholder.class.getSimpleName(), new double[]{0.5, 0.5, 0.5});
        map.put(EntityBobeko.class.getSimpleName(), new double[]{2.0, 0.0, 0.0});
        map.put(EntityCalpod.class.getSimpleName(), new double[]{1.5, 0.5, 0.0});
        map.put(EntityChupacabra.class.getSimpleName(), new double[]{1.5, 0.5, 0.0});
        map.put(EntityCinder.class.getSimpleName(), new double[]{0.8, 0.5, 0.0});
        map.put(EntityClink.class.getSimpleName(), new double[]{1.0, 0.0, 0.0});
        map.put(EntityCockatrice.class.getSimpleName(), new double[]{5.0, 2.0, 1.5});
        map.put(EntityConcapedeHead.class.getSimpleName(), new double[]{1.0, 0.0, 0.0});
        map.put(EntityConcapedeSegment.class.getSimpleName(), new double[]{1.0, 0.0, 0.0});
        map.put(EntityDawon.class.getSimpleName(), new double[]{1.5, 0.5, 0.0});
        map.put(EntityDjinn.class.getSimpleName(), new double[]{0.5, 0.0, 0.0});
        map.put(EntityDweller.class.getSimpleName(), new double[]{1.2, 0.0, 0.0});
        map.put(EntityEechetik.class.getSimpleName(), new double[]{2.0, 0.5, 0.0});
        map.put(EntityEnt.class.getSimpleName(), new double[]{1.5, 1.0, 0.5});
        map.put(EntityErepede.class.getSimpleName(), new double[]{1.5, 0.5, 0.0});
        map.put(EntityEttin.class.getSimpleName(), new double[]{2.0, 0.5, 0.0});
        map.put(EntityEyewig.class.getSimpleName(), new double[]{2.0, 0.0, 0.0});
        map.put(EntityFeradon.class.getSimpleName(), new double[]{2.5, 0.5, 0.0});
        map.put(EntityGeken.class.getSimpleName(), new double[]{1.2, 0.0, 0.0});
        map.put(EntityGeonach.class.getSimpleName(), new double[]{1.2, 0.5, 0.2});
        map.put(EntityGorger.class.getSimpleName(), new double[]{2.0, 0.0, 0.0});
        map.put(EntityGrell.class.getSimpleName(), new double[]{1.0, 2.0, -1.0});
        map.put(EntityGrue.class.getSimpleName(), new double[]{1.5, 0.5, 0.0});
        map.put(EntityHerma.class.getSimpleName(), new double[]{1.2, 0.0, 0.0});
        map.put(EntityIgnibus.class.getSimpleName(), new double[]{8.0, 2.0, 0.0});
        map.put(EntityIka.class.getSimpleName(), new double[]{1.5, 0.0, 0.0});
        map.put(EntityIoray.class.getSimpleName(), new double[]{1.5, 0.0, 0.0});
        map.put(EntityJabberwock.class.getSimpleName(), new double[]{1.5, 0.0, 0.2});
        map.put(EntityJengu.class.getSimpleName(), new double[]{1.0, 0.5, 0.0});
        map.put(EntityJoustAlpha.class.getSimpleName(), new double[]{0.8, 1.0, 0.0});
        map.put(EntityKhalk.class.getSimpleName(), new double[]{5.0, 0.5, 0.0});
        map.put(EntityKrake.class.getSimpleName(), new double[]{1.0, 0.5, 0.0});
        map.put(EntityLobber.class.getSimpleName(), new double[]{3.0, 0.0, 0.0});
        map.put(EntityLurker.class.getSimpleName(), new double[]{1.0, 0.0, 0.0});
        map.put(EntityMaka.class.getSimpleName(), new double[]{3.0, -0.5, 0.0});
        map.put(EntityMakaAlpha.class.getSimpleName(), new double[]{4.0, 0.5, 0.5});
        map.put(EntityMaug.class.getSimpleName(), new double[]{1.5, 0.5, 0.0});
        map.put(EntityMorock.class.getSimpleName(), new double[]{7.0, 3.0, 0.0});
        map.put(EntityPinky.class.getSimpleName(), new double[]{1.5, 0.5, 0.0});
        map.put(EntityQuetzodracl.class.getSimpleName(), new double[]{4.0, 0.0, 0.0});
        map.put(EntityQuillbeast.class.getSimpleName(), new double[]{0.5, 0.5, 0.0});
        map.put(EntityRaiko.class.getSimpleName(), new double[]{4.0, 1.0, 1.0});
        map.put(EntityReiver.class.getSimpleName(), new double[]{1.2, 0.5, 0.2});
        map.put(EntityRemobra.class.getSimpleName(), new double[]{0.5, 0.0, 0.0});
        map.put(EntityRoa.class.getSimpleName(), new double[]{2.5, 0.0, -0.5});
        map.put(EntityRoc.class.getSimpleName(), new double[]{2.0, 1.0, 0.0});
        map.put(EntitySalamander.class.getSimpleName(), new double[]{2.5, 0.0, 0.0});
        map.put(EntitySerpix.class.getSimpleName(), new double[]{3.0, 0.0, 0.0});
        map.put(EntityShade.class.getSimpleName(), new double[]{1.0, 0.0, 0.0});
        map.put(EntityShambler.class.getSimpleName(), new double[]{1.0, 0.5, 0.0});
        map.put(EntitySkylus.class.getSimpleName(), new double[]{2.0, 0.0, 0.0});
        map.put(EntitySpectre.class.getSimpleName(), new double[]{0.5, 0.5, 0.0});
        map.put(EntitySpriggan.class.getSimpleName(), new double[]{0.5, 0.5, 0.0});
        map.put(EntityStrider.class.getSimpleName(), new double[]{1.5, 0.5, 0.5});
        map.put(EntitySylph.class.getSimpleName(), new double[]{1.5, 0.5, 0.5});
        map.put(EntityThresher.class.getSimpleName(), new double[]{10.0, 1.5, 1.0});
        map.put(EntityTpumpkyn.class.getSimpleName(), new double[]{1.0, 0.5, 0.0});
        map.put(EntityTremor.class.getSimpleName(), new double[]{0.5, 0.5, 0.0});
        map.put(EntityTriffid.class.getSimpleName(), new double[]{2.0, 0.5, 0.0});
        map.put(EntityTroll.class.getSimpleName(), new double[]{1.0, 0.0, 0.0});
        map.put(EntityUvaraptor.class.getSimpleName(), new double[]{2.0, 0.0, 0.5});
        map.put(EntityVapula.class.getSimpleName(), new double[]{0.5, 0.5, 0.0});
        map.put(EntityVentoraptor.class.getSimpleName(), new double[]{2.0, 0.0, 0.5});
        map.put(EntityVespid.class.getSimpleName(), new double[]{1.5, 1.0, 0.0});
        map.put(EntityVespidQueen.class.getSimpleName(), new double[]{1.0, 1.0, -0.5});
        map.put(EntityVolcan.class.getSimpleName(), new double[]{0.5, 0.5, 0.0});
        map.put(EntityVorach.class.getSimpleName(), new double[]{1.0, 0.0, 0.0});
        map.put(EntityWarg.class.getSimpleName(), new double[]{1.0, 0.0, 0.0});
        map.put(EntityWildkin.class.getSimpleName(), new double[]{0.5, 0.0, 0.0});
        map.put(EntityWraith.class.getSimpleName(), new double[]{1.0, 0.0, 0.0});
        map.put(EntityXaphan.class.getSimpleName(), new double[]{0.5, 1.0, 0.0});
        map.put(EntityYale.class.getSimpleName(), new double[]{2.0, 0.0, 0.5});
        map.put(EntityZoataur.class.getSimpleName(), new double[]{4.0, 2.0, 2.0});

        return Collections.unmodifiableMap(map);
    }
}