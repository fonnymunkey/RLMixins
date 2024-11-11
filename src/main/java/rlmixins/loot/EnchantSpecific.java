package rlmixins.loot;

import com.google.gson.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import rlmixins.RLMixins;
import rlmixins.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnchantSpecific extends LootFunction {
	
	List<Pair<Enchantment, RandomValueRange>> enchantmentList;
	
	public EnchantSpecific(LootCondition[] conditionsIn, List<Pair<Enchantment, RandomValueRange>> enchantmentList) {
		super(conditionsIn);
		this.enchantmentList = enchantmentList;
	}
	
	@Override
	public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
		if(this.enchantmentList != null && !this.enchantmentList.isEmpty()) {
			boolean book = stack.getItem() == Items.BOOK;
			if(book) stack = new ItemStack(Items.ENCHANTED_BOOK);
			
			for(Pair<Enchantment, RandomValueRange> pair : enchantmentList) {
				int i = pair.getRight().generateInt(rand);
				
				if(book) {
					ItemEnchantedBook.addEnchantment(stack, new EnchantmentData(pair.getLeft(), i));
				}
				else {
					stack.addEnchantment(pair.getLeft(), i);
				}
			}
		}
		
		return stack;
	}
	
	public static class Serializer extends LootFunction.Serializer<EnchantSpecific> {
		
		public Serializer() {
			super(new ResourceLocation(RLMixins.MODID + ":" + "enchant_specific"), EnchantSpecific.class);
		}
		
		public void serialize(JsonObject object, EnchantSpecific functionClazz, JsonSerializationContext serializationContext) {
			if(functionClazz.enchantmentList != null && !functionClazz.enchantmentList.isEmpty()) {
				if(functionClazz.enchantmentList.size() == 1) {
					Pair<Enchantment, RandomValueRange> pair = functionClazz.enchantmentList.get(0);
					ResourceLocation loc = Enchantment.REGISTRY.getNameForObject(pair.getLeft());
					if(loc == null) {
						throw new IllegalArgumentException("Don't know how to serialize enchantment " + pair.getLeft());
					}
					object.add("enchantment", new JsonPrimitive(loc.toString()));
					object.add("levels", serializationContext.serialize(pair.getRight()));
				}
				else {
					JsonArray jsonArray = new JsonArray();
					for(Pair<Enchantment, RandomValueRange> pair : functionClazz.enchantmentList) {
						ResourceLocation loc = Enchantment.REGISTRY.getNameForObject(pair.getLeft());
						if(loc == null) {
							throw new IllegalArgumentException("Don't know how to serialize enchantment " + pair.getLeft());
						}
						JsonObject object1 = new JsonObject();
						object1.add("enchantment", new JsonPrimitive(loc.toString()));
						object1.add("levels", serializationContext.serialize(pair.getRight()));
						jsonArray.add(object1);
					}
					object.add("enchantments", jsonArray);
				}
			}
		}
		
		public EnchantSpecific deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
			List<Pair<Enchantment, RandomValueRange>> enchantmentList = new ArrayList<>();
			if(object.has("enchantment")) {
				String s = JsonUtils.getString(object, "enchantment");
				Enchantment ench = Enchantment.REGISTRY.getObject(new ResourceLocation(s));
				if(ench == null) {
					throw new JsonSyntaxException("Unknown enchantment '" + s + "'");
				}
				RandomValueRange lvl = JsonUtils.deserializeClass(object, "levels", deserializationContext, RandomValueRange.class);
				enchantmentList.add(new Pair<>(ench, lvl));
			}
			else if(object.has("enchantments")) {
				for(JsonElement jsonElement : JsonUtils.getJsonArray(object, "enchantments")) {
					if(jsonElement instanceof JsonObject) {
						JsonObject object1 = (JsonObject)jsonElement;
						if(object1.has("enchantment")) {
							String s = JsonUtils.getString(object1, "enchantment");
							Enchantment ench = Enchantment.REGISTRY.getObject(new ResourceLocation(s));
							if(ench == null) {
								throw new JsonSyntaxException("Unknown enchantment '" + s + "'");
							}
							RandomValueRange lvl = JsonUtils.deserializeClass(object1, "levels", deserializationContext, RandomValueRange.class);
							enchantmentList.add(new Pair<>(ench, lvl));
						}
					}
				}
			}
			return new EnchantSpecific(conditionsIn, enchantmentList);
		}
	}
}