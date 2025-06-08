package com.serenity.serenityCore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

public final class SerenityCore extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println(ChatColor.GREEN + "SerenityCore has been enabled!");
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
