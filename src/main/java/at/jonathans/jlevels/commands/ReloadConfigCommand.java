package at.jonathans.jlevels.commands;

import at.jonathans.jlevels.JLevels;
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

        commandSender.sendMessage(plugin.getMessage("message-reload-config"));
        plugin.reload();
        commandSender.sendMessage(plugin.getMessage("message-reload-config-done"));

        return true;
    }
}
