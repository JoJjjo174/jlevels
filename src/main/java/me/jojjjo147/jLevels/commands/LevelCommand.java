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

            String message = plugin.getMessage("message-level");
            message = applyPlaceholders(p, message);

            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));

        } else {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessage("command-not-player")));
        }

        return true;
    }

    public String applyPlaceholders(Player player, String text) {

        PersistentDataContainer data = player.getPersistentDataContainer();
        int level = data.get(new NamespacedKey(plugin, "level"), PersistentDataType.INTEGER);
        int xp = data.get(new NamespacedKey(plugin, "xp"), PersistentDataType.INTEGER);

        int required_xp = plugin.getXpManager().getRequiredXP(level);

        String colourCode = "&" + plugin.getXpManager().getLevelColour(level);

        text = text.replace("%player%", player.getDisplayName());
        text = text.replace("%level%", String.valueOf(level));
        text = text.replace("%required_xp%", String.valueOf(required_xp));
        text = text.replace("%xp%", String.valueOf(xp));
        text = text.replace("%level_colour%", colourCode);

        return text;
    }

}
