package me.jojjjo147.jLevels.commands;

import me.jojjjo147.jLevels.JLevels;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class LevelCommand implements CommandExecutor {

    private final JLevels plugin;

    public LevelCommand(JLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player p) {

            String message = plugin.getConfig().getString("lang.message-level");
            message = applyPlaceholders(p, message);

            p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));

        } else {
           plugin.getLogger().info(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lang.command-not-player")));
        }

        return true;
    }

    public String applyPlaceholders(Player player, String text) {

        PersistentDataContainer data = player.getPersistentDataContainer();
        int level = data.get(new NamespacedKey(plugin, "level"), PersistentDataType.INTEGER);

        text = text.replace("%player%", player.getDisplayName());
        text = text.replace("%level%", String.valueOf(level));

        return text;
    }

}
