package rlmixins.mixin.lycanites.boundingbox;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.creature.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import rlmixins.wrapper.LycanitesBBWrapper;

import javax.annotation.Nullable;
import java.util.function.Function;

@Mixin(value = {
        EntityAbaia.class,
        EntityAbtu.class,
        EntityAegis.class,
        EntityAfrit.class,
        EntityAglebemu.class,
        EntityArchvile.class,
        EntityArgus.class,
        EntityArix.class,
        EntityAspid.class,
        EntityAstaroth.class,
        EntityArisaur.class,
        EntityBalayang.class,
        EntityBanshee.class,
        EntityBarghest.class,
        EntityBeholder.class,
        EntityBobeko.class,
        EntityCalpod.class,
        EntityChupacabra.class,
        EntityCinder.class,
        EntityClink.class,
        EntityCockatrice.class,
        EntityConcapedeHead.class,
        EntityConcapedeSegment.class,
        EntityDawon.class,
        EntityDjinn.class,
        EntityDweller.class,
        EntityEechetik.class,
        EntityEnt.class,
        EntityErepede.class,
        EntityEttin.class,
        EntityEyewig.class,
        EntityFeradon.class,
        EntityGeken.class,
        EntityGeonach.class,
        EntityGorger.class,
        EntityGrell.class,
        EntityGrue.class,
        EntityHerma.class,
        EntityIgnibus.class,
        EntityIka.class,
        EntityIoray.class,
        EntityJabberwock.class,
        EntityJengu.class,
        EntityJoustAlpha.class,
        EntityKhalk.class,
        EntityKrake.class,
        EntityLobber.class,
        EntityLurker.class,
        EntityMaka.class,
        EntityMakaAlpha.class,
        EntityMaug.class,
        EntityMorock.class,
        EntityPinky.class,
        EntityQuetzodracl.class,
        EntityQuillbeast.class,
        EntityRaiko.class,
        EntityReiver.class,
        EntityRemobra.class,
        EntityRoa.class,
        EntityRoc.class,
        EntitySalamander.class,
        EntitySerpix.class,
        EntityShade.class,
        EntityShambler.class,
        EntitySkylus.class,
        EntitySpectre.class,
        EntitySpriggan.class,
        EntityStrider.class,
        EntitySylph.class,
        EntityThresher.class,
        EntityTpumpkyn.class,
        EntityTremor.class,
        EntityTriffid.class,
        EntityTroll.class,
        EntityUvaraptor.class,
        EntityVapula.class,
        EntityVentoraptor.class,
        EntityVespid.class,
        EntityVespidQueen.class,
        EntityVolcan.class,
        EntityVorach.class,
        EntityWarg.class,
        EntityWildkin.class,
        EntityWraith.class,
        EntityXaphan.class,
        EntityYale.class,
        EntityZoataur.class,})
public abstract class UndersizedBBRenderMixin extends BaseCreatureEntity {

    public UndersizedBBRenderMixin(World world) { super(world); }

    /**
     * Fix undersized render bounding box
     */
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        //if(ForgeConfigHandler.client.targetEntity.equals(this.getClass().getSimpleName())) return this.getEntityBoundingBox().grow(ForgeConfigHandler.client.growXZ, ForgeConfigHandler.client.growY, ForgeConfigHandler.client.growXZ).offset(0.0, ForgeConfigHandler.client.offsetY, 0.0);
        return execNullable(
                LycanitesBBWrapper.lookup.get(this.getClass().getSimpleName()),
                b -> this.getEntityBoundingBox().grow(b[0], b[1], b[0]).offset(0, b[2], 0),
                super.getRenderBoundingBox());
    }

    /**
     * From BetterCombat
     */
    private static <T, R> R execNullable(@Nullable T obj, Function<T, R> onNonNull, R orElse) {
        if(obj != null ) return onNonNull.apply(obj);
        return orElse;
    }
}