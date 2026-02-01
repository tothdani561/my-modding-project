package dev.hytalemodding.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AbsTargetPlayerHealCommand extends AbstractTargetPlayerCommand {
    private final DefaultArg<Float> healthArg;
    private final OptionalArg<String> messageArg;
    private final FlagArg debugArg;

    public AbsTargetPlayerHealCommand() {
        super("healplayer", "Healing a player for an <input> ammount of HP (default: 100)");
        this.healthArg = this.withDefaultArg("health", "Amount to heal player", ArgTypes.FLOAT, (float)100, "Desc of Default: 100");
        this.messageArg = this.withOptionalArg("message", "Message to print while healing", ArgTypes.STRING);
        this.debugArg = this.withFlagArg("debug", "Add debug logs");
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nullable Ref<EntityStore> ref, @Nonnull Ref<EntityStore> ref1, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {

        if (this.debugArg.get(commandContext) == true) {
            commandContext.sendMessage(Message.raw("Debug: Starting heal process..."));
        }

        assert ref != null;
        EntityStatMap stats = store.getComponent(ref, EntityStatMap.getComponentType());
        int healthIdx = DefaultEntityStatTypes.getHealth();

        assert stats != null;
        EntityStatValue health = stats.get(healthIdx);

        assert health != null;
        float missing = health.getMax() - health.get();

        if (this.debugArg.get(commandContext) == true) {
            commandContext.sendMessage(Message.raw("Missing : " + missing + " health"));
            commandContext.sendMessage(Message.raw("Adding : " + healthArg.get(commandContext) + " health to "));
            commandContext.sendMessage(Message.raw(messageArg.get(commandContext)));
            commandContext.sendMessage(Message.raw("Input value : " + healthArg.get(commandContext) + " Default"));
            commandContext.sendMessage(Message.raw("Default Health value : " + healthArg.getDefaultValue()));
        }

        stats.addStatValue(healthIdx, healthArg.get(commandContext));

    }
}
