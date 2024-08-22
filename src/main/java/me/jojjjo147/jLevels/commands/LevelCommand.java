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

            PersistentDataContainer data = p.getPersistentDataContainer();

            int level = data.get(new NamespacedKey(plugin, "level"), PersistentDataType.INTEGER);

            p.sendMessage(ChatColor.GRAY + "Yout current level is: " + ChatColor.LIGHT_PURPLE + level);

        } else {
           plugin.getLogger().info("This command can only be executed by a player!");
        }

        return true;
    }
}
