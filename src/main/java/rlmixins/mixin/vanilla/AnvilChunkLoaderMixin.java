package rlmixins.mixin.vanilla;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.chunk.storage.RegionFileCache;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Based on Chunk Data Saving patch created by mrgrim and EigenCraft Unofficial Patch https://github.com/mrgrim/MUP/blob/master/src/main/java/org/gr1m/mc/mup/bugfix/mc119971/mixin/MixinAnvilChunkLoader.java
 */
@Mixin(AnvilChunkLoader.class)
public abstract class AnvilChunkLoaderMixin {
    @Shadow private void writeChunkData(ChunkPos pos, NBTTagCompound compound) throws IOException { }

    @Shadow @Final public File chunkSaveLocation;

    @Shadow @Final private static Logger LOGGER;

    @Shadow private boolean flushing;

    @Shadow private final Map<ChunkPos, NBTTagCompound> chunksToSave = new HashMap<>();

    private final Map<ChunkPos, NBTTagCompound> chunksInWrite = new HashMap<>();

    synchronized private void queueChunkToSave(ChunkPos pos, NBTTagCompound data) { chunksToSave.put(pos, data); }

    synchronized private Map.Entry<ChunkPos, NBTTagCompound> fetchChunkToWrite() {
        if(chunksToSave.isEmpty()) return null;

        Set<Map.Entry<ChunkPos, NBTTagCompound>> entrySet = chunksToSave.entrySet();
        Iterator<Map.Entry<ChunkPos, NBTTagCompound>> iter = entrySet.iterator();
        Map.Entry<ChunkPos, NBTTagCompound> entry = iter.next();
        iter.remove();

        chunksInWrite.put(entry.getKey(), entry.getValue());

        return entry;
    }

    synchronized private void retireChunkToWrite(ChunkPos pos, NBTTagCompound data) {
        chunksInWrite.remove(pos);
    }

    synchronized private NBTTagCompound reloadChunkFromSaveQueues(ChunkPos pos) {
        NBTTagCompound data = chunksToSave.get(pos);
        if (data != null) return data;

        return chunksInWrite.get(pos);
    }

    synchronized private boolean chunkExistInSaveQueues(ChunkPos pos) {
        return chunksToSave.containsKey(pos) || chunksInWrite.containsKey(pos);
    }

    @Redirect(
            method = "loadChunk__Async",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;",
                    ordinal = 0, remap = false), remap = false)
    private Object pullChunkToSave(Map lChunksToSave, Object lpos) {
        return this.reloadChunkFromSaveQueues((ChunkPos)lpos);
    }

    @Inject(
            method = "isChunkGeneratedAt",
            at = @At("HEAD"),
            cancellable = true)
    private void overrideIsChunkGeneratedAt(int x, int z, CallbackInfoReturnable<Boolean> ci) {
        ChunkPos chunkpos = new ChunkPos(x, z);
        boolean exists = chunkExistInSaveQueues(chunkpos);
        ci.setReturnValue(exists || RegionFileCache.chunkExists(this.chunkSaveLocation, x, z));
    }

    @Redirect(
            method = "addChunkToPending",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z", remap = false))
    private boolean overrideAddChunkToPending(Set lChunksBeingSaved, Object lPos, ChunkPos pos, NBTTagCompound compound) {
        this.queueChunkToSave(pos, compound);
        return true;
    }

    @Inject(
            method = "writeNextIO",
            at = @At("HEAD"), cancellable = true)
    private void overrideWriteNextIO(CallbackInfoReturnable<Boolean> ci) {
        Map.Entry<ChunkPos, NBTTagCompound> entry = this.fetchChunkToWrite();
        if(entry == null) {
            if(this.flushing) LOGGER.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", new Object[] {this.chunkSaveLocation.getName()});

            ci.setReturnValue(false);
            return;
        }

        ChunkPos chunkpos = entry.getKey();
        NBTTagCompound nbttagcompound = entry.getValue();

        try { this.writeChunkData(chunkpos, nbttagcompound); }
        catch(Exception exception) { LOGGER.error((String)"Failed to save chunk", exception); }

        this.retireChunkToWrite(chunkpos, nbttagcompound);

        ci.setReturnValue(true);
    }
}