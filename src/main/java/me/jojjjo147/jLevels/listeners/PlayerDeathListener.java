package me.jojjjo147.jLevels.listeners;

import me.jojjjo147.jLevels.JLevels;
import me.jojjjo147.jLevels.XPManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final JLevels plugin;
    private final XPManager xpmg;

    public PlayerDeathListener(JLevels plugin, XPManager xpmg) {
        this.plugin = plugin;
        this.xpmg = xpmg;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {

        if (e.getEntity().getKiller() != null && e.getEntity().getKiller() instanceof Player killer) {

            int xpAmount = plugin.getConfig().getInt("rewards.player-kill");
            xpmg.addXP(killer, xpAmount, plugin.getMessage("xpreason-player-killed"));

        }

    }

}
