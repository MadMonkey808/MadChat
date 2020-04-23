package me.madmonkey.madchat.Utils;

import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {
    public static void sendMessage(Player p, String msg) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void broadcastMessage(String msg) {
        for (Player players : Bukkit.getOnlinePlayers())
            players.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void log(Level level, String msg) {
        System.out.println(msg);
    }
}
