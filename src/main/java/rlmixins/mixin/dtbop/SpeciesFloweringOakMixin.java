package rlmixins.mixin.dtbop;

import biomesoplenty.api.enums.BOPTrees;
import biomesoplenty.common.block.BlockBOPLeaves;
import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.trees.Species;
import dynamictreesbop.trees.species.SpeciesFloweringOak;
import net.minecraftforge.common.BiomeDictionary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SpeciesFloweringOak.class)
public abstract class SpeciesFloweringOakMixin extends Species {

    @Redirect(
            method = "<init>(Lcom/ferreusveritas/dynamictrees/trees/TreeFamily;)V",
            at = @At(value = "INVOKE", target = "Ldynamictreesbop/trees/species/SpeciesFloweringOak;addValidLeavesBlocks([Lcom/ferreusveritas/dynamictrees/api/treedata/ILeavesProperties;)V"),
            remap = false
    )
    public void rlmixins_dynamicTreesBopSpeciesFloweringOak_init(SpeciesFloweringOak instance, ILeavesProperties[] iLeavesProperties) {
        instance.addValidLeavesBlocks(iLeavesProperties);
        setDefaultGrowingParametersFloweringOak(instance);
        instance.setRequiresTileEntity(true);
        treeFamily.addConnectableVanillaLeaves(
                state -> state.getBlock() instanceof BlockBOPLeaves && state.getValue(((BlockBOPLeaves)state.getBlock()).variantProperty) == BOPTrees.FLOWERING
        );
    }

    private void setDefaultGrowingParametersFloweringOak(SpeciesFloweringOak instance) {
        instance.setBasicGrowingParameters(0.3F, 12.0F, this.upProbability, this.lowestBranchHeight, 0.85F);
        instance.setupStandardSeedDropping();
        instance.envFactor(BiomeDictionary.Type.COLD, 0.75F);
        instance.envFactor(BiomeDictionary.Type.HOT, 0.5F);
        instance.envFactor(BiomeDictionary.Type.DRY, 0.5F);
        instance.envFactor(BiomeDictionary.Type.FOREST, 1.05F);
    }
}