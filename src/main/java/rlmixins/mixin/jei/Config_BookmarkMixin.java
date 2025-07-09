package rlmixins.mixin.jei;

import mezz.jei.config.Config;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(Config.class)
public abstract class Config_BookmarkMixin {
	
	@Unique
	private static File rlmixins$jeiConfigDir = null;
	
	@Inject(
			method = "preInit",
			at = @At("HEAD"),
			remap = false
	)
	private static void rlmixins_jeiConfig_preInit_inject(FMLPreInitializationEvent event, CallbackInfo ci) {
		rlmixins$jeiConfigDir = new File(event.getModConfigurationDirectory(), "jei");
	}
	
	@Redirect(
			method = "preInit",
			at = @At(value = "NEW", target = "(Ljava/io/File;Ljava/lang/String;)Ljava/io/File;", ordinal = 2)
	)
	private static File rlmixins_jeiConfig_preInit_redirect0(File file, String s) {
		File bookmarkReplace;
		if(rlmixins$jeiConfigDir == null) bookmarkReplace = new File(file, s);
		else bookmarkReplace = new File(rlmixins$jeiConfigDir, "bookmarks.ini");
		rlmixins$jeiConfigDir = null;
		return bookmarkReplace;
	}
	
	@Redirect(
			method = "preInit",
			at = @At(value = "INVOKE", target = "Ljava/io/File;exists()Z", ordinal = 1),
			remap = false
	)
	private static boolean rlmixins_jeiConfig_preInit_redirect1(File instance) {
		return true;
	}
}