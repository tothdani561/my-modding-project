package dev.hytalemodding.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.commands.player.inventory.GiveCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetEntityCommand;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import it.unimi.dsi.fastutil.objects.ObjectList;

import javax.annotation.Nonnull;

/**
 * Example arg types
 * ArgTypes.STRING
 * ArgTypes.INTEGER
 * ArgTypes.BOOLEAN
 * ArgTypes.FLOAT
 * ArgTypes.DOUBLE
 * ArgTypes.UUID
 *
 * Example usage: /targetentitycmd --health 50 --debug
 */
public class AbsTargetEntityCommand extends AbstractTargetEntityCommand {
    private final DefaultArg<Float> healthArg;
    private final FlagArg debugArg;

    public AbsTargetEntityCommand() {
        super("targetentitycmd", "An abstract target entity command example");
        this.healthArg = this.withDefaultArg("health", "Amount to heal entity", ArgTypes.FLOAT, (float)100, "Desc of Default: 100");
        this.debugArg = this.withFlagArg("debug", "Add debug logs");

        // addUsageVariant(new GiveOtherCommand()); // Example of adding usage variants from other commands

        // Adding aliases to the command
        addAliases("tec", "targc");
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull ObjectList<Ref<EntityStore>> objectList, @Nonnull World world, @Nonnull Store<EntityStore> store) {

        if (this.debugArg.get(commandContext) == true) {
            commandContext.sendMessage(Message.raw("Debug: Starting heal process..."));
        }

        if (objectList.isEmpty()) return;
        Ref<EntityStore> firstRef = objectList.getFirst();
        EntityStatMap stats = store.getComponent(firstRef, EntityStatMap.getComponentType());

        if (stats == null) {
            commandContext.sendMessage(Message.raw("This entity has no stats!"));
            return;
        }

        int healthIdx = DefaultEntityStatTypes.getHealth();
        EntityStatValue health = stats.get(healthIdx);

        if (health == null) {
            commandContext.sendMessage(Message.raw("This entity has no health!"));
            return;
        }

        float missing = health.getMax() - health.get();

        if (this.debugArg.get(commandContext) == true) {
            commandContext.sendMessage(Message.raw("Current Health: " + health.get()));
            commandContext.sendMessage(Message.raw("Max Health: " + health.getMax()));
            commandContext.sendMessage(Message.raw("Missing Health: " + missing));
            commandContext.sendMessage(Message.raw("Adding : " + healthArg.get(commandContext) + " HP"));
            commandContext.sendMessage(Message.raw("New HP : " + (health.get() + healthArg.get(commandContext))));
        }

        stats.addStatValue(healthIdx, healthArg.get(commandContext));
        commandContext.sendMessage(Message.raw("Healed entity for " + healthArg.get(commandContext) + " HP!"));
    }
}
