package me.jojjjo147.jLevels.listeners;

import gg.gyro.localeAPI.Locales;
import me.jojjjo147.jLevels.JLevels;
import me.jojjjo147.jLevels.XPManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class XpBottleListener implements Listener {

    private final JLevels plugin;
    private final XPManager xpmg;
    private final Locales locales = Locales.getInstance();

    public XpBottleListener(JLevels plugin, XPManager xpmg) {
        this.plugin = plugin;
        this.xpmg = xpmg;
    }

    @EventHandler
    public void onXpBottleThrow(PlayerInteractEvent e) {

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            ItemStack item = e.getItem();

            if (item != null && item.getType() == Material.EXPERIENCE_BOTTLE) {

                PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();

                if (data.has(new NamespacedKey(plugin, "jxp"), PersistentDataType.INTEGER)) {

                    e.setCancelled(true);

                    int xpAmount = data.get(new NamespacedKey(plugin, "jxp"), PersistentDataType.INTEGER);

                    Player p = e.getPlayer();
                    item.setAmount(item.getAmount() - 1);

                    xpmg.addXP(p, xpAmount, plugin.getString(p, "xpreason-xpbottle"));
                    p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

                }

            }

        }



    }

}
