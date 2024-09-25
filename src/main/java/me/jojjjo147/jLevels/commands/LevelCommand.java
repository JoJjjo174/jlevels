package me.jojjjo147.jLevels.commands;

import gg.gyro.localeAPI.Locales;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

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
    private final Locales locales = Locales.getInstance();

    public LevelCommand(JLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player p) {

            String message = locales.get(p.getLocale(), "message-level");
            message = applyPlaceholders(p, message);

            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));

        } else {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locales.get("command-not-player")));
        }

        return true;
    }

    public String applyPlaceholders(Player player, String text) {

        PersistentDataContainer data = player.getPersistentDataContainer();
        int level = data.get(new NamespacedKey(plugin, "level"), PersistentDataType.INTEGER);
        int xp = data.get(new NamespacedKey(plugin, "xp"), PersistentDataType.INTEGER);

        Expression expression = new ExpressionBuilder(plugin.getConfig().getString("xp-formula"))
                .variables("x")
                .build()
                .setVariable("x", level);

        int required_xp = (int)expression.evaluate();

        text = text.replace("%player%", player.getDisplayName());
        text = text.replace("%level%", String.valueOf(level));
        text = text.replace("%required_xp%", String.valueOf(required_xp));
        text = text.replace("%xp%", String.valueOf(xp));

        return text;
    }

}
