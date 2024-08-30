package me.jojjjo147.jLevels;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

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

                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lang.message-levelup")));

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
}
