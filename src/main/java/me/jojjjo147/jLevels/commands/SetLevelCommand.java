package me.jojjjo147.jLevels.commands;

import me.jojjjo147.jLevels.JLevels;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetLevelCommand implements CommandExecutor {

    private final JLevels plugin;

    public SetLevelCommand(JLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length < 2) {
            commandSender.sendMessage(plugin.getMessage("message-missing-arguments"));
            return true;
        }

        Player player = Bukkit.getPlayer(strings[0]);
        if (player == null) {
            commandSender.sendMessage(plugin.getMessage("message-invalid-player"));
            return true;
        }

        try {
            int level = Integer.parseInt(strings[1]);

            plugin.getXpManager().setLevel(player, level);
            commandSender.sendMessage(applyPlaceholders(plugin.getMessage("message-level-set"), player, level));
            return true;

        } catch (NumberFormatException e) {
            commandSender.sendMessage(plugin.getMessage("message-level-number"));
            return true;
        }

    }

    private String applyPlaceholders(String message, Player player, int level) {
        message = message.replace("%player%", player.getDisplayName());
        message = message.replace("%level%", String.valueOf(level));

        return message;
    }
}
