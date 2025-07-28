package com.serenity.serenityCore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SerenityCore extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("SerenityCore has been enabled!");
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("SerenityCore has been disabled!");
    }
}
