package me.jojjjo147.jLevels.listeners;

import me.jojjjo147.jLevels.XPManager;

import me.jojjjo147.jLevels.JLevels;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobKillListener implements Listener {

    private final JLevels plugin;

    public MobKillListener(JLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMobKill(EntityDeathEvent e) {

        if (e.getEntity().getKiller() instanceof Player p && e.getEntity() instanceof Monster) {

            int xpAmount = plugin.getConfig().getInt("rewards.monster-kill");
            plugin.getXpManager().addXP(p, xpAmount, plugin.getMessage("xpreason-moster-killed"));

        }

    }


}
