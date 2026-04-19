package at.jonathans.jlevels.guis;

import at.jonathans.jlevels.JLevels;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LevelGui implements InventoryHolder {

    private final Inventory inventory;
    private JLevels plugin;
    private int scrollOffset;
    private int level;
    private int xp;
    private int requiredXp;

    public LevelGui(int level, int xp) {
        plugin = JLevels.getInstance();
        this.inventory = Bukkit.createInventory(
                this,
                45,
                Component.text("Level ")
                        .append(Component.text(String.format("§%s%d", plugin.getXpManager().getLevelColour(level), level)))
        );
        this.level = level;
        this.xp = xp;
        requiredXp = plugin.getXpManager().getRequiredXP(level);

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
                Component.text("You are Level ")
                        .append(Component.text(String.format("§%s%d", plugin.getXpManager().getLevelColour(level), level)))
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
        int maxDisplayLevel;

        if (plugin.getXpManager().maxLevelSet()) {
            maxDisplayLevel = Math.min(minDisplayLevel + 6, plugin.getXpManager().getMaxLevel());
        } else {
            maxDisplayLevel= minDisplayLevel + 6;
        }

        int diff = maxDisplayLevel - minDisplayLevel;

        for (int change = 0; change <= diff; change++) {
            int slot = offset + change;
            int slotLevel = minDisplayLevel + change;
            int displaySlotLevel = Math.max(slotLevel % 100, 1);

            List<String> legacyRewards = List.of("&7No rewards");
            if (plugin.getConfig().contains("level-rewards." + slotLevel)) {
                legacyRewards = plugin.getConfig().getStringList("level-rewards." + slotLevel + ".text");
            }

            ArrayList<TextComponent> rewards = new ArrayList<>();
            for (String legacyRewardString : legacyRewards) {
                rewards.add(
                        Component.text(ChatColor.translateAlternateColorCodes('&', legacyRewardString))
                );
            }

            ItemStack levelItem = new ItemStack(getLevelMaterial(slotLevel, level), displaySlotLevel);
            ItemMeta levelItemMeta = levelItem.getItemMeta();
            levelItemMeta.displayName(
                    Component.text("Level ")
                            .append(Component.text(String.format("§%s%d", plugin.getXpManager().getLevelColour(slotLevel), slotLevel)))
            );
            levelItemMeta.lore(rewards);
            levelItemMeta.setMaxStackSize(99);
            levelItem.setItemMeta(levelItemMeta);
            inventory.setItem(slot, levelItem);
        }

        if (minDisplayLevel != 1) {
            ItemStack scrollLeftArrow = new ItemStack(Material.ARROW);
            ItemMeta scrollLeftItemMeta = scrollLeftArrow.getItemMeta();
            scrollLeftItemMeta.displayName(
                    Component.text("Scroll Left")
            );
            scrollLeftArrow.setItemMeta(scrollLeftItemMeta);
            inventory.setItem(36, scrollLeftArrow);
        }

        if (maxDisplayLevel != plugin.getXpManager().getMaxLevel()) {
            ItemStack scrollRightArrow = new ItemStack(Material.ARROW);
            ItemMeta scrollRightItemMeta = scrollRightArrow.getItemMeta();
            scrollRightItemMeta.displayName(
                    Component.text("Scroll Right")
            );
            scrollRightArrow.setItemMeta(scrollRightItemMeta);
            inventory.setItem(44, scrollRightArrow);
        }


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
