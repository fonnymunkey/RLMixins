package rlmixins.wrapper;

import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public abstract class BaublesWrapper {
	
	public static boolean isBaubleEquipped(EntityPlayer player, Item item) {
		return BaublesApi.isBaubleEquipped(player, item) != -1;
	}
}