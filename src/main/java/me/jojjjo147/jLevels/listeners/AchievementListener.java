package me.jojjjo147.jLevels.listeners;

import gg.gyro.localeAPI.Locales;
import me.jojjjo147.jLevels.JLevels;
import me.jojjjo147.jLevels.XPManager;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AchievementListener implements Listener {

    private final JLevels plugin;
    private final XPManager xpmg;
    private final Locales locales = Locales.getInstance();

    public AchievementListener(JLevels plugin, XPManager xpmg) {
        this.plugin = plugin;
        this.xpmg = xpmg;
    }
    @EventHandler
    public void onAchievement(PlayerAdvancementDoneEvent e) {

        NamespacedKey key = e.getAdvancement().getKey();

        if (!key.getKey().startsWith("recipes/")) {

            Player p = e.getPlayer();
            xpmg.addXP(p, plugin.getConfig().getInt("rewards.achievement"), locales.get(p.getLocale(), "xpreason-achievement"));

        }

    }

}
