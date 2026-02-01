package dev.hytalemodding.block;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.hytalemodding.ExamplePlugin;

import javax.annotation.Nullable;

public class ExampleBlock implements Component<ChunkStore> {

    public static final BuilderCodec CODEC;

    public ExampleBlock() {}

    public static ComponentType getComponentType() {
        return ExamplePlugin.get().getExampleBlockComponentType();
    }

    public void runBlockAction(int x, int y, int z, World world) {
        world.execute(() -> {
            world.setBlock(x + 1, y, z, "Rock_Ice");
        });
    }

    @Nullable
    @Override
    public Component<ChunkStore> clone() {
        return new ExampleBlock();
    }

    static {
        CODEC = BuilderCodec.builder(ExampleBlock.class, ExampleBlock::new)
                .build();
    }
}
