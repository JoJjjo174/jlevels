package me.jojjjo147.jLevels;

import gg.gyro.localeAPI.Locales;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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
    private final Locales locales = Locales.getInstance();

    public XPManager(JLevels plugin) {
        this.plugin = plugin;
    }
    public void addXP(Player p, int addXpAmount, String reason) {

        sendActionbar(p, addXpAmount, reason);

        PersistentDataContainer data = p.getPersistentDataContainer();

        final NamespacedKey levelKey = new NamespacedKey(plugin, "level");
        final NamespacedKey xpKey = new NamespacedKey(plugin, "xp");

        int xp = data.get(xpKey, PersistentDataType.INTEGER);
        int level = data.get(levelKey, PersistentDataType.INTEGER);

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

                p.sendMessage(ChatColor.translateAlternateColorCodes('&', applyPlaceholders(level, rewards, locales.get(p.getLocale(), "message-levelup"))));

                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

                for (String command : plugin.getConfig().getStringList("level-rewards." + level + ".commands")) {
                    command = command.replace("%player%", p.getDisplayName());
                    Bukkit.dispatchCommand(console, command);
                }

                expression.setVariable("x", level);
                required_xp = (int)expression.evaluate();
            }

            p.playSound(p, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);

            data.set(xpKey, PersistentDataType.INTEGER, xp);
            data.set(levelKey, PersistentDataType.INTEGER, level);

        } else {
            data.set(new NamespacedKey(plugin, "xp"), PersistentDataType.INTEGER, xp);
        }

    }

    public void sendActionbar(Player p, int amount, String reason) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', applyActionbarPlaceholders(amount, reason, locales.get(p.getLocale(), "actionbar-gained-xp")))));
    }

    public String applyActionbarPlaceholders(int xp, String reason, String text) {

        text = text.replace("%xp%", String.valueOf(xp));
        text = text.replace("%reason%", reason);

        return text;
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
