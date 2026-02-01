package dev.hytalemodding.block;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.RefSystem;
import com.hypixel.hytale.math.util.ChunkUtil;
import com.hypixel.hytale.server.core.modules.block.BlockModule;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import dev.hytalemodding.ExamplePlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ExampleInitializer extends RefSystem {

    @Override
    public void onEntityAdded(@Nonnull Ref ref, @Nonnull AddReason addReason, @Nonnull Store store, @Nonnull CommandBuffer commandBuffer) {
        BlockModule.BlockStateInfo info = (BlockModule.BlockStateInfo) commandBuffer.getComponent(ref, BlockModule.BlockStateInfo.getComponentType());
        if (info == null) return;

        ExampleBlock generator = (ExampleBlock) commandBuffer.getComponent(ref, ExamplePlugin.get().getExampleBlockComponentType());
        if (generator != null) {
            int x = ChunkUtil.xFromBlockInColumn(info.getIndex());
            int y = ChunkUtil.yFromBlockInColumn(info.getIndex());
            int z = ChunkUtil.zFromBlockInColumn(info.getIndex());

            WorldChunk worldChunk = (WorldChunk) commandBuffer.getComponent(info.getChunkRef(), WorldChunk.getComponentType());
            if (worldChunk != null) {
                // Schedule the block action to run on the next tick
                // Tells the game that this block should tick, allowing ExampleSystem to process it.
                worldChunk.setTicking(x, y, z, true);
            }
        }
    }

    @Override
    public void onEntityRemove(@Nonnull Ref ref, @Nonnull RemoveReason removeReason, @Nonnull Store store, @Nonnull CommandBuffer commandBuffer) {}

    @Nullable
    @Override
    public Query getQuery() {
        return Query.and(BlockModule.BlockStateInfo.getComponentType(), ExamplePlugin.get().getExampleBlockComponentType());
    }
}
