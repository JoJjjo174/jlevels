package me.jojjjo147.jLevels.commands;

import gg.gyro.localeAPI.Locales;
import me.jojjjo147.jLevels.JLevels;
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
    private final Locales locales = Locales.getInstance();

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

                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getString(p, "name-xpbottle").replace("%xp%", args[0])));
                    meta.setLore(Arrays.asList(
                            ChatColor.translateAlternateColorCodes('&', plugin.getString(p, "lore-xpbottle").replace("%xp%", args[0]))
                    ));

                    item.setItemMeta(meta);
                    item.setAmount(Integer.valueOf(args[1]));

                    p.getInventory().addItem(item);

                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getString(p, "message-xpbottle-given")));

                } catch (NumberFormatException e) {
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getString(p, "message-xp-number")));
                }

            } else {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getString(p, "message-missing-arguments")));
            }

        } else {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locales.get("command-not-player")));
        }

        return true;
    }
}
