package at.jonathans.jlevels.commands;

import at.jonathans.jlevels.JLevels;
import at.jonathans.jlevels.JlevelsPlayer;
import at.jonathans.jlevels.guis.LevelGui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.awt.*;
import java.awt.print.Paper;

public class LevelCommand implements CommandExecutor {

    private final JLevels plugin;

    public LevelCommand(JLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(plugin.getMessage("command-not-player"));
            return true;
        }

        Player player = (Player) commandSender;
        JlevelsPlayer jPlayer = new JlevelsPlayer(plugin, player);

        LevelGui levelGui = new LevelGui(jPlayer);
        player.openInventory(levelGui.getInventory());

        return true;
    }

    public String applyPlaceholders(Player player, String text) {

        PersistentDataContainer data = player.getPersistentDataContainer();
        int level = data.get(new NamespacedKey(plugin, "level"), PersistentDataType.INTEGER);
        int xp = data.get(new NamespacedKey(plugin, "xp"), PersistentDataType.INTEGER);

        int required_xp = plugin.getXpManager().getRequiredXP(level);

        String colourCode = "§" + plugin.getXpManager().getLevelColour(level);

        text = text.replace("%player%", player.getDisplayName());
        text = text.replace("%level%", String.valueOf(level));
        text = text.replace("%required_xp%", String.valueOf(required_xp));
        text = text.replace("%xp%", String.valueOf(xp));
        text = text.replace("%level_colour%", colourCode);

        return text;
    }

}
