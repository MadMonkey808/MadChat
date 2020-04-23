package me.madmonkey.madchat.Events;

import me.madmonkey.madchat.Main;
import me.madmonkey.madchat.Commands.Chat;
import me.madmonkey.madchat.Utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ToggleChat implements Listener {
    private Main plugin;

    public ToggleChat(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        if (Chat.muted)
            if (!p.hasPermission("chatmanager.togglechat")) {
                event.setCancelled(true);
                Utils.sendMessage(p, "&cChat is currently Locked.");
            } else if (p.hasPermission("chatmanager.togglechat")) {
                event.setCancelled(false);
            }
    }
}
