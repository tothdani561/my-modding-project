package dev.hytalemodding;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import dev.hytalemodding.block.ExampleBlock;
import dev.hytalemodding.block.ExampleInitializer;
import dev.hytalemodding.block.ExampleSystem;
import dev.hytalemodding.commands.*;
import dev.hytalemodding.events.BasicChatFormatter;
import dev.hytalemodding.events.ExampleCancelCraft;
import dev.hytalemodding.events.ExampleEvent;

import javax.annotation.Nonnull;

public class ExamplePlugin extends JavaPlugin {

    protected static ExamplePlugin instance;
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private ComponentType exampleBlockComponentType;

    public static ExamplePlugin get() {
        return instance;
    }

    public ExamplePlugin(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;
    }

    @Override
    protected void setup() {
        LOGGER.atInfo().log("[STOKEYNPCS] ExamplePlugin is initializing!");
        commands();
        events();
        systems();
        this.exampleBlockComponentType = this.getChunkStoreRegistry().registerComponent(ExampleBlock.class, "ExampleBlock", ExampleBlock.CODEC);
    }

    @Override
    protected void start() {
        instance = this;
        this.getChunkStoreRegistry().registerSystem(new ExampleSystem());
        this.getChunkStoreRegistry().registerSystem(new ExampleInitializer());
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

    public ComponentType getExampleBlockComponentType() {
        return this.exampleBlockComponentType;
    }
}