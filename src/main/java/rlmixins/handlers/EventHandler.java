package rlmixins.handlers;

import bettercombat.mod.event.RLCombatCriticalHitEvent;
import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.Shultrea.Rin.Enchantments_Sector.Smc_030;
import com.Shultrea.Rin.Enchantments_Sector.Smc_040;
import ejektaflex.bountiful.block.BlockBountyBoard;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.RLMixins;

@Mod.EventBusSubscriber(modid = RLMixins.MODID)
public class EventHandler {

    /**
     * Reimplement Curse of Possesion and Curse of Decay handlers in a non-laggy and buggy way
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
            if(thrower != null && !thrower.isCreative() && thrower.isEntityAlive()) {//Check for is alive, otherwise it can add it to a dead players inventory on death, voiding it
                if(thrower.addItemStackToInventory(stack)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    /**
     * Handle Critical Strike enchantment in a non-broken and offhand-sensitive way
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onCritical(RLCombatCriticalHitEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if(player == null) return;
        ItemStack stack = event.getOffhand() ? player.getHeldItemOffhand() : player.getHeldItemMainhand();
        //Only trigger on an actual crit, not an attempted crit
        if(!stack.isEmpty() && (event.getResult() == Event.Result.ALLOW || (event.isVanillaCritical() && event.getResult() == Event.Result.DEFAULT))) {
            int level = EnchantmentHelper.getEnchantmentLevel(Smc_030.CriticalStrike, stack);
            if(level <= 0) return;

            if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
            int counter = stack.getTagCompound().getInteger("failedCritCount");
            int maxReduction = level * 50;

            if(player.world.rand.nextInt(1000 - maxReduction) >= 32 * (counter + 1)) stack.getTagCompound().setInteger("failedCritCount", counter + 1);
            else {
                stack.getTagCompound().setInteger("failedCritCount", 0);
                float crit = 0.4F + (float)level * 0.4F + player.world.rand.nextFloat() * 0.5F;

                player.world.playSound(null, player.posX, player.posY, player.posZ, ModRegistry.CRITICAL_STRIKE, SoundCategory.PLAYERS, 0.8F, 1.0F /(player.world.rand.nextFloat() * 0.4F + 1.2F)* 1.6F);

                event.setDamageModifier(event.getDamageModifier() + crit);
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
        if(InFModifierHandler.isModifierClass(item)) {
            EnumCreatureAttribute attribute = target instanceof EntityLivingBase ? ((EntityLivingBase)target).getCreatureAttribute() : EnumCreatureAttribute.UNDEFINED;
            if(attribute == EnumCreatureAttribute.UNDEAD && InFModifierHandler.isSilverWeapon(item)) {
                event.setDamageModifier(event.getDamageModifier() + 2.0F);
            }
            else if(InFModifierHandler.isMyrmexWeapon(item)) {
                if(attribute != EnumCreatureAttribute.ARTHROPOD) {
                    event.setDamageModifier(event.getDamageModifier() + 4.0F);
                }
                else if(InFModifierHandler.isDeathworm(target)) {
                    event.setDamageModifier(event.getDamageModifier() + 4.0F);
                }
            }
            else if(InFModifierHandler.isFireDragon(target) && InFModifierHandler.isIcedWeapon(item)) {
                event.setDamageModifier(event.getDamageModifier() + 8.0F);
            }
            else if(InFModifierHandler.isIceDragon(target) && InFModifierHandler.isFlamedWeapon(item)) {
                event.setDamageModifier(event.getDamageModifier() + 8.0F);
            }
        }
    }

    /**
     * Stop bounty boards from being able to be broken if clientside breakable is set to true
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if(event.getWorld().getBlockState(event.getPos()).getBlock() instanceof BlockBountyBoard) event.setCanceled(true);
    }

/*
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onFirstAidLivingDamage(FirstAidLivingDamageEvent event) {
        if(event.getEntityPlayer()==null || event.isCanceled() || event.getEntityPlayer().world.isRemote) return;
        if(baubles.api.BaublesApi.isBaubleEquipped(event.getEntityPlayer(), ModItems.trinketBrokenHeart)==-1) return;

        boolean saved = false;
        for(AbstractDamageablePart part : event.getAfterDamage()) {//Iterate parts
            if(part.canCauseDeath && part.currentHealth <= 0) {//Only care about crucial parts, and only if it would kill the player
                if((int)(part.getMaxHealth() - (Math.max(2, event.getUndistributedDamage()))) < 2) return;//Let the player die if they don't have enough heart containers to save them

                part.setMaxHealth((int)(part.getMaxHealth() - Math.max(2, event.getUndistributedDamage())));//Reduce max health by atleast 1, plus undistributed (undistributed doesn't really work properly)
                part.heal(2, null, false);//Heal the dead part to a single heart

                if(event.getEntityPlayer() instanceof EntityPlayerMP) FirstAid.NETWORKING.sendTo(new MessageUpdatePart(part), (EntityPlayerMP)event.getEntityPlayer());
                saved = true;
            }
        }
        if(saved) {
            event.getEntityPlayer().world.playSound(null, event.getEntityPlayer().posX, event.getEntityPlayer().posY, event.getEntityPlayer().posZ,
                    SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 0.7F, (event.getEntityPlayer().world.rand.nextFloat()-event.getEntityPlayer().world.rand.nextFloat())*0.1F+0.8F);
        }
    }

    @SubscribeEvent
    public static void onPlayerWake(PlayerWakeUpEvent event) {
        if(event.getEntityPlayer()==null || event.getEntityPlayer().world.isRemote || event.getEntityPlayer() instanceof FakePlayer) return;
        AbstractPlayerDamageModel damageModel = Objects.requireNonNull(event.getEntityPlayer().getCapability(CapabilityExtendedHealthSystem.INSTANCE, null));

        for(AbstractDamageablePart part : damageModel) {
            if(part.canCauseDeath && part.getMaxHealth()<part.initialMaxHealth) {
                part.setMaxHealth(part.initialMaxHealth);
                if(event.getEntityPlayer() instanceof EntityPlayerMP) FirstAid.NETWORKING.sendTo(new MessageUpdatePart(part), (EntityPlayerMP)event.getEntityPlayer());
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onFirstAidLivingDamage(FirstAidLivingDamageEvent event) {
        if (event.getEntityPlayer() == null || event.isCanceled() || event.getEntityPlayer().world.isRemote) return;
        if (baubles.api.BaublesApi.isBaubleEquipped(event.getEntityPlayer(), ModItems.trinketBrokenHeart) == -1) return;

        for(AbstractDamageablePart part : event.getAfterDamage()) {//Iterate parts
            if(part.canCauseDeath && part.currentHealth <= 0) {//Only care about crucial parts, and only if it would kill the player
                if((int)(part.getMaxHealth() - (Math.max(2, event.getUndistributedDamage()))) < 2) return;//Let the player die if they don't have enough heart containers to save them

                part.setMaxHealth((int)(part.getMaxHealth() - Math.max(2, event.getUndistributedDamage())));//Reduce max health by atleast 1, plus undistributed (undistributed doesn't really work properly)
                part.heal(2, null, false);//Heal the dead part to a single heart

                if(event.getEntityPlayer() instanceof EntityPlayerMP) FirstAid.NETWORKING.sendTo(new MessageUpdatePart(part), (EntityPlayerMP)event.getEntityPlayer());
                saved = true;
            }
        }
    }
     */
}