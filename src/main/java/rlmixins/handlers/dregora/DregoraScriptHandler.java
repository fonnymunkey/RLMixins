package rlmixins.handlers.dregora;

import c4.champions.common.affix.AffixRegistry;
import c4.champions.common.affix.IAffix;
import c4.champions.common.capability.CapabilityChampionship;
import c4.champions.common.capability.IChampionship;
import c4.champions.common.rank.Rank;
import c4.champions.common.rank.RankManager;
import c4.champions.common.util.ChampionHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTippedArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.List;
import java.util.Set;

public class DregoraScriptHandler {

    private static final ResourceLocation playerBossReg = new ResourceLocation("playerbosses:player_boss");
    private static final ResourceLocation playerBossBiome = new ResourceLocation("openterraingenerator:overworld_abyssal_rift");
    private static final ResourceLocation dragonBossReg = new ResourceLocation("srparasites:sim_dragone");

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if(entity == null || entity.world.isRemote) return;

        ResourceLocation entityId = EntityList.getKey(entity);
        if(entityId == null || !ForgeConfigHandler.getDregoraArrowAllowedEntities().contains(entityId)) return;

        if(!(entity.getHeldItemMainhand().getItem() instanceof ItemBow)) return;

        NBTTagCompound tag = entity.getEntityData();
        if(tag.getInteger("ArrowCheck") != 1) {
            tag.setInteger("ArrowCheck", 1);

            if(entity.getHeldItemOffhand().getItem() instanceof ItemTippedArrow) {
                tag.setInteger("NoArrowSwitch", 1);
                return;
            }

            if(entity.world.rand.nextFloat() <= ForgeConfigHandler.server.dregoraTippedArrowReplacementChance) {
                List<PotionType> typeList = ForgeConfigHandler.getDregoraArrowAllowedPotionTypes();
                if(typeList.isEmpty()) return;

                tag.setInteger("ArrowEntity", 1);
                ItemStack arrow = PotionUtils.addPotionToItemStack(
                        new ItemStack(Items.TIPPED_ARROW, 1),
                        typeList.get(entity.world.rand.nextInt(typeList.size())));
                entity.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, arrow);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if(entity == null || entity.world.isRemote || entity.dimension != 0) return;

        ResourceLocation entityid = EntityList.getKey(entity);
        if(!playerBossReg.equals(entityid)) return;

        if(entity.getCustomNameTag().contains("Shivaxi")) {
            Biome biome = entity.world.getBiome(entity.getPosition());
            if(playerBossBiome.equals(biome.getRegistryName())) {
                entity.world.createExplosion(entity, entity.posX, entity.posY, entity.posZ, 4, false);

                Entity regEnt = EntityList.createEntityByIDFromName(dragonBossReg, entity.world);
                if(!(regEnt instanceof EntityLiving)) return;
                EntityLiving toSpawn = (EntityLiving)regEnt;

                NBTTagCompound comp = toSpawn.writeToNBT(new NBTTagCompound());
                comp.setString("DeathLootTable", "dregora:entities/playerbosses/abyssal_tower_shivaxi");
                comp.setBoolean("parasitedespawn", false);
                toSpawn.readEntityFromNBT(comp);

                toSpawn.setPosition(entity.posX, entity.posY, entity.posZ);
                if(ChampionHelper.isValidChampion(toSpawn)) {
                    IChampionship chp = CapabilityChampionship.getChampionship(toSpawn);
                    if(chp != null && chp.getRank() == null) {
                        Rank rank = RankManager.getRankForTier(1);
                        chp.setRank(rank);
                        Set<String> affixes = ChampionHelper.generateAffixes(rank, toSpawn);
                        chp.setAffixes(affixes);
                        chp.setName(TextFormatting.DARK_RED+"☢ "+TextFormatting.DARK_GREEN+TextFormatting.BOLD+"Blighted Shivaxi"+TextFormatting.RESET+TextFormatting.DARK_RED+" ☢");
                        chp.getRank().applyGrowth(toSpawn);

                        for(String s : chp.getAffixes()) {
                            IAffix affix = AffixRegistry.getAffix(s);
                            if(affix != null) {
                                affix.onInitialSpawn(toSpawn, chp);
                            }
                        }
                    }
                }
                toSpawn.setCustomNameTag(TextFormatting.DARK_RED+"☢ "+TextFormatting.DARK_GREEN+TextFormatting.BOLD+"Blighted Shivaxi"+TextFormatting.RESET+TextFormatting.DARK_RED+" ☢");
                toSpawn.enablePersistence();
                toSpawn.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("LoweredHealth", ForgeConfigHandler.server.dregoraScriptDragonHealth, 1));
                entity.world.spawnEntity(toSpawn);
            }
        }
    }
}