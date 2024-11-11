package rlmixins.mixin.bloodmoon;

import lumien.bloodmoon.config.BloodmoonConfig;
import lumien.bloodmoon.server.BloodmoonSpawner;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;

@Mixin(BloodmoonSpawner.class)
public abstract class BloodmoonSpawnerMixin {
	
	@Shadow(remap = false)
	@Final
	private Set<ChunkPos> eligibleChunksForSpawning;
	
	@Shadow(remap = false)
	@Final
	private static int MOB_COUNT_DIV;
	
	@Shadow(remap = false)
	protected static BlockPos getRandomChunkPosition(World worldIn, int x, int z) {
		return null;
	}
	
	@Shadow(remap = false)
	public static boolean canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType spawnPlacementTypeIn, World worldIn, BlockPos pos) {
		return false;
	}
	
	/**
	 * @author fonnymunkey
	 * @reason improve spawning performance
	 */
	@Overwrite(remap = false)
	public int findChunksForSpawning(WorldServer worldServerIn, boolean spawnHostileMobs, boolean spawnPeacefulMobs, boolean spawnOnSetTickRate) {
		if(!spawnHostileMobs && !spawnPeacefulMobs) {
			return 0;
		}
		else {
			this.eligibleChunksForSpawning.clear();
			int i = 0;
			
			for(EntityPlayer entityplayer : worldServerIn.playerEntities) {
				if(!entityplayer.isSpectator()) {
					int j = MathHelper.floor(entityplayer.posX / 16.0D);
					int k = MathHelper.floor(entityplayer.posZ / 16.0D);
					int l = 8;
					
					for(int i1 = -l; i1 <= l; ++i1) {
						for(int j1 = -l; j1 <= l; ++j1) {
							boolean flag = i1 == -l || i1 == l || j1 == -l || j1 == l;
							ChunkPos chunkpos = new ChunkPos(i1 + j, j1 + k);
							
							if(!this.eligibleChunksForSpawning.contains(chunkpos)) {
								++i;
								
								if(!flag && worldServerIn.getWorldBorder().contains(chunkpos)) {
									PlayerChunkMapEntry playermanager$playerinstance = worldServerIn.getPlayerChunkMap().getEntry(chunkpos.x, chunkpos.z);
									
									if(playermanager$playerinstance != null && playermanager$playerinstance.isSentToPlayers()) {
										//Don't attempt spawn checks in unloaded chunks otherwise sky checks will cause chunk loading
										if(worldServerIn.getChunkProvider().chunkExists(chunkpos.x, chunkpos.z)) {
											this.eligibleChunksForSpawning.add(chunkpos);
										}
									}
								}
							}
						}
					}
				}
			}
			
			int j4 = 0;
			BlockPos blockpos1 = worldServerIn.getSpawnPoint();
			
			for(EnumCreatureType enumcreaturetype : EnumCreatureType.values()) {
				if((!enumcreaturetype.getPeacefulCreature() || spawnPeacefulMobs) && (enumcreaturetype.getPeacefulCreature() || spawnHostileMobs) && (!enumcreaturetype.getAnimal() || spawnOnSetTickRate)) {
					int k4 = worldServerIn.countEntities(enumcreaturetype, true);
					int spawnLimit = enumcreaturetype.getMaxNumberOfCreature() * i / MOB_COUNT_DIV;
					
					spawnLimit *= BloodmoonConfig.SPAWNING.SPAWN_LIMIT_MULT;
					
					if(k4 <= spawnLimit) {
						java.util.ArrayList<ChunkPos> shuffled = com.google.common.collect.Lists.newArrayList(this.eligibleChunksForSpawning);
						java.util.Collections.shuffle(shuffled);
						BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
						label415:
						
						for(ChunkPos chunkcoordintpair1 : shuffled) {
							BlockPos blockpos = getRandomChunkPosition(worldServerIn, chunkcoordintpair1.x, chunkcoordintpair1.z);
							int k1 = blockpos.getX();
							int l1 = blockpos.getY();
							int i2 = blockpos.getZ();
							IBlockState iblockstate = worldServerIn.getBlockState(blockpos);
							
							if(!iblockstate.isNormalCube()) {
								int j2 = 0;
								
								for(int k2 = 0; k2 < 3; ++k2) {
									int l2 = k1;
									int i3 = l1;
									int j3 = i2;
									int k3 = 6;
									Biome.SpawnListEntry biomegenbase$spawnlistentry = null;
									IEntityLivingData ientitylivingdata = null;
									int l3 = MathHelper.ceil(Math.random() * 4.0D);
									
									for(int i4 = 0; i4 < l3; ++i4) {
										l2 += worldServerIn.rand.nextInt(k3) - worldServerIn.rand.nextInt(k3);
										i3 += worldServerIn.rand.nextInt(1) - worldServerIn.rand.nextInt(1);
										j3 += worldServerIn.rand.nextInt(k3) - worldServerIn.rand.nextInt(k3);
										blockpos$mutableblockpos.setPos(l2, i3, j3);
										float f = (float) l2 + 0.5F;
										float f1 = (float) j3 + 0.5F;
										
										if(blockpos1.distanceSq((double)f, (double)i3, (double)f1) >= (BloodmoonConfig.SPAWNING.SPAWN_DISTANCE * BloodmoonConfig.SPAWNING.SPAWN_DISTANCE) && !worldServerIn.isAnyPlayerWithinRangeAt((double) f, (double) i3, (double) f1, BloodmoonConfig.SPAWNING.SPAWN_RANGE) && worldServerIn.canBlockSeeSky(blockpos$mutableblockpos)) {
											if(biomegenbase$spawnlistentry == null) {
												biomegenbase$spawnlistentry = worldServerIn.getSpawnListEntryForTypeAt(enumcreaturetype, blockpos$mutableblockpos);
												
												if(biomegenbase$spawnlistentry == null || !BloodmoonConfig.canSpawn(biomegenbase$spawnlistentry.entityClass)) {
													biomegenbase$spawnlistentry = null;
													break;
												}
											}
											
											if(worldServerIn.canCreatureTypeSpawnHere(enumcreaturetype, biomegenbase$spawnlistentry, blockpos$mutableblockpos) && canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.getPlacementForEntity(biomegenbase$spawnlistentry.entityClass), worldServerIn, blockpos$mutableblockpos)) {
												EntityLiving entityliving;
												
												try {
													entityliving = biomegenbase$spawnlistentry.newInstance(worldServerIn);
												}
												catch (Exception exception) {
													exception.printStackTrace();
													return j4;
												}
												
												entityliving.setLocationAndAngles((double) f, (double) i3, (double) f1, worldServerIn.rand.nextFloat() * 360.0F, 0.0F);
												
												net.minecraftforge.fml.common.eventhandler.Event.Result canSpawn = net.minecraftforge.event.ForgeEventFactory.canEntitySpawn(entityliving, worldServerIn, f, i3, f1, false);
												if(canSpawn == net.minecraftforge.fml.common.eventhandler.Event.Result.ALLOW || (canSpawn == net.minecraftforge.fml.common.eventhandler.Event.Result.DEFAULT && (entityliving.getCanSpawnHere() && entityliving.isNotColliding()))) {
													if(!net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn(entityliving, worldServerIn, f, l3, f1)) {
														ientitylivingdata = entityliving.onInitialSpawn(worldServerIn.getDifficultyForLocation(new BlockPos(entityliving)), ientitylivingdata);
													}
													
													if(entityliving.isNotColliding()) {
														++j2;
														entityliving.getEntityData().setBoolean("bloodmoonSpawned", true);
														worldServerIn.spawnEntity(entityliving);
													}
													else {
														entityliving.setDead();
													}
													
													if(i2 >= net.minecraftforge.event.ForgeEventFactory.getMaxSpawnPackSize(entityliving)) {
														continue label415;
													}
												}
												
												j4 += j2;
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
			return j4;
		}
	}
}