package me.jojjjo147.jLevels.commands;

import gg.gyro.localeAPI.Locales;
import me.jojjjo147.jLevels.JLevels;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadConfigCommand implements CommandExecutor {

    private final JLevels plugin;
    private final Locales locales = Locales.getInstance();

    public ReloadConfigCommand(JLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locales.get("message-reload-config")));
        plugin.reloadConfig();
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locales.get("message-reload-config-done")));

        return true;
    }
}
