package dev.hytalemodding.permissions;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.permissions.HytalePermissions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class PermissionExample extends AbstractCommand {

    public PermissionExample() {
        super("permissionexample", "An example command for permissions");
        requirePermission(HytalePermissions.fromCommand("permissionexample"));
        requirePermission(HytalePermissions.fromCommand("usercommands"));
    }

    @Nullable
    @Override
    protected CompletableFuture<Void> execute(@Nonnull CommandContext commandContext) {
        commandContext.sendMessage(Message.raw("Hello from ExampleCommand!"));
        return CompletableFuture.completedFuture(null);
    }
}
