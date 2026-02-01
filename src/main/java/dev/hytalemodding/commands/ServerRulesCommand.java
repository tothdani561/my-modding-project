package dev.hytalemodding.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class ServerRulesCommand extends AbstractAsyncCommand {

    public ServerRulesCommand() {
        super("rules", "Lists the servers rules");
    }

    @Nonnull
    @Override
    protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext commandContext) {
        commandContext.sendMessage(Message.raw("Az a szabály, hogy nincs szabály!"));
        return CompletableFuture.completedFuture(null);
    }
}
