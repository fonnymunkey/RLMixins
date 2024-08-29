package rlmixins.wrapper;

import net.minecraft.client.Minecraft;

import java.io.File;

public abstract class ClientWrapper {
	
	public static File getGameDir() {
		return Minecraft.getMinecraft().gameDir;
	}
}