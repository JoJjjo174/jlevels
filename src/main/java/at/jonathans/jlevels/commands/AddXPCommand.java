package at.jonathans.jlevels.commands;

import at.jonathans.jlevels.JLevels;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AddXPCommand implements CommandExecutor, TabExecutor {

    private final JLevels plugin;

    public AddXPCommand(JLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (args.length < 2) {
            commandSender.sendMessage(plugin.getMessage("message-missing-arguments"));
            return true;
        }

        Player target = Bukkit.getServer().getPlayerExact(args[0]);
        if (target == null) {
            commandSender.sendMessage(plugin.getMessage("message-invalid-player"));
            return true;
        }

        String stringXpArgument = args[1];
        int xpArgument;
        try {
            xpArgument = Integer.parseInt(stringXpArgument);
        } catch (NumberFormatException e) {
            commandSender.sendMessage(plugin.getMessage("message-xp-number"));
            return true;
        }

        String xpReason;
        if (args.length > 2) {
            StringBuilder xpReasonBuilder = new StringBuilder();

            for (int i = 2; i < args.length; i++) {
                xpReasonBuilder.append(args[i]).append(" ");
            }

            xpReason = xpReasonBuilder.toString().strip();
        } else {
            xpReason = plugin.getMessage("xpreason-admin");
        }

        plugin.getXpManager().addXP(target, xpArgument, xpReason);
        commandSender.sendMessage(plugin.getMessage("message-add-xp"));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return switch (args.length) {
            case 1 -> null;
            case 2 -> List.of("<xp>");
            default -> List.of("[message]");
        };
    }
}
