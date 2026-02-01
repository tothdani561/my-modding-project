package dev.hytalemodding;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import dev.hytalemodding.commands.*;
import dev.hytalemodding.events.BasicChatFormatter;
import dev.hytalemodding.events.ExampleCancelCraft;
import dev.hytalemodding.events.ExampleEvent;

import javax.annotation.Nonnull;

public class ExamplePlugin extends JavaPlugin {

    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public ExamplePlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        LOGGER.atInfo().log("[STOKEYNPCS] ExamplePlugin is initializing!");
        commands();
        events();
        systems();
    }

    private void systems() {
        this.getEntityStoreRegistry().registerSystem(new ExampleCancelCraft());
    }

    private void events() {
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, ExampleEvent::onPlayerReady);
        this.getEventRegistry().registerGlobal(PlayerChatEvent.class, BasicChatFormatter::onPlayerChat);
    }

    private void commands() {
        this.getCommandRegistry().registerCommand(new ExampleCommand("example", "An example command"));
        this.getCommandRegistry().registerCommand(new ServerRulesCommand());
        this.getCommandRegistry().registerCommand(new AbsPlayerCommand());
        this.getCommandRegistry().registerCommand(new AbsTargetPlayerHealCommand());
        this.getCommandRegistry().registerCommand(new AbsTargetEntityCommand());
        this.getCommandRegistry().registerCommand(new AbsTargetEntityCommand());
    }
}