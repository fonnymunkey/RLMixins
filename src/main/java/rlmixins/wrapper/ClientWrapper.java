package rlmixins.wrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;

import java.io.File;

public abstract class ClientWrapper {
	
	public static File getGameDir() {
		return Minecraft.getMinecraft().gameDir;
	}
	
	public static void setVelocity(EntityLivingBase entity, double x, double y, double z) {
		entity.setVelocity(x, y, z);
	}
}