package rlmixins.entity.flare;
import java.util.List;
import java.util.UUID;

import cursedflames.bountifulbaubles.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rlmixins.RLMixins;

//TODO: Clean this class up
public class EntityFlareNonAlbedo extends EntityArrow {
    protected boolean inGround;
    protected BlockPos tilePos;
    protected Block inTile;
    protected double tileX;
    protected double tileY;
    protected double tileZ;
    protected int xTile;
    protected int yTile;
    protected int zTile;
    protected int ticksInGround = 0;
//	protected String throwerName;
    private boolean isPlayingSound = false;

    public EntityFlareNonAlbedo(World world) {
        super(world);
    }

    public EntityFlareNonAlbedo(World world, double x, double y, double z) {
        super(world);
        this.setPosition(x, y, z);
    }

    public EntityFlareNonAlbedo(World world, EntityLivingBase thrower) {
        this(world, thrower.posX, thrower.posY+(double) thrower.getEyeHeight()-0.10000000149011612D,
                thrower.posZ);
        this.shootingEntity = thrower;
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        // copy pasted from EntityArrow
        double f = MathHelper.sqrt(x*x+y*y+z*z);
        x /= f;
        y /= f;
        z /= f;
        x += this.rand.nextGaussian()*0.007499999832361937D*(double) inaccuracy;
        y += this.rand.nextGaussian()*0.007499999832361937D*(double) inaccuracy;
        z += this.rand.nextGaussian()*0.007499999832361937D*(double) inaccuracy;
        x *= (double) velocity;
        y *= (double) velocity;
        z *= (double) velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        double f1 = MathHelper.sqrt(x*x+z*z);
        this.rotationYaw = (float) (MathHelper.atan2(x, z)*(180D/Math.PI));
        this.rotationPitch = (float) (MathHelper.atan2(y, f1)*(180D/Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
//		BountifulBaubles.logger.info("flare shot");
    }

    /**
     * Sets throwable heading based on an entity that's throwing it
     */
    public void shoot(EntityLivingBase entityThrower, float rotationPitchIn, float rotationYawIn,
                      float pitchOffset, float velocity, float inaccuracy) {
        this.shootingEntity = entityThrower;
        float f = -MathHelper.sin(rotationYawIn*0.017453292F)
                *MathHelper.cos(rotationPitchIn*0.017453292F);
        float f1 = -MathHelper.sin((rotationPitchIn+pitchOffset)*0.017453292F);
        float f2 = MathHelper.cos(rotationYawIn*0.017453292F)
                *MathHelper.cos(rotationPitchIn*0.017453292F);
        this.shoot((double) f, (double) f1, (double) f2, velocity, inaccuracy);
        this.motionX += entityThrower.motionX;
        this.motionZ += entityThrower.motionZ;

        if (!entityThrower.onGround) {
            this.motionY += entityThrower.motionY;
        }
    }

    @Override
    protected void arrowHit(EntityLivingBase entity) {
        if(!(entity instanceof EntityEnderman)) entity.setFire(5);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
//		BountifulBaubles.logger.info("reading from nbt "+compound.toString());
        this.inGround = compound.getBoolean("inGround");
        if (compound.hasKey("tilePos")) {
            NBTTagCompound pos = compound.getCompoundTag("tilePos");
            this.tilePos = Util.blockPosFromNBT(pos);
        }
        if (compound.hasKey("inTile", 8)) {
            this.inTile = Block.getBlockFromName(compound.getString("inTile"));
        }
        if (compound.hasUniqueId("thrower")) {
            UUID thrower = compound.getUniqueId("thrower");
            this.shootingEntity = this.world.getPlayerEntityByUUID(thrower);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setBoolean("inGround", this.inGround);
        if (this.tilePos!=null) {
            NBTTagCompound pos = Util.blockPosToNBT(this.tilePos);
            compound.setTag("tilePos", pos);
        }
        ResourceLocation resourcelocation = Block.REGISTRY.getNameForObject(this.inTile);
        compound.setString("inTile", resourcelocation==null ? "" : resourcelocation.toString());
        if (this.shootingEntity!=null) {
            compound.setUniqueId("thrower", this.shootingEntity.getUniqueID());
        }
    }

//	@Override
//	public void writeSpawnData(ByteBuf buffer) {
//		NBTTagCompound tag = new NBTTagCompound();
//		tag.setDouble("x", this.posX);
//		tag.setDouble("y", this.posY);
//		tag.setDouble("z", this.posZ);
//		tag.setDouble("mx", this.motionX);
//		tag.setDouble("my", this.motionY);
//		tag.setDouble("mz", this.motionZ);
//		writeEntityToNBT(tag);
//		ByteBufUtils.writeTag(buffer, tag);
//	}
//
//	@Override
//	public void readSpawnData(ByteBuf additionalData) {
//		NBTTagCompound tag = ByteBufUtils.readTag(additionalData);
//		readEntityFromNBT(tag);
//		if (tag.hasKey("x")&&tag.hasKey("y")&&tag.hasKey("z")&&tag.hasKey("mx")&&tag.hasKey("my")
//				&&tag.hasKey("mz")) {
//			this.posX = tag.getDouble("x");
//			this.posY = tag.getDouble("y");
//			this.posZ = tag.getDouble("z");
//			this.motionX = tag.getDouble("mx");
//			this.motionY = tag.getDouble("my");
//			this.motionZ = tag.getDouble("mz");
//		}
//	}

    protected float getGravityVelocity() {
        return 0.03F;
    }

    @Override
    public void onUpdate() {
        // Copied from EntityThrowable
        super.onEntityUpdate();

        if (this.prevRotationPitch==0.0F&&this.prevRotationYaw==0.0F) {
            float f = MathHelper.sqrt(this.motionX*this.motionX+this.motionZ*this.motionZ);
            this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ)
                    *(180D/Math.PI));
            this.rotationPitch = (float) (MathHelper.atan2(this.motionY, (double) f)
                    *(180D/Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
        }

        int particleRate = this.inGround ? 10 : 2;

        if(this.world.isRemote) {
            Vec3d vel = new Vec3d(0, 0, -1).rotatePitch((float) (this.rotationPitch*(Math.PI/180D)))
                    .rotateYaw((float) (this.rotationYaw*(Math.PI/180D)));
            if((this.world.getTotalWorldTime()%particleRate)==0) {
                this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX+vel.x*0.3,
                        this.posY+vel.y*0.3, this.posZ+vel.z*0.3, vel.x*0.055, vel.y*0.055,
                        vel.z*0.055);
            }

            if(!this.isPlayingSound) {
                this.isPlayingSound = true;
                RLMixins.PROXY.playSoundFlare(this);
            }
        }

        // not sure what this is for
//		if (this.throwableShake>0) {
//			--this.throwableShake;
//		}

//		if (this.ticksInGround==10) {
//			BountifulBaubles.logger.info(this.posX+" "+this.posY+" "+this.posZ);
//		}

        // disappear after 5 minutes
        if (this.ticksExisted>6000) {
            this.setDead();
        }

//		if (this.ticksExisted%40==0) {
//			BountifulBaubles.logger.info("entity tick");
//		}

        if (this.inGround&&tilePos!=null) {
            if (this.world.getBlockState(tilePos).getBlock()==this.inTile) {
                ++this.ticksInGround;
//
//				if (this.ticksInGround==1200) {
//					this.setDead();
//				}

//				if (this.posX!=this.tileX||this.posY!=this.tileY||this.posZ!=this.tileZ) {
//					this.posX = this.tileX;
//					this.posY = this.tileY;
//					this.posZ = this.tileZ;
//				}

                return;
            }

            this.inGround = false;
            this.motionX *= (double) (this.rand.nextFloat()*0.2F);
            this.motionY *= (double) (this.rand.nextFloat()*0.2F);
            this.motionZ *= (double) (this.rand.nextFloat()*0.2F);
//			this.ticksInGround = 0;
//			this.ticksInAir = 0;
        } else {
//			++this.ticksInAir;
        }

        Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d vec3d1 = new Vec3d(this.posX+this.motionX, this.posY+this.motionY,
                this.posZ+this.motionZ);
        RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, vec3d1, false, true,
                false);
        vec3d = new Vec3d(this.posX, this.posY, this.posZ);
        vec3d1 = new Vec3d(this.posX+this.motionX, this.posY+this.motionY, this.posZ+this.motionZ);

        if (raytraceresult!=null) {
            vec3d1 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y,
                    raytraceresult.hitVec.z);
        }

        Entity entity = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this,
                this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ)
                        .grow(1.0D));
        double d0 = 0.0D;

        for (Entity entity1 : list) {
            if (entity1.canBeCollidedWith()) {
                if (entity1!=this.shootingEntity||this.ticksExisted>=5) {
                    AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox()
                            .grow(0.30000001192092896D);
                    RayTraceResult raytraceresult1 = axisalignedbb.calculateIntercept(vec3d,
                            vec3d1);

                    if (raytraceresult1!=null) {
                        double d1 = vec3d.squareDistanceTo(raytraceresult1.hitVec);

                        if (d1<d0||d0==0.0D) {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }
        }

        if (entity!=null) {
            raytraceresult = new RayTraceResult(entity);
        }

        if (raytraceresult!=null) {
            if (raytraceresult.typeOfHit==RayTraceResult.Type.BLOCK&&this.world
                    .getBlockState(raytraceresult.getBlockPos()).getBlock()==Blocks.PORTAL) {
                this.setPortal(raytraceresult.getBlockPos());
            } else if (!net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this,
                    raytraceresult)) {
                this.onHit(raytraceresult);
            }
        }

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        float f = MathHelper.sqrt(this.motionX*this.motionX+this.motionZ*this.motionZ);
        this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ)*(180D/Math.PI));
        this.rotationPitch = (float) (MathHelper.atan2(this.motionY, (double) f)*(180D/Math.PI));
//		while ((this.rotationPitch = this.prevRotationPitch)<-180.0F) {
//			this.prevRotationPitch -= 360.0F;
//		}
//
//		while (this.rotationPitch-this.prevRotationPitch>=180.0F) {
//			this.prevRotationPitch += 360.0F;
//		}
//
//		while (this.rotationYaw-this.prevRotationYaw<-180.0F) {
//			this.prevRotationYaw -= 360.0F;
//		}
//
//		while (this.rotationYaw-this.prevRotationYaw>=180.0F) {
//			this.prevRotationYaw += 360.0F;
//		}

//		this.rotationPitch = this.prevRotationPitch
//				+(this.rotationPitch-this.prevRotationPitch)*0.2F;
//		this.rotationYaw = this.prevRotationYaw+(this.rotationYaw-this.prevRotationYaw)*0.2F;
        double velMultiplier = 0.995;
        float gravity = this.getGravityVelocity();

        if (this.isInWater()) {
            for (int j = 0; j<4; ++j) {
                this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE,
                        this.posX-this.motionX*0.25D, this.posY-this.motionY*0.25D,
                        this.posZ-this.motionZ*0.25D, this.motionX, this.motionY, this.motionZ);
            }

            velMultiplier = 0.8;
        }

        this.motionX *= velMultiplier;
        this.motionY *= velMultiplier;
        this.motionZ *= velMultiplier;

        if(!this.hasNoGravity()) {
            if(this.motionY>0) this.motionY -= (double) gravity;
            else {
                double preY = this.motionY;
                this.motionY -= (double) gravity/8;
                this.motionX *= 0.95;
                this.motionY *= 1.01;
                this.motionZ *= 0.95;

                if(this.motionY - preY > gravity/4) this.motionY = preY - gravity/4;
            }
        }

        this.setPosition(this.posX, this.posY, this.posZ);

        if (this.inGround) {
//			BountifulBaubles.logger.info(this.posX+" "+this.posY+" "+this.posZ);
        }
    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) {
        Entity entity = raytraceResultIn.entityHit;

        if(entity!=null) {
/*
            RLMixins.LOGGER.log(Level.INFO,
                    "onHit remote: " + entity.world.isRemote +
                            " ticksexisted: " + this.ticksExisted +
                            " entity: " + entity.getName() +
                            " shootingEntity: " + (this.shootingEntity==null ? "null" : this.shootingEntity.getName()));
*/
            if(entity.world.isRemote && this.ticksExisted<5) return; //Should stop desync if player is moving forward and shoots themselves

            float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            //int i = MathHelper.ceil((double)f * this.damage);
            int i = MathHelper.ceil((double)f * 0.5D);

            DamageSource damagesource;

            if(this.shootingEntity == null) damagesource = DamageSource.causeArrowDamage(this, this);
            else damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);

            if(entity.attackEntityFrom(damagesource, (float)i))
            {
                if(entity instanceof EntityLivingBase)
                {
                    EntityLivingBase entitylivingbase = (EntityLivingBase)entity;

                    this.arrowHit(entitylivingbase);

                    if(this.shootingEntity != null && entitylivingbase != this.shootingEntity && entitylivingbase instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
                    {
                        ((EntityPlayerMP)this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
                    }
                }

                this.playSound(SoundEvents.ITEM_FLINTANDSTEEL_USE, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            }

            this.motionX *= -0.10000000149011612D;
            this.motionY *= -0.10000000149011612D;
            this.motionZ *= -0.10000000149011612D;
            this.rotationYaw += 180.0F;
            this.prevRotationYaw += 180.0F;
/*
            if (!this.world.isRemote && this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ < 0.0010000000474974513D)
            {
                if (this.pickupStatus == EntityArrow.PickupStatus.ALLOWED)
                {
                    this.entityDropItem(this.getArrowStack(), 0.1F);
                }

                this.setDead();
            }

 */
        }
        else {
            BlockPos blockpos = raytraceResultIn.getBlockPos();
            this.tilePos = blockpos;
//			this.xTile = blockpos.getX();
//			this.yTile = blockpos.getY();
//			this.zTile = blockpos.getZ();
            IBlockState iblockstate = this.world.getBlockState(blockpos);
            this.inTile = iblockstate.getBlock();
//            this.inData = this.inTile.getMetaFromState(iblockstate);
            this.motionX = (double) ((float) (raytraceResultIn.hitVec.x-this.posX));
            this.motionY = (double) ((float) (raytraceResultIn.hitVec.y-this.posY));
            this.motionZ = (double) ((float) (raytraceResultIn.hitVec.z-this.posZ));
            float f2 = MathHelper.sqrt(
                    this.motionX*this.motionX+this.motionY*this.motionY+this.motionZ*this.motionZ);
            this.posX -= this.motionX/(double) f2*0.05000000074505806D;
            this.posY -= this.motionY/(double) f2*0.05000000074505806D;
            this.posZ -= this.motionZ/(double) f2*0.05000000074505806D;
//            this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            this.inGround = true;
//            this.arrowShake = 7;
//            this.setIsCritical(false);

            if(iblockstate.getMaterial()!=Material.AIR) {
                this.inTile.onEntityCollision(this.world, blockpos, iblockstate, this);
                if(this.ticksInGround < 120 && this.world.isAirBlock(this.tilePos.offset(raytraceResultIn.sideHit))) this.world.setBlockState(this.tilePos.offset(raytraceResultIn.sideHit), Blocks.FIRE.getDefaultState(), 11);
            }
        }
    }

    @Override
    protected ItemStack getArrowStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public float getBrightness()
    {
        return 1.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }



/*
    @Override
    @SideOnly(Side.CLIENT)
    public Light provideLight() {
        return Light.builder().pos(this).color(0.7F, 0.21F, 0.21F).radius(20).build();
    }

    @Override
    public void gatherLights(GatherLightsEvent event, Entity context) {
//		Light light = Light.builder().pos(this).color(0.7F, 0.21F, 0.21F).radius(20).build();
////		BountifulBaubles.logger.info(light);
//		Light light = new Light((float)this.posX, (float)this.posY, (float)this.posZ, 0.7F, 0.21F, 0.21F, 1F, 20F);
//		BountifulBaubles.logger.info(light);
//		event.add(light);
    }
 */

}