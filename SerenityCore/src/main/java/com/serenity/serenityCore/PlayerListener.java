package com.serenity.serenityCore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {


    private final SerenityCore plugin;

    public PlayerListener(SerenityCore plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onFirstJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (!player.hasPlayedBefore()) {
            String firstJoin = plugin.getConfig().getString("onFirstJoin");
            if (firstJoin != null) {
                String onFirstJoin = firstJoin.replace("%player%", player.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), onFirstJoin);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        String joinEvery = plugin.getConfig().getString("onJoin");
        if (joinEvery != null) {
            String onJoinEvery = joinEvery.replace("%player%", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), onJoinEvery);
        }
    }
}