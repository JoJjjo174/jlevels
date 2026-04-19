package at.jonathans.jlevels.listeners;

import at.jonathans.jlevels.guis.LevelGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getInventory().getHolder() instanceof LevelGui holder) {
            int guiSize = holder.getInventory().getSize();
            if (event.getRawSlot() < guiSize || event.getClick().isShiftClick() || event.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
                event.setCancelled(true);
            }
        }

    }

}
