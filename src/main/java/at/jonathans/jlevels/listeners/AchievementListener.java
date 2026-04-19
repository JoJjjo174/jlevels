package at.jonathans.jlevels.listeners;

import at.jonathans.jlevels.JLevels;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AchievementListener implements Listener {

    private final JLevels plugin;
    public AchievementListener() {
        this.plugin = JLevels.getInstance();
    }
    @EventHandler
    public void onAchievement(PlayerAdvancementDoneEvent e) {

        NamespacedKey key = e.getAdvancement().getKey();

        if (!key.getKey().startsWith("recipes/")) {

            Player p = e.getPlayer();
            plugin.getXpManager().addXP(p, plugin.getConfig().getInt("rewards.achievement"), plugin.getMessage("xpreason-achievement"));

        }

    }

}
