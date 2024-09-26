package me.jojjjo147.jLevels.listeners;

import gg.gyro.localeAPI.Locales;
import me.jojjjo147.jLevels.XPManager;

import me.jojjjo147.jLevels.JLevels;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobKillListener implements Listener {

    private final JLevels plugin;
    private final XPManager xpmg;
    private final Locales locales = Locales.getInstance();

    public MobKillListener(JLevels plugin, XPManager xpmg) {
        this.plugin = plugin;
        this.xpmg = xpmg;
    }

    @EventHandler
    public void onMobKill(EntityDeathEvent e) {

        if (e.getEntity().getKiller() instanceof Player p && e.getEntity() instanceof Monster) {

            int xpAmount = plugin.getConfig().getInt("rewards.monster-kill");
            xpmg.addXP(p, xpAmount, plugin.getString(p, "xpreason-moster-killed"));

        }

    }


}
