package me.madmonkey.madchat.Commands;

import me.madmonkey.madchat.Main;
import me.madmonkey.madchat.Utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Chat implements CommandExecutor {
    private Main plugin;

    public Chat(Main plugin) {
        this.plugin = plugin;
    }

    public static boolean muted = false;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("chat")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You cannot run this from Console.");
                return true;
            }
            Player p = (Player)sender;
            if (args.length == 0) {
                if (p.hasPermission("chat.help")) {
                    Utils.sendMessage(p, "&cCommands:");
                    Utils.sendMessage(p, "&7/chat");
                    Utils.sendMessage(p, "&7/chat clear");
                    Utils.sendMessage(p, "&7/chat toggle");
                    Utils.sendMessage(p, "&7/chat slow");
                    return true;
                }
                Utils.sendMessage(p, "&cYou do not have enough permission to perform this command.");
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("clear"))
                if (p.hasPermission("chatmanager.clearchat")) {
                    for (int x = 0; x < 100; x++)
                        Utils.broadcastMessage("");
                    Utils.broadcastMessage("&7Chat has been cleared by " + p.getDisplayName());
                } else {
                    Utils.sendMessage(p, "&cYou do not have enough permission to perform this command.");
                }
            if (args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
                if (!p.hasPermission("chatmanager.togglechat"))
                    Utils.sendMessage(p, "&cYou do not have enough permission to perform this command.");
                if (!muted && p.hasPermission("chatmanager.togglechat")) {
                    muted = true;
                    Utils.broadcastMessage("&cChat has been Muted by " + p.getDisplayName());
                } else if (muted && p.hasPermission("chatmanager.togglechat")) {
                    muted = false;
                    Utils.broadcastMessage("&aChat has been UnMuted by " + p.getDisplayName());
                }
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("slow")) {
                if (!p.hasPermission("chatmanager.slowchat")) {
                    Utils.sendMessage(p, "&cYou do not have enough permission to perform this command.");
                    return true;
                }
                Utils.sendMessage(p, "&7Chat Slow: &c" + String.valueOf(this.plugin.getConfig().getInt("ChatManager.Slow")) + " &7second" + ((this.plugin.getConfig().getInt("ChatManager.Slow") == 1) ? "" : "s"));
                return true;
            }
            if (args.length == 2 && p.hasPermission("chatmanager.slowchat")) {
                if (!p.hasPermission("chatmanager.slowchat")) {
                    Utils.sendMessage(p, "&cYou do not have enough permission to perform this command.");
                    return true;
                }
                try {
                    int interval = Integer.parseInt(args[1]);
                    this.plugin.getConfig().set("ChatManager.Slow", Integer.valueOf(interval));
                    this.plugin.saveConfig();
                    Utils.broadcastMessage("&7Chat has been slowed for &c" + args[1] + " &7second" + ((interval == 1) ? "" : "s"));
                } catch (NumberFormatException e) {
                    Utils.sendMessage(p, "&c" + args[1] + "is not a valid number.");
                }
            }
        }
        return true;
    }
}
