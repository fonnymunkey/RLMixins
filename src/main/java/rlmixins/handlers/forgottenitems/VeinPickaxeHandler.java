package rlmixins.handlers.forgottenitems;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tschipp.forgottenitems.items.ItemVeinPickaxe;
import tschipp.forgottenitems.util.FIConfig;

import java.util.ArrayList;

/**
 * Based on VeinPickaxe handling from ForgottenItems
 */
public class VeinPickaxeHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        World world = player.world;
        ItemStack stack = player.getHeldItemMainhand();
        if(!world.isRemote && stack.getItem() instanceof ItemVeinPickaxe && !player.isCreative() && !player.isSneaking()) {
            if(!stack.hasTagCompound() || !stack.getTagCompound().getBoolean("veinCooldown")) {
                BlockPos pos = event.getPos();
                if(!canMineEffectively(player, pos, stack)) return;
                if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
                stack.getTagCompound().setBoolean("veinCooldown", true);

                IBlockState mat = world.getBlockState(pos);
                ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
                ArrayList<BlockPos> tempPositions = new ArrayList<BlockPos>();
                positions.add(pos);

                for(int i = 0; i < FIConfig.veinPickaxeRadius; ++i) {
                    for(int j = 0; j < positions.size(); ++j) {
                        addPositions(j, world, positions, tempPositions, mat);
                    }
                    positions.addAll(tempPositions);
                    tempPositions.clear();
                }
                positions.remove(pos);

                int maxDam = stack.getMaxDamage();
                for(BlockPos position : positions) {
                    if(!stack.isEmpty() && stack.getItemDamage() <= maxDam) {
                        if(canMineEffectively(player, position, stack)) {
                            ((EntityPlayerMP)player).interactionManager.tryHarvestBlock(position);
                        }
                    }
                }

                stack.getTagCompound().setBoolean("veinCooldown", false);
                positions.clear();
                tempPositions.clear();
            }
        }
    }

    private static boolean canMineEffectively(EntityPlayer player, BlockPos pos, ItemStack stack) {
        IBlockState state = player.world.getBlockState(pos);
        Block block = state.getBlock();

        if(block == Blocks.AIR) return false;
        if(player.world.getTileEntity(pos) != null) return false;

        for(String type : stack.getItem().getToolClasses(player.getHeldItemMainhand())) {
            if(block.isToolEffective(type, state) && block.getHarvestLevel(state) <= stack.getItem().getHarvestLevel(player.getHeldItemMainhand(), type, player, state)) {
                return true;
            }
        }
        return false;
    }

    private static void addPositions(int i, World world, ArrayList<BlockPos> positions, ArrayList<BlockPos> tempPositions, IBlockState mat) {
        if(world.getBlockState(((BlockPos)positions.get(i)).north()) == mat
                && !positions.contains(((BlockPos)positions.get(i)).north())
                && !tempPositions.contains(((BlockPos)positions.get(i)).north())) {
            tempPositions.add(((BlockPos)positions.get(i)).north());
        }

        if (world.getBlockState(((BlockPos)positions.get(i)).east()) == mat
                && !positions.contains(((BlockPos)positions.get(i)).east())
                && !tempPositions.contains(((BlockPos)positions.get(i)).east())) {
            tempPositions.add(((BlockPos)positions.get(i)).east());
        }

        if (world.getBlockState(((BlockPos)positions.get(i)).south()) == mat
                && !positions.contains(((BlockPos)positions.get(i)).south())
                && !tempPositions.contains(((BlockPos)positions.get(i)).south())) {
            tempPositions.add(((BlockPos)positions.get(i)).south());
        }

        if (world.getBlockState(((BlockPos)positions.get(i)).west()) == mat
                && !positions.contains(((BlockPos)positions.get(i)).west())
                && !tempPositions.contains(((BlockPos)positions.get(i)).west())) {
            tempPositions.add(((BlockPos)positions.get(i)).west());
        }

        if (world.getBlockState(((BlockPos)positions.get(i)).up()) == mat
                && !positions.contains(((BlockPos)positions.get(i)).up())
                && !tempPositions.contains(((BlockPos)positions.get(i)).up())) {
            tempPositions.add(((BlockPos)positions.get(i)).up());
        }

        if (world.getBlockState(((BlockPos)positions.get(i)).down()) == mat
                && !positions.contains(((BlockPos)positions.get(i)).down())
                && !tempPositions.contains(((BlockPos)positions.get(i)).down())) {
            tempPositions.add(((BlockPos)positions.get(i)).down());
        }
    }
}
