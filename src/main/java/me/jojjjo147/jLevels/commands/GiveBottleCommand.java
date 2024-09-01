package me.jojjjo147.jLevels.commands;

import me.jojjjo147.jLevels.JLevels;
import me.jojjjo147.jLevels.XPManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class GiveBottleCommand implements CommandExecutor {

    private final JLevels plugin;

    public GiveBottleCommand(JLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player p) {

            if (args.length > 1) {

                ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);
                ItemMeta meta = item.getItemMeta();
                PersistentDataContainer data = meta.getPersistentDataContainer();

                try {
                    data.set(new NamespacedKey(plugin, "jxp"), PersistentDataType.INTEGER, Integer.valueOf(args[0]));

                    meta.setDisplayName(ChatColor.AQUA + args[0] + "XP");
                    meta.setLore(Arrays.asList(
                            ChatColor.GRAY + "right click to get " + ChatColor.AQUA + args[0] + "XP"
                    ));

                    item.setItemMeta(meta);
                    item.setAmount(Integer.valueOf(args[1]));

                    p.getInventory().addItem(item);

                    p.sendMessage(ChatColor.GREEN + "Addded XP Bottle(s) to your inventory");

                } catch (NumberFormatException e) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lang.message-xp-number")));
                }

            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lang.message-missing-arguments")));
            }

        } else {
            plugin.getLogger().info(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lang.command-not-player")));
        }

        return true;
    }
}
