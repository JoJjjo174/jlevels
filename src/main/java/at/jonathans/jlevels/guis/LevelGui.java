package at.jonathans.jlevels.guis;

import at.jonathans.jlevels.JlevelsPlayer;
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
    private final JlevelsPlayer jPlayer;

    public LevelGui(JlevelsPlayer jPlayer) {
        this.inventory = Bukkit.createInventory(
                this,
                45,
                Component.text("Leveling")
        );
        this.jPlayer = jPlayer;

        setItems();
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    private void setItems() {

        int level = jPlayer.getLevel();
        int xp = jPlayer.getXp();
        int requiredXp = jPlayer.getRequiredXp(level);

        int displayLevel = Math.max(level % 100, 1);
        ItemStack levelItem = new ItemStack(Material.EXPERIENCE_BOTTLE, displayLevel);
        ItemMeta levelItemMeta = levelItem.getItemMeta();
        levelItemMeta.displayName(
                Component.text(String.format("You are Level %d", level))
        );
        levelItemMeta.lore(List.of(
                Component.text(String.format("%d / %d XP", xp, requiredXp)),
                Component.text("to next level")
        ));
        levelItemMeta.setMaxStackSize(99);
        levelItem.setItemMeta(levelItemMeta);

        this.inventory.setItem(4, levelItem);
    }
}
