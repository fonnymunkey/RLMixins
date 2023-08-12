package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Prop_Sector.ArrowPropertiesHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ArrowPropertiesHandler.class)
public abstract class ArrowPropertiesHandlerMixin {

    /**
     * @author fonnymunkey
     * @reason Modify handler to fix crash with TickDynamic
     */
    @Overwrite(remap = false)
    @SubscribeEvent(
            priority = EventPriority.HIGHEST,
            receiveCanceled = true
    )
    public void onEvent(TickEvent.WorldTickEvent event) {
        //Disabled because Starfall isnt even implemented, this has just been taking up processing
        /*
        if(event.phase == TickEvent.Phase.START) {
            ++this.counter;
            if(this.counter % 20 == 0) {
                this.counter = 0;
                List<Entity> entities = event.world.loadedEntityList;

                for(Entity entity : entities) {
                    if(entity instanceof EntityArrow) {
                        if(entity.hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null)) {
                            IArrowProperties ar = (IArrowProperties)entity.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
                            if(ar.getIsStarFallMade()) {
                                NBTTagCompound life = new NBTTagCompound();
                                entity.writeToNBT(life);
                                short f = life.getShort("life");
                                life.setShort("life", (short)(f + 50));
                                entity.readFromNBT(life);
                            }
                        }
                    }
                }
            }
        }
         */
    }
}