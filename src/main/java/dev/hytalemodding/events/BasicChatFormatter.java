package dev.hytalemodding.events;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import java.awt.*;

public class BasicChatFormatter {

    public static void onPlayerChat(PlayerChatEvent event) {
        PlayerRef sender = event.getSender();
        if (event.getContent().equalsIgnoreCase("poo")) {
            event.setCancelled(true);
            sender.sendMessage(Message.raw("HEY, you cannot say that!").color(Color.RED));
        }

        if (event.getContent().equalsIgnoreCase("you stink")) {
            event.setContent("i stink");
        }

        event.setFormatter(((playerRef, message) ->
            Message.join(
                    Message.raw("[ADMIN] ").color(Color.RED),
                    Message.raw(sender.getUsername()).color(Color.YELLOW),
                    Message.raw(" : " + message).color(Color.WHITE)
            )));
    }

}
