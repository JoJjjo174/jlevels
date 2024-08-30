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
    private final XPManager xpmg;

    public AddXPCommand(JLevels plugin, XPManager xpmg) {
        this.plugin = plugin;
        this.xpmg = xpmg;

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player p) {
            if (args.length > 1) {

                Player target = Bukkit.getServer().getPlayerExact(args[0]);
                try {
                    int xpAmount = Integer.parseInt(args[1]);

                    if (target != null) {

                        xpmg.addXP(target, xpAmount);

                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lang.message-add-xp")));

                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lang.message-invalid-player")));
                    }

                } catch (Exception e) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lang.message-xp-number")));
                }

            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lang.message-missing-arguments")));
            }
        } else {
            plugin.getLogger().info(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lang.command-not-player")));
        }

        return true;
    }
}
