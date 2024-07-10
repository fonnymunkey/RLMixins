package rlmixins.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.item.ModItems;
import cursedflames.bountifulbaubles.loot.ModLoot;
import net.minecraft.item.Item;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.ForgeConfigHandler;

@Mixin(ModLoot.class)
public abstract class ModLootMixin {

    @Redirect(
            method = "onLootTablesLoaded",
            at = @At(value = "NEW", target = "(Lnet/minecraft/item/Item;II[Lnet/minecraft/world/storage/loot/functions/LootFunction;[Lnet/minecraft/world/storage/loot/conditions/LootCondition;Ljava/lang/String;)Lnet/minecraft/world/storage/loot/LootEntryItem;", ordinal = 5)
    )
    private static LootEntryItem rlmixins_bountifulBaublesModLoot_onLootTablesLoaded(Item itemIn, int weightIn, int qualityIn, LootFunction[] functionsIn, LootCondition[] conditionsIn, String entryName) {
        return new LootEntryItem(itemIn, itemIn == ModItems.trinketLuckyHorseshoe ? ForgeConfigHandler.server.luckyHorseshoeWeight : weightIn, qualityIn, functionsIn, conditionsIn, entryName);
    }
}