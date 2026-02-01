package dev.hytalemodding.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

/**
 * An abstract player command example.
 * AbstractPlayerCommand run on the world thread, which means they can safely access the Store and Refs.
 */
public class AbsPlayerCommand extends AbstractPlayerCommand {

    public AbsPlayerCommand() {
        super("absplayercmd", "An abstract player command example");
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());
        UUIDComponent component = store.getComponent(ref, UUIDComponent.getComponentType());
        TransformComponent transform = store.getComponent(ref, TransformComponent.getComponentType());

        assert player != null;
        assert component != null;
        assert transform != null;
        player.sendMessage(Message.raw("Player#getUuid() : " + component.getUuid()));
        player.sendMessage(Message.raw("UUIDComponent : " + component.getUuid()));
        player.sendMessage(Message.raw("Transform : " + transform.getPosition()));
    }
}
