package me.jojjjo147.jLevels.listeners;

import me.jojjjo147.jLevels.JLevels;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class JoinListener implements Listener {

    private final JLevels plugin;

    public JoinListener(JLevels plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        PersistentDataContainer data = p.getPersistentDataContainer();

        if (!data.has(new NamespacedKey(plugin, "level"), PersistentDataType.INTEGER)) {
            data.set(new NamespacedKey(plugin, "level"), PersistentDataType.INTEGER, 0);
        }

        if (!data.has(new NamespacedKey(plugin, "xp"), PersistentDataType.INTEGER)) {
            data.set(new NamespacedKey(plugin, "xp"), PersistentDataType.INTEGER, 0);
        }

    }

}
