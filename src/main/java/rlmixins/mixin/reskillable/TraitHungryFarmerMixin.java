package rlmixins.mixin.reskillable;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import codersafterdark.reskillable.base.LevelLockHandler;
import codersafterdark.reskillable.skill.farming.TraitHungryFarmer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.Arrays;

@Mixin(TraitHungryFarmer.class)
public abstract class TraitHungryFarmerMixin extends Unlockable {
    public TraitHungryFarmerMixin(ResourceLocation name, int x, int y, ResourceLocation skillName, int cost, String... defaultRequirements) {
        super(name, x, y, skillName, cost, defaultRequirements);
    }

    /**
     * @author Nischhelm
     * @reason fully reworked
     */
    @Overwrite(remap = false)
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (player == null || player.isCreative() || player.isSpectator()) return;
        PlayerData data = PlayerDataHandler.get(player);
        if (data == null || !data.getSkillInfo(this.getParentSkill()).isUnlocked(this)) return;

        //Eat only if there's more than half a hunger haunch missing
        if (player.getFoodStats().getFoodLevel() >= 18) return;

        //Choose the first item in player inventory (not ContainerPlayer!) that is edible
        ItemStack chosenStack = ItemStack.EMPTY;
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);

            if (stack.isEmpty()) continue;
            if (!(stack.getItem() instanceof ItemFood)) continue;
            if (!LevelLockHandler.canPlayerUseItem(player, stack)) continue;
            ResourceLocation loc = stack.getItem().getRegistryName();
            if (loc != null && Arrays.asList(ForgeConfigHandler.server.hungryFarmerBlacklist).contains(loc.toString())) continue;

            chosenStack = stack;
            break;
        }

        if (!chosenStack.isEmpty()) {
            ItemStack chosenStackCopy = chosenStack.copy();
            ItemStack eatenStack = chosenStack.getItem().onItemUseFinish(chosenStack, player.getEntityWorld(), player);

            //Fire Forge LivingEntityUseItemEvent.Finish for thirst and other side effects
            if (ForgeConfigHandler.server.hungryFarmerFiresForgeEvent)
                ForgeEventFactory.onItemUseFinish(player, chosenStackCopy, 1, eatenStack);
        }
    }
}


