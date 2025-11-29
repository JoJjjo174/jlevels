package me.jojjjo147.jLevels.commands;

import me.jojjjo147.jLevels.JLevels;
import me.jojjjo147.jLevels.XPManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddXPCommand implements CommandExecutor {

    private final JLevels plugin;

    public AddXPCommand(JLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player p) {
            if (args.length > 1) {

                Player target = Bukkit.getServer().getPlayerExact(args[0]);
                try {
                    int xpAmount = Integer.parseInt(args[1]);

                    if (target != null) {

                        String xpReason = "";

                        if (args.length > 2) {

                            for (int i = 2; i < args.length; i++) {

                                xpReason += args[i] + " ";
                            }

                            xpReason = xpReason.substring(0, xpReason.length() - 1);

                        } else {
                            xpReason = plugin.getMessage("xpreason-admin");

                        }

                        plugin.getXpManager().addXP(target, xpAmount, xpReason);

                        commandSender.sendMessage(plugin.getMessage("message-add-xp"));

                    } else {
                        commandSender.sendMessage(plugin.getMessage("message-invalid-player"));
                    }

                } catch (NumberFormatException e) {
                    commandSender.sendMessage(plugin.getMessage("message-xp-number"));
                }

            } else {
                commandSender.sendMessage(plugin.getMessage("message-missing-arguments"));
            }
        } else {
            commandSender.sendMessage(plugin.getMessage("command-not-player"));
        }

        return true;
    }
}
