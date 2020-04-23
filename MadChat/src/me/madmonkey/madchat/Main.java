package me.madmonkey.madchat;

import java.util.logging.Level;
import me.madmonkey.madchat.Commands.Chat;
import me.madmonkey.madchat.Events.SlowChat;
import me.madmonkey.madchat.Events.ToggleChat;
import me.madmonkey.madchat.Utils.Utils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public void onEnable() {
        Utils.log(Level.INFO, "-=-=-=-=-=-=-=-=-=-=-=-=-");
        Utils.log(Level.INFO, "MadChat made by MadMonkey");
        Utils.log(Level.INFO, "Version: 1.0");
        Utils.log(Level.INFO, "-=-=-=-=-=-=-=-=-=-=-=-=-");
        registerCommands();
        registerEvents();
        saveDefaultConfig();
        reloadConfig();
    }

    public void onDisable() {
        Utils.log(Level.INFO, "ChatManager has been disabled.");
    }

    public void registerCommands() {
        getCommand("chat").setExecutor((CommandExecutor)new Chat(this));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents((Listener)new ToggleChat(this), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new SlowChat(this), (Plugin)this);
    }
}
