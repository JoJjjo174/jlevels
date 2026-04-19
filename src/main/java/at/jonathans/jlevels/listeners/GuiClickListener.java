package at.jonathans.jlevels.listeners;

import at.jonathans.jlevels.JLevels;
import at.jonathans.jlevels.guis.LevelGui;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
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

                if (event.getRawSlot() == 36 && event.getCurrentItem() != null) {
                    event.getWhoClicked().playSound(
                            Sound.sound(Key.key("ui.button.click"), Sound.Source.UI, 1f, 1f)
                    );
                    holder.scrollLeft();

                } else if (event.getRawSlot() == 44 && event.getCurrentItem() != null) {
                    event.getWhoClicked().playSound(
                            Sound.sound(Key.key("ui.button.click"), Sound.Source.UI, 1f, 1f)
                    );
                    holder.scrollRight();
                }
            }
        }

    }

}
