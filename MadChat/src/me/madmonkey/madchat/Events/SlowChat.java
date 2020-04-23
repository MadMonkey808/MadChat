package me.madmonkey.madchat.Events;

import java.util.HashMap;
import java.util.Map;
import me.madmonkey.madchat.Main;
import me.madmonkey.madchat.Utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SlowChat implements Listener {
    private Main plugin;

    private final Map<String, Long> cooldownTime;

    public SlowChat(Main plugin) {
        this.cooldownTime = new HashMap<>();
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerChat(PlayerChatEvent event) {
        Player p = event.getPlayer();
        if (p.hasPermission("chatmanager.slow"))
            return;
        long now = System.currentTimeMillis();
        String name = p.getName();
        Long lastChat = this.cooldownTime.get(name);
        if (lastChat != null) {
            long earliestNext = lastChat.longValue() + (this.plugin.getConfig().getInt("ChatManager.Slow") * 1000);
            if (now < earliestNext) {
                int timeRemaining = (int)((earliestNext - now) / 1000L) + 1;
                Utils.sendMessage(p, "&7Please wait &c" + timeRemaining + " &7more second" + ((timeRemaining > 1) ? "s" : ""));
                event.setCancelled(true);
                return;
            }
        }
        this.cooldownTime.put(name, Long.valueOf(now));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        this.cooldownTime.remove(p.getName());
    }
}
