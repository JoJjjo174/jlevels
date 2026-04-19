package at.jonathans.jlevels.listeners;

import at.jonathans.jlevels.guis.LevelGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

public class GuiDragListener implements Listener {

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {

        if (event.getInventory().getHolder() instanceof LevelGui) {
            event.setCancelled(true);
        }

    }

}
