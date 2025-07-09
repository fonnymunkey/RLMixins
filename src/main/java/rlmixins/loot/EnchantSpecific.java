package rlmixins.loot;

import com.google.gson.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import rlmixins.RLMixins;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnchantSpecific extends LootFunction {
	
	List<Tuple<Enchantment, RandomValueRange>> enchantmentList;
	
	public EnchantSpecific(LootCondition[] conditionsIn, List<Tuple<Enchantment, RandomValueRange>> enchantmentList) {
		super(conditionsIn);
		this.enchantmentList = enchantmentList;
	}
	
	@Override
	public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
		if(this.enchantmentList != null && !this.enchantmentList.isEmpty()) {
			boolean book = stack.getItem() == Items.BOOK;
			if(book) stack = new ItemStack(Items.ENCHANTED_BOOK);
			
			for(Tuple<Enchantment, RandomValueRange> pair : enchantmentList) {
				int i = pair.getSecond().generateInt(rand);
				
				if(book) {
					ItemEnchantedBook.addEnchantment(stack, new EnchantmentData(pair.getFirst(), i));
				}
				else {
					stack.addEnchantment(pair.getFirst(), i);
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
					Tuple<Enchantment, RandomValueRange> pair = functionClazz.enchantmentList.get(0);
					ResourceLocation loc = Enchantment.REGISTRY.getNameForObject(pair.getFirst());
					if(loc == null) {
						throw new IllegalArgumentException("Don't know how to serialize enchantment " + pair.getFirst());
					}
					object.add("enchantment", new JsonPrimitive(loc.toString()));
					object.add("levels", serializationContext.serialize(pair.getSecond()));
				}
				else {
					JsonArray jsonArray = new JsonArray();
					for(Tuple<Enchantment, RandomValueRange> pair : functionClazz.enchantmentList) {
						ResourceLocation loc = Enchantment.REGISTRY.getNameForObject(pair.getFirst());
						if(loc == null) {
							throw new IllegalArgumentException("Don't know how to serialize enchantment " + pair.getFirst());
						}
						JsonObject object1 = new JsonObject();
						object1.add("enchantment", new JsonPrimitive(loc.toString()));
						object1.add("levels", serializationContext.serialize(pair.getSecond()));
						jsonArray.add(object1);
					}
					object.add("enchantments", jsonArray);
				}
			}
		}
		
		public EnchantSpecific deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
			List<Tuple<Enchantment, RandomValueRange>> enchantmentList = new ArrayList<>();
			if(object.has("enchantment")) {
				String s = JsonUtils.getString(object, "enchantment");
				Enchantment ench = Enchantment.REGISTRY.getObject(new ResourceLocation(s));
				if(ench == null) {
					throw new JsonSyntaxException("Unknown enchantment '" + s + "'");
				}
				RandomValueRange lvl = JsonUtils.deserializeClass(object, "levels", deserializationContext, RandomValueRange.class);
				enchantmentList.add(new Tuple<>(ench, lvl));
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
							enchantmentList.add(new Tuple<>(ench, lvl));
						}
					}
				}
			}
			return new EnchantSpecific(conditionsIn, enchantmentList);
		}
	}
}