package rlmixins.loot;

import com.google.gson.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import rlmixins.RLMixins;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PotionSpecific extends LootFunction {
	
	private final PotionType potionType;
	private final List<PotionEffect> potionEffects;
	
	public PotionSpecific(LootCondition[] conditionsIn, PotionType potionType, List<PotionEffect> potionEffects) {
		super(conditionsIn);
		this.potionType = potionType;
		this.potionEffects = potionEffects;
	}
	
	@Override
	public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
		if(this.potionType == null && (this.potionEffects == null || this.potionEffects.isEmpty())) return stack;
		if(stack.getItem() == Items.ARROW) {
			stack = new ItemStack(Items.TIPPED_ARROW, stack.getCount());
		}
		else if(stack.getItem() == Items.GLASS_BOTTLE) {
			stack = new ItemStack(Items.POTIONITEM);
		}
		if(this.potionType != null) PotionUtils.addPotionToItemStack(stack, this.potionType);
		if(this.potionEffects != null && !this.potionEffects.isEmpty()) PotionUtils.appendEffects(stack, this.potionEffects);
		
		return stack;
	}
	
	public static class Serializer extends LootFunction.Serializer<PotionSpecific> {
		
		public Serializer() {
			super(new ResourceLocation(RLMixins.MODID + ":" + "potion_specific"), PotionSpecific.class);
		}
		
		public void serialize(JsonObject object, PotionSpecific functionClazz, JsonSerializationContext serializationContext) {
			if(functionClazz.potionType != null) {
				ResourceLocation loc = PotionType.REGISTRY.getNameForObject(functionClazz.potionType);
				object.add("potionType", new JsonPrimitive(loc.toString()));
			}
			if(functionClazz.potionEffects != null && !functionClazz.potionEffects.isEmpty()) {
				JsonArray jsonarray = new JsonArray();
				for(PotionEffect potionEffect : functionClazz.potionEffects) {
					ResourceLocation loc = Potion.REGISTRY.getNameForObject(potionEffect.getPotion());
					if(loc == null) {
						throw new IllegalArgumentException("Don't know how to serialize potion " + potionEffect);
					}
					JsonObject obj = new JsonObject();
					obj.add("potion", new JsonPrimitive(loc.toString()));
					obj.addProperty("amplifier", potionEffect.getAmplifier());
					obj.addProperty("duration", potionEffect.getDuration());
					obj.addProperty("ambient", potionEffect.getIsAmbient());
					obj.addProperty("showParticles", potionEffect.doesShowParticles());
					jsonarray.add(obj);
				}
				object.add("potionEffects", jsonarray);
			}
		}
		
		public PotionSpecific deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
			PotionType potionType = null;
			if(object.has("potionType")) {
				String s = JsonUtils.getString(object, "potionType");
				potionType = PotionType.REGISTRY.getObject(new ResourceLocation(s));
			}
			List<PotionEffect> potionEffects = new ArrayList<>();
			if(object.has("potionEffects")) {
				for(JsonElement element : JsonUtils.getJsonArray(object, "potionEffects")) {
					if(element instanceof JsonObject) {
						JsonObject object1 = (JsonObject)element;
						String s = JsonUtils.getString(object1, "potion");
						Potion potion = Potion.REGISTRY.getObject(new ResourceLocation(s));
						if(potion == null) {
							throw new JsonSyntaxException("Unknown potion '" + s + "'");
						}
						int amplifier = JsonUtils.getInt(object1, "amplifier", 0);
						int duration = JsonUtils.getInt(object1, "duration", 0);
						boolean ambient = JsonUtils.getBoolean(object1, "ambient", false);
						boolean showParticles = JsonUtils.getBoolean(object1, "showParticles", true);
						potionEffects.add(new PotionEffect(potion, amplifier, duration, ambient, showParticles));
					}
				}
			}
			return new PotionSpecific(conditionsIn, potionType, potionEffects);
		}
	}
}