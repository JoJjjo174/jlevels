package me.jojjjo147.jLevels;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class XPManager {

    private final JLevels plugin;

    public XPManager(JLevels plugin) {
        this.plugin = plugin;
    }
    public void addXP(Player p, int addXpAmount) {

        PersistentDataContainer data = p.getPersistentDataContainer();

        int xp = data.get(new NamespacedKey(plugin, "xp"), PersistentDataType.INTEGER);
        int level = data.get(new NamespacedKey(plugin, "level"), PersistentDataType.INTEGER);

        Expression expression = new ExpressionBuilder(plugin.getConfig().getString("xp-formula"))
                .variables("x")
                .build()
                .setVariable("x", level);

        int required_xp = (int)expression.evaluate();
        xp += addXpAmount;

        if (required_xp <= xp) {

            while (required_xp <= xp) {
                level++;
                xp -= required_xp;

                List<String> rewards = Arrays.asList("&7No rewards");

                if (plugin.getConfig().contains("level-rewards." + level)) {
                    rewards = plugin.getConfig().getStringList("level-rewards." + level + ".text");
                }

                p.sendMessage(ChatColor.translateAlternateColorCodes('&', applyPlaceholders(level, rewards, plugin.getConfig().getString("lang.message-levelup"))));

                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

                for (String command : plugin.getConfig().getStringList("level-rewards." + level + ".commands")) {
                    command = command.replace("%player%", p.getDisplayName());
                    Bukkit.dispatchCommand(console, command);
                }

                expression.setVariable("x", level);
                required_xp = (int)expression.evaluate();
            }

            p.playSound(p, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);

            data.set(new NamespacedKey(plugin, "xp"), PersistentDataType.INTEGER, xp);
            data.set(new NamespacedKey(plugin, "level"), PersistentDataType.INTEGER, level);

        } else {
            data.set(new NamespacedKey(plugin, "xp"), PersistentDataType.INTEGER, xp);
        }

    }

    public String applyPlaceholders(int level, List<String> rewards, String text) {

        String rewardString = "";

        for (String reward : rewards) {
            rewardString += "   &e-&r " + reward + "\n";
        }

        rewardString = rewardString.stripTrailing();
        rewardString += "&r";

        text = text.replace("%level%", String.valueOf(level));
        text = text.replace("%rewards%", rewardString);

        return text;
    }
}
