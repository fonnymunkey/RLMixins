package rlmixins.mixin.fancymenu;

import de.keksuccino.fancymenu.FancyMenu;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import rlmixins.wrapper.ClientWrapper;

import java.io.File;

@Mixin(FancyMenu.class)
public abstract class FancyMenuMixin {
	
	/**
	 * @author fonnymunkey
	 * @reason fix crashing when loaded on server
	 */
	@Overwrite(remap = false)
	public static File getGameDirectory() {
		return FMLCommonHandler.instance().getSide() == Side.CLIENT ? ClientWrapper.getGameDir() : new File("");
	}
}