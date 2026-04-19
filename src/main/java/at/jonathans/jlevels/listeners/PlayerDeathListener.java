package at.jonathans.jlevels.listeners;

import at.jonathans.jlevels.JLevels;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final JLevels plugin;

    public PlayerDeathListener() {
        this.plugin = JLevels.getInstance();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {

        if (e.getEntity().getKiller() != null && e.getEntity().getKiller() instanceof Player killer) {

            int xpAmount = plugin.getConfig().getInt("rewards.player-kill");
            plugin.getXpManager().addXP(killer, xpAmount, plugin.getMessage("xpreason-player-killed"));

        }

    }

}
