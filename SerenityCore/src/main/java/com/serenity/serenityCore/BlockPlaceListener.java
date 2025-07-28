package com.serenity.serenityCore;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class BlockPlaceListener implements Listener {

    private final SerenityCore plugin;

    // Player Logger
    private final Map<UUID, Set<String>> playerFirstPlacedBlocks = new HashMap<>();

    public BlockPlaceListener(SerenityCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();
        String placedBlockName = block.getType().name().toLowerCase();

        ConfigurationSection blocksSection = plugin.getConfig().getConfigurationSection("blocks");
        if (blocksSection == null) return;

        for (String key : blocksSection.getKeys(false)) {
            ConfigurationSection blockConfig = blocksSection.getConfigurationSection(key);
            if (blockConfig == null) continue;

            String configBlockName = blockConfig.getString("block", "").toLowerCase();
            if (!configBlockName.equals(placedBlockName)) continue;

            String type = blockConfig.getString("type", "every").toLowerCase();

            boolean shouldReward = false;
            if (type.equals("every")) {
                shouldReward = true;
            } else if (type.equals("first")) {
                Set<String> placedBlocks = playerFirstPlacedBlocks.computeIfAbsent(player.getUniqueId(), k -> new HashSet<>());
                if (!placedBlocks.contains(key)) {
                    placedBlocks.add(key);
                    shouldReward = true;
                }
            }

            if (shouldReward) {
                List<String> commands = blockConfig.getStringList("commands");
                for (String cmd : commands) {
                    String replaced = cmd.replace("%player%", player.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replaced);
                }

                List<String> messages = blockConfig.getStringList("messages");
                for (String msg : messages) {
                    String replaced = msg.replace("%player%", player.getName());
                    replaced = translateColors(replaced);
                    player.sendMessage(replaced);
                }
            }
            break;
        }
    }

    // & and hex colors
    private String translateColors(String message) {
        if (message == null) return null;

        message = translateHexColors(message);
        message = ChatColor.translateAlternateColorCodes('&', message);

        return message;
    }

    // Hex converter 
    private String translateHexColors(String message) {
        StringBuilder builder = new StringBuilder();
        char[] chars = message.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '&' && i + 7 < chars.length && chars[i + 1] == '#') {
                String hex = message.substring(i + 2, i + 8);
                try {
                    ChatColor hexColor = ChatColor.of("#" + hex);
                    builder.append(hexColor);
                    i += 7;
                    continue;
                } catch (IllegalArgumentException ignored) {
                }
            }
            builder.append(chars[i]);
        }
        return builder.toString();
    }
}
