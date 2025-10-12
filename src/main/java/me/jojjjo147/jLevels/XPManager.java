package me.jojjjo147.jLevels;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class XPManager {

    private final JLevels plugin;
    private HashMap<Integer, Character> levelColours;

    public XPManager(JLevels plugin) {
        this.plugin = plugin;
        levelColours = getLevelColours();
    }

    public void addXP(Player p, int addXpAmount, String reason) {

        sendActionbar(p, addXpAmount, reason);

        PersistentDataContainer data = p.getPersistentDataContainer();

        final NamespacedKey levelKey = new NamespacedKey(plugin, "level");
        final NamespacedKey xpKey = new NamespacedKey(plugin, "xp");

        int xp = data.get(xpKey, PersistentDataType.INTEGER);
        int level = data.get(levelKey, PersistentDataType.INTEGER);

        int required_xp = getRequiredXP(level);
        xp += addXpAmount;

        int maxLevel = plugin.getConfig().getInt("max-level");
        boolean reachedMaxLevel = false;

        if (maxLevel != -1) {
            reachedMaxLevel = level >= maxLevel;
        }

        if (required_xp <= xp && !reachedMaxLevel) {

            while (required_xp <= xp && !reachedMaxLevel) {
                level++;
                xp -= required_xp;

                List<String> rewards = Arrays.asList("&7No rewards");

                if (plugin.getConfig().contains("level-rewards." + level)) {
                    rewards = plugin.getConfig().getStringList("level-rewards." + level + ".text");
                }

                p.sendMessage(ChatColor.translateAlternateColorCodes('&', applyPlaceholders(level, rewards, plugin.getMessage("message-levelup"))));

                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

                for (String command : plugin.getConfig().getStringList("level-rewards." + level + ".commands")) {
                    command = command.replace("%player%", p.getDisplayName());
                    Bukkit.dispatchCommand(console, command);
                }

                required_xp = getRequiredXP(level);

                if (maxLevel != -1) {
                    reachedMaxLevel = level >= maxLevel;
                }
            }

            p.playSound(p, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);

            data.set(xpKey, PersistentDataType.INTEGER, xp);
            data.set(levelKey, PersistentDataType.INTEGER, level);

        } else {
            data.set(new NamespacedKey(plugin, "xp"), PersistentDataType.INTEGER, xp);
        }

    }

    public void sendActionbar(Player p, int amount, String reason) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', applyActionbarPlaceholders(amount, reason, plugin.getMessage("actionbar-gained-xp")))));
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

        String colourCode = "&" + getLevelColour(level);

        text = text.replace("%level%", String.valueOf(level));
        text = text.replace("%rewards%", rewardString);
        text = text.replace("%level_colour%", colourCode);

        return text;
    }

    public int getRequiredXP (int level) {
        String xpFormula = plugin.getConfig().getString("xp-formula");

        if (xpFormula == null) {
            plugin.getLogger().warning("Couldn't fetch XP formula");
            return 0;
        }

        Expression expression = new ExpressionBuilder(xpFormula)
                .variables("x")
                .build()
                .setVariable("x", level);

        return (int)expression.evaluate();
    }

    private HashMap<Integer, Character> getLevelColours() {

        ConfigurationSection levelSection = plugin.getConfig().getConfigurationSection("level-rewards");

        if (levelSection == null) {
            plugin.getLogger().warning("Couldn't find level section in config");
            return null;
        }

        HashMap<Integer, Character> levelColours = new HashMap<>();

        for (String key : levelSection.getKeys(false)) {

            ConfigurationSection subSection = levelSection.getConfigurationSection(key);

            if (subSection == null || !subSection.contains("colour")) {
                continue;
            }

            levelColours.put(Integer.valueOf(key), subSection.getString("colour").charAt(0));

        }

        levelColours.put(0, plugin.getConfig().getString("default-level-colour").charAt(0));

        return levelColours;

    }

    public char getLevelColour(int level) {

        Set<Integer> sortedKeys = new TreeSet<>(levelColours.keySet());

        int currentColourLevel = 0;
        for (int key : sortedKeys) {

            if (level >= key) {
                currentColourLevel = key;
            } else {
                break;
            }

        }

        return levelColours.get(currentColourLevel);

    }

    public void reload() {

        levelColours = getLevelColours();

    }
}
