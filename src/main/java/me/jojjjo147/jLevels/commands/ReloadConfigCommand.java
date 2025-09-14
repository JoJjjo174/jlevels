package me.jojjjo147.jLevels.commands;

import me.jojjjo147.jLevels.JLevels;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadConfigCommand implements CommandExecutor {

    private final JLevels plugin;
    public ReloadConfigCommand(JLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessage("message-reload-config")));
        plugin.reloadAllConfigurations();
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessage("message-reload-config-done")));

        return true;
    }
}
