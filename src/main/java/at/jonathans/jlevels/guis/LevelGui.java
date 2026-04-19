package at.jonathans.jlevels.guis;

import at.jonathans.jlevels.JlevelsPlayer;
import com.destroystokyo.paper.MaterialTags;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LevelGui implements InventoryHolder {

    private final Inventory inventory;
    private int scrollOffset;

    private int level;
    private int xp;
    private int requiredXp;

    public LevelGui(JlevelsPlayer jPlayer) {
        this.inventory = Bukkit.createInventory(
                this,
                45,
                Component.text("Leveling")
        );

        level = jPlayer.getLevel();
        xp = jPlayer.getXp();
        requiredXp = jPlayer.getRequiredXp(level);

        scrollOffset = 0;
        setItems();
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    private void setItems() {

        int displayLevel = Math.max(level % 100, 1);
        ItemStack currentLevelItem = new ItemStack(Material.EXPERIENCE_BOTTLE, displayLevel);
        ItemMeta currentLevelItemMeta = currentLevelItem.getItemMeta();
        currentLevelItemMeta.displayName(
                Component.text(String.format("You are Level %d", level))
        );
        currentLevelItemMeta.lore(List.of(
                Component.text(String.format("%d / %d XP", xp, requiredXp)),
                Component.text("to next level")
        ));
        currentLevelItemMeta.setMaxStackSize(99);
        currentLevelItem.setItemMeta(currentLevelItemMeta);
        inventory.setItem(4, currentLevelItem);

        int offset = 19;
        int minDisplayLevel = Math.max(level - 2 + scrollOffset, 1);
        for (int diff = 0; diff < 7; diff++) {
            int slot = offset + diff;
            int slotLevel = minDisplayLevel + diff;
            int displaySlotLevel = Math.max(slotLevel % 100, 1);

            ItemStack levelItem = new ItemStack(getLevelMaterial(slotLevel, level), displaySlotLevel);
            ItemMeta levelItemMeta = levelItem.getItemMeta();
            levelItemMeta.displayName(
                    Component.text(String.format("Level %d", slotLevel))
            );
            levelItemMeta.setMaxStackSize(99);
            levelItem.setItemMeta(levelItemMeta);
            inventory.setItem(slot, levelItem);
        }

        ItemStack scrollLeftArrow = new ItemStack(Material.ARROW);
        ItemMeta scrollLeftItemMeta = scrollLeftArrow.getItemMeta();
        scrollLeftItemMeta.displayName(
                Component.text("Scroll Left")
        );
        scrollLeftArrow.setItemMeta(scrollLeftItemMeta);
        inventory.setItem(36, scrollLeftArrow);

        ItemStack scrollRightArrow = new ItemStack(Material.ARROW);
        ItemMeta scrollRightItemMeta = scrollRightArrow.getItemMeta();
        scrollRightItemMeta.displayName(
                Component.text("Scroll Right")
        );
        scrollRightArrow.setItemMeta(scrollRightItemMeta);
        inventory.setItem(44, scrollRightArrow);

    }

    private Material getLevelMaterial(int level, int playerLevel) {

        if (level <= playerLevel) {
            return Material.LIME_STAINED_GLASS_PANE;
        } else if (level == playerLevel+1) {
            return Material.YELLOW_STAINED_GLASS_PANE;
        } else {
            return Material.RED_STAINED_GLASS_PANE;
        }

    }

    public void scrollLeft() {
        scrollOffset--;
        inventory.clear();
        setItems();
    }

    public void scrollRight() {
        scrollOffset++;
        inventory.clear();
        setItems();
    }
}
