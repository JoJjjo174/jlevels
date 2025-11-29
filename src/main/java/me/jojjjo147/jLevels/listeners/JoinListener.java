package me.jojjjo147.jLevels.listeners;

import me.jojjjo147.jLevels.JLevels;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class JoinListener implements Listener {

    private final JLevels plugin;
    private final boolean outdated;

    public JoinListener(JLevels plugin, boolean outdated) {
        this.plugin = plugin;
        this.outdated = outdated;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        PersistentDataContainer data = p.getPersistentDataContainer();

        final NamespacedKey levelKey = new NamespacedKey(plugin, "level");
        final NamespacedKey xpKey = new NamespacedKey(plugin, "xp");

        if (!data.has(levelKey, PersistentDataType.INTEGER)) {
            data.set(levelKey, PersistentDataType.INTEGER, 0);
        }

        if (!data.has(xpKey, PersistentDataType.INTEGER)) {
            data.set(xpKey, PersistentDataType.INTEGER, 0);
        }

        if (outdated && p.hasPermission("jlevels.receive-outdated-warning")) {
            p.sendMessage(plugin.getMessage("outdated-warning"));
        }

    }

}
