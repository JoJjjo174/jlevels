package at.jonathans.jlevels.listeners;

import at.jonathans.jlevels.JLevels;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class SleepListener implements Listener {

    private final JLevels plugin;

    public SleepListener() {
        this.plugin = JLevels.getInstance();
    }

    @EventHandler
    public void onWakeUp(PlayerBedLeaveEvent e) {
        long time = e.getPlayer().getWorld().getTime();
        if (time < 40) {
            plugin.getXpManager().addXP(e.getPlayer(), plugin.getConfig().getInt("rewards.sleep"), plugin.getMessage("xpreason-sleep"));
        }
    }

}