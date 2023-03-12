package rlmixins.mixin.simpledifficulty;

import com.charles445.simpledifficulty.api.SDItems;
import com.charles445.simpledifficulty.api.item.IItemCanteen;
import com.charles445.simpledifficulty.api.thirst.ThirstEnum;
import com.charles445.simpledifficulty.block.BlockRainCollector;
import com.charles445.simpledifficulty.item.ItemDragonCanteen;
import com.charles445.simpledifficulty.util.SoundUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockRainCollector.class)
public abstract class BlockRainCollectorMixin {

    @Inject(
            method = "onBlockActivated",
            at = @At("RETURN"),
            cancellable = true
    )
    public void rlmixins_simpleDifficultyBlockRainCollector_onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, CallbackInfoReturnable<Boolean> cir) {
        if(!cir.getReturnValue()) {
            int amount = state.getValue(BlockRainCollector.LEVEL);
            ItemStack itemstack = player.getHeldItem(hand);
            if(itemstack.getItem() == SDItems.ironCanteen || itemstack.getItem() instanceof ItemDragonCanteen) {
                if(amount > 0 && !world.isRemote && !player.capabilities.isCreativeMode) {
                    IItemCanteen canteen = (IItemCanteen)itemstack.getItem();
                    ThirstEnum type = canteen.getThirstEnum(itemstack);
                    if(canteen.tryAddDose(itemstack, ThirstEnum.PURIFIED)) {
                        ((BlockRainCollector)(Object)this).setWaterLevel(world, pos, state, amount - 1);
                        SoundUtil.serverPlayBlockSound(world, pos, SoundEvents.ITEM_BUCKET_FILL);
                    }
                }
                cir.setReturnValue(true);
            }
        }
    }
}