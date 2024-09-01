package me.jojjjo147.jLevels.listeners;

import me.jojjjo147.jLevels.JLevels;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class XpBottleListener implements Listener {

    private final JLevels plugin;

    public XpBottleListener(JLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onXpBottleThrow(PlayerInteractEvent e) {

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            ItemStack item = e.getItem();

            if (item != null && item.getType() == Material.EXPERIENCE_BOTTLE) {

                PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();

                if (data.has(new NamespacedKey(plugin, "jxp"), PersistentDataType.INTEGER)) {

                    e.setCancelled(true);

                }

            }

        }



    }

}
