package rlmixins.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.init.SRPItems;
import com.dhanantry.scapeandrunparasites.util.config.SRPConfig;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.RLMixins;
import rlmixins.handlers.ForgeConfigHandler;

@Mixin(SRPItems.class)
public abstract class SRPItemsStrangeBoneMixin {

    @Shadow(remap = false)
    public static Item bone;

    @Inject(
            method="init",
            at=@At(value="TAIL"),
            remap=false
    )
    private static void increaseStacksizeMixin(CallbackInfo ci){
        if(ForgeConfigHandler.mixinConfig.increaseStrangeBoneStackSize) {
            bone.setMaxStackSize(16);
        }
    }
}