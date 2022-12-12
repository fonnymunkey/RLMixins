package rlmixins.handlers;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import bettercombat.mod.event.RLCombatCriticalHitEvent;
import bettercombat.mod.event.RLCombatModifyDamageEvent;
import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.lib.LibMisc;
import com.Shultrea.Rin.Enchantments_Sector.Smc_010;
import com.Shultrea.Rin.Enchantments_Sector.Smc_030;
import com.Shultrea.Rin.Enchantments_Sector.Smc_040;
import com.charles445.simpledifficulty.api.SDDamageSources;
import com.charles445.simpledifficulty.potion.PotionHypothermia;
import com.oblivioussp.spartanweaponry.util.EntityDamageSourceArmorPiercing;
import cursedflames.bountifulbaubles.baubleeffect.IFireResistance;
import cursedflames.bountifulbaubles.item.ItemShieldObsidian;
import cursedflames.bountifulbaubles.item.ModItems;
import ejektaflex.bountiful.block.BlockBountyBoard;
import ichttt.mods.firstaid.FirstAid;
import ichttt.mods.firstaid.api.damagesystem.AbstractDamageablePart;
import ichttt.mods.firstaid.api.event.FirstAidLivingDamageEvent;
import ichttt.mods.firstaid.common.network.MessageUpdatePart;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.Level;
import rlmixins.RLMixins;
import rlmixins.wrapper.InFModifierWrapper;

import java.util.*;

@Mod.EventBusSubscriber(modid = RLMixins.MODID)
public class EventHandler {
    /**
     * Broken Heart Trinket UUID
     */
    public static final UUID MODIFIER_UUID = UUID.fromString("554f3929-4193-4ae5-a4da-4b528a89ca32");

    /**
     * Reimplement Curse of Possession and Curse of Decay handlers in a non-laggy and buggy way
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if(event.isCanceled() || event.getEntity() == null || !(event.getEntity() instanceof EntityItem)) return;

        EntityItem itemEntity = (EntityItem)event.getEntity();
        ItemStack stack = itemEntity.getItem();

        if(EnchantmentHelper.getEnchantmentLevel(Smc_040.CurseofDecay, stack) > 0) {
            itemEntity.lifespan = 40;
            itemEntity.setPickupDelay(10);
            return;//CoP and CoD can not be applied together, so skip CoP check
        }

        if(EnchantmentHelper.getEnchantmentLevel(Smc_040.CurseofPossession, stack) > 0) {//Remove setting the item to never despawn, thats stupid, its a Curse
            EntityPlayer thrower = event.getWorld().getClosestPlayerToEntity(itemEntity, 8.0);//32 is way too large of a radius to check
            if(thrower != null && !thrower.isCreative()) {
                if(thrower.isEntityAlive()) {
                    if(thrower.addItemStackToInventory(stack)) {
                        event.setCanceled(true);
                        return;
                    }
                }
                itemEntity.lifespan = 5;//Delete item on drop if player isnt null but dead or cant be added to inventory
                itemEntity.setPickupDelay(10);
            }
        }
    }

    /*
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if(!(event.getEntityLiving() instanceof EntityPlayer)
                || !event.getEntityLiving().isEntityAlive()
                || event.getEntityLiving().world.isRemote
                || !(event.getSlot() == EntityEquipmentSlot.MAINHAND || event.getSlot() == EntityEquipmentSlot.OFFHAND)
                || event.getFrom().isEmpty()) return;
        //Only run if item removed has CoP, and new item doesn't have CoP
        if(EnchantmentHelper.getEnchantmentLevel(Smc_040.CurseofPossession, event.getFrom()) > 0 && EnchantmentHelper.getEnchantmentLevel(Smc_040.CurseofPossession, event.getTo()) <= 0) {
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            //TODO: play sound
            player.attackEntityFrom(DamageSource.MAGIC, 2);
        }
    }
     */

    /**
     * Handle Critical Strike and Luck Magnification enchantment in a non-broken and offhand-sensitive way
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onCritical(RLCombatCriticalHitEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if(player == null) return;
        ItemStack stack = event.getOffhand() ? player.getHeldItemOffhand() : player.getHeldItemMainhand();
        //Only trigger on an actual crit, not an attempted crit
        if(!stack.isEmpty() && (event.getResult() == Event.Result.ALLOW || (event.isVanillaCritical() && event.getResult() == Event.Result.DEFAULT))) {
            //Critical Strike
            int csLevel = EnchantmentHelper.getEnchantmentLevel(Smc_030.CriticalStrike, stack);
            if(csLevel > 0) {
                if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
                int counter = stack.getTagCompound().getInteger("failedCritCount");
                int maxReduction = csLevel * 50;

                if(player.world.rand.nextInt(1000 - maxReduction) >= 32 * (counter + 1)) stack.getTagCompound().setInteger("failedCritCount", counter + 1);
                else {
                    stack.getTagCompound().setInteger("failedCritCount", 0);
                    float crit = 0.4F + (float)csLevel * 0.4F + player.world.rand.nextFloat() * 0.5F;

                    player.world.playSound(null, player.posX, player.posY, player.posZ, ModRegistry.CRITICAL_STRIKE, SoundCategory.PLAYERS, 0.8F, 1.0F /(player.world.rand.nextFloat() * 0.4F + 1.2F)* 1.6F);

                    event.setDamageModifier(event.getDamageModifier() + crit);
                }
            }
            //Luck Magnification
            int lmLevel = EnchantmentHelper.getEnchantmentLevel(Smc_040.LuckMagnification, stack);
            if(lmLevel > 0) {
                IAttributeInstance luck = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.LUCK);
                float amount = (float)luck.getAttributeValue();

                if(amount != 0) {
                    if(player.world.rand.nextInt(100) < Math.abs(amount * (float)lmLevel)) {
                        event.setDamageModifier(event.getDamageModifier() + amount * (float)lmLevel * 0.1F);
                    }
                }
            }
        }
    }

    /**
     * Handle looting modifier from Luck Magnification
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLootingLevel(LootingLevelEvent event) {
        if(event.getDamageSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.getDamageSource().getTrueSource();
            //Not ideal, but event doesn't post the stack
            ItemStack stack = player.getHeldItemMainhand().isEmpty() ? player.getHeldItemOffhand() : player.getHeldItemMainhand();
            if(!stack.isEmpty()) {
                int level = EnchantmentHelper.getEnchantmentLevel(Smc_040.LuckMagnification, stack);
                if(level > 0) {
                    IAttributeInstance luck = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.LUCK);
                    int modifier = (int)((double)event.getLootingLevel() + luck.getAttributeValue() * (double)level / 2.0D);
                    event.setLootingLevel(modifier);
                }
            }
        }
    }

    /**
     * Handle Luck Magnification applying the luck effect
     */
    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if(event.player != null &&
                event.phase != TickEvent.Phase.START &&
                !event.player.world.isRemote &&
                event.player.ticksExisted%9 == 0) {
            int level = event.player.getHeldItemMainhand().isEmpty() ? 0 : EnchantmentHelper.getEnchantmentLevel(Smc_040.LuckMagnification, event.player.getHeldItemMainhand());
            if(level <= 0) level += event.player.getHeldItemOffhand().isEmpty() ? 0 : EnchantmentHelper.getEnchantmentLevel(Smc_040.LuckMagnification, event.player.getHeldItemOffhand());
            if(level > 0) {
                event.player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 10, level - 1, true, false));
            }
        }
    }

    /**
     * Handle improperly applied IceAndFire weapon attributes
     */
    @SubscribeEvent
    public static void modifyAttackDamagePre(RLCombatModifyDamageEvent.Pre event) {
        EntityPlayer player = event.getEntityPlayer();
        Entity target = event.getTarget();
        if(player == null || target == null || event.getStack().isEmpty()) return;

        Item item = event.getStack().getItem();
        if(InFModifierWrapper.isModifierClass(item)) {
            EnumCreatureAttribute attribute = target instanceof EntityLivingBase ? ((EntityLivingBase)target).getCreatureAttribute() : EnumCreatureAttribute.UNDEFINED;
            if(attribute == EnumCreatureAttribute.UNDEAD && InFModifierWrapper.isSilverWeapon(item)) {
                event.setDamageModifier(event.getDamageModifier() + 2.0F);
            }
            else if(InFModifierWrapper.isMyrmexWeapon(item)) {
                if(attribute != EnumCreatureAttribute.ARTHROPOD) {
                    event.setDamageModifier(event.getDamageModifier() + 4.0F);
                }
                else if(InFModifierWrapper.isDeathworm(target)) {
                    event.setDamageModifier(event.getDamageModifier() + 4.0F);
                }
            }
            else if(InFModifierWrapper.isFireDragon(target) && InFModifierWrapper.isIcedWeapon(item)) {
                event.setDamageModifier(event.getDamageModifier() + 8.0F);
            }
            else if(InFModifierWrapper.isIceDragon(target) && InFModifierWrapper.isFlamedWeapon(item)) {
                event.setDamageModifier(event.getDamageModifier() + 8.0F);
            }
        }
    }

    /**
     * Handle Rune Piercing Capabilities properly
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void modifyAttackDamagePost(RLCombatModifyDamageEvent.Post event) {
        EntityPlayer player = event.getEntityPlayer();
        Entity target = event.getTarget();
        if(player == null || target == null || event.getStack().isEmpty()) return;

        int pierceLevel = EnchantmentHelper.getEnchantmentLevel(Smc_010.Rune_PiercingCapabilities, event.getStack());
        if(pierceLevel > 0) {
            float piercePercent = pierceLevel/4.0F;
            if(event.getDamageSource() instanceof EntityDamageSourceArmorPiercing) {
                piercePercent += ((EntityDamageSourceArmorPiercing)event.getDamageSource()).getPercentage();
            }
            event.setDamageSource(new EntityDamageSourceArmorPiercing("player", player, Math.min(piercePercent, 1.0F)));
        }
    }

    /**
     * Stop bounty boards from being able to be broken if clientside breakable is set to true
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if(event.getWorld().getBlockState(event.getPos()).getBlock() instanceof BlockBountyBoard) event.setCanceled(true);
    }

    private static final List<DamageSource> nonAttackSources = Arrays.asList(
            DamageSource.FALL,
            DamageSource.OUT_OF_WORLD,
            DamageSource.DROWN,
            DamageSource.HOT_FLOOR,
            DamageSource.IN_FIRE,
            DamageSource.ON_FIRE,
            DamageSource.LAVA,
            DamageSource.STARVE,
            SDDamageSources.DEHYDRATION,
            SDDamageSources.HYPERTHERMIA,
            SDDamageSources.HYPOTHERMIA,
            SDDamageSources.PARASITES);

    /**
     * Reimplement Reskillable's UnderShirt trait to work with FirstAid
     */
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onFirstAidLivingDamageHigh(FirstAidLivingDamageEvent event) {
        if(event.getEntityPlayer()==null || event.getEntityPlayer().world.isRemote) return;
        if(event.getUndistributedDamage() > 1000) return; //Don't protect against attacks meant to instakill
        if(nonAttackSources.contains(event.getSource())) return;//Don't protect against certain non-attack damage sources

        List<AbstractDamageablePart> parts = new ArrayList<>();
        for(AbstractDamageablePart part : event.getAfterDamage()) {//Check if they are going to die first before bothering with trait check
            if(part.canCauseDeath && part.currentHealth <= 0) {
                parts.add(part);
            }
        }

        if(!parts.isEmpty() && PlayerDataHandler.get(event.getEntityPlayer()).getSkillInfo(
                ReskillableRegistries.SKILLS.getValue(new ResourceLocation(LibMisc.MOD_ID, "defense"))).isUnlocked(
                        ReskillableRegistries.UNLOCKABLES.getValue(new ResourceLocation(LibMisc.MOD_ID, "undershirt")))) {
            if(event.getEntityPlayer().getEntityData().getInteger("skillable:UndershirtCD") <= 0) {
                boolean saved = false;
                for(AbstractDamageablePart part : parts) {//Iterate parts
                    if(event.getBeforeDamage().getFromEnum(part.part).currentHealth >= 4.0) {//Only proc undershirt if the part had atleast 2 hearts
                        part.heal(1.0F, null, false);
                        if(event.getEntityPlayer() instanceof EntityPlayerMP) FirstAid.NETWORKING.sendTo(new MessageUpdatePart(part), (EntityPlayerMP) event.getEntityPlayer());
                        saved = true;
                    }
                }
                if(saved) {
                    event.getEntityPlayer().getEntityData().setInteger("skillable:UndershirtCD", 1200);
                    event.getEntityPlayer().world.playSound(null, event.getEntityPlayer().posX, event.getEntityPlayer().posY, event.getEntityPlayer().posZ, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 0.8F, (event.getEntityPlayer().world.rand.nextFloat()-event.getEntityPlayer().world.rand.nextFloat())*0.1F+0.8F);
                }
            }
        }
    }

    /**
     * Reimplement BountifulBaubles Broken Heart Trinket using FirstAid compat
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onFirstAidLivingDamageLow(FirstAidLivingDamageEvent event) {
        if(event.getEntityPlayer()==null || event.getEntityPlayer().world.isRemote) return;
        if(event.getUndistributedDamage() > 1000) return; //Don't protect against attacks meant to instakill
        if(baubles.api.BaublesApi.isBaubleEquipped(event.getEntityPlayer(), ModItems.trinketBrokenHeart)==-1) return;

        EntityPlayer player = event.getEntityPlayer();
        boolean failed = false;
        List<AbstractDamageablePart> parts = new ArrayList<>();
        for(AbstractDamageablePart part : event.getAfterDamage()) {
            if(part.canCauseDeath && part.currentHealth <= 0) {
                if(part.getMaxHealth() >= 4) parts.add(part);
                else failed = true;
            }
        }

        if(!failed && !parts.isEmpty()) {
            IAttributeInstance maxHealth = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            AttributeModifier modifier = maxHealth.getModifier(MODIFIER_UUID);
            double prevMaxHealthDamage = 0.0;
            if(modifier != null) {
                prevMaxHealthDamage = modifier.getAmount();
            }

            double curMaxHealth = maxHealth.getBaseValue();
            for(AttributeModifier mod : maxHealth.getModifiersByOperation(0)) {
                curMaxHealth += mod.getAmount();
            }
            double originalMaxHealth = curMaxHealth - prevMaxHealthDamage;

            double healthToRemove = (originalMaxHealth*0.3D) + (double)(parts.size()*2) + event.getUndistributedDamage();//Remove 30% of original health, not current, plus undistributed and 1 heart per part destroyed

            if(healthToRemove > curMaxHealth-2) return;//Let them die if current max is too low

            for(AbstractDamageablePart part : parts) {
                part.heal(1.0F, null, false);
                if(event.getEntityPlayer() instanceof EntityPlayerMP) FirstAid.NETWORKING.sendTo(new MessageUpdatePart(part), (EntityPlayerMP)event.getEntityPlayer());
            }

            if(modifier != null) {
                maxHealth.removeModifier(modifier);
            }

            modifier = new AttributeModifier(MODIFIER_UUID, "Broken Heart MaxHP drain", prevMaxHealthDamage - healthToRemove, 0);
            maxHealth.applyModifier(modifier);

            player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_IRONGOLEM_HURT, SoundCategory.PLAYERS, 1.2F, (player.world.rand.nextFloat()-player.world.rand.nextFloat())*0.1F+0.8F);
        }
    }

    /**
     * Reimplement BountifulBaubles fire resistance trinkets to actually work properly
     */
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onLivingHurt(LivingHurtEvent event) {
        if(event.getEntityLiving() instanceof EntityPlayer &&
                event.getSource().isFireDamage() &&
                !event.getEntityLiving().world.isRemote &&
                !event.getSource().equals(DamageSource.LAVA)) {
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();

            float damageMulti = 1.0F;

            IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
            List<UUID> found = new ArrayList<>();

            for(int i = -2; i < baubles.getSlots(); ++i) {
                ItemStack stack = i >= 0 ? baubles.getStackInSlot(i) : (i == -2 ? player.getHeldItemMainhand() : player.getHeldItemOffhand());
                if(stack.getItem() instanceof IFireResistance
                        && !found.contains(((IFireResistance)stack.getItem()).getFireResistID())
                        && (i >= 0 || stack.getItem() instanceof ItemShieldObsidian)) {
                    IFireResistance fireResist = (IFireResistance)stack.getItem();
                    found.add(fireResist.getFireResistID());
                    damageMulti *= Math.max(1.0 - fireResist.getResistance(), 0.0);
                }
            }
            event.setAmount(event.getAmount() * damageMulti);

            if(player.isBurning() && damageMulti != 1.0F) player.extinguish();
        }
    }
}