package me.jojjjo147.jLevels.commands;

import me.jojjjo147.jLevels.JLevels;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class GiveBottleCommand implements CommandExecutor, TabExecutor {

    private final JLevels plugin;

    public GiveBottleCommand(JLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (args.length < 3) {
            commandSender.sendMessage(plugin.getMessage("message-missing-arguments"));
            return true;
        }

        String targetPlayerArgument = args[0];
        String stringXpArgument = args[1];
        String stringAmountArgument = args[2];

        int xpArgument, amountArgument;
        try {
            xpArgument = Integer.parseInt(stringXpArgument);
            amountArgument = Integer.parseInt(stringAmountArgument);
        } catch (NumberFormatException e) {
            commandSender.sendMessage(plugin.getMessage("message-xp-number"));
            return true;
        }

        Player target = Bukkit.getServer().getPlayerExact(targetPlayerArgument);
        if (target == null) {
            commandSender.sendMessage(plugin.getMessage("message-invalid-player"));
            return true;
        }

        ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        data.set(new NamespacedKey(plugin, "jxp"), PersistentDataType.INTEGER, xpArgument);

        meta.setDisplayName(plugin.getMessage("name-xpbottle").replace("%xp%", stringXpArgument));
        meta.setLore(Arrays.asList(
                plugin.getMessage("lore-xpbottle").replace("%xp%", stringXpArgument)
        ));

        item.setItemMeta(meta);
        item.setAmount(amountArgument);

        target.getInventory().addItem(item);

        commandSender.sendMessage(applyPlaceholders(plugin.getMessage("message-xpbottle-given"), target, amountArgument));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return switch (args.length) {
            case 1 -> null;
            case 2 -> List.of("<xp>");
            case 3 -> List.of("<amount>");
            default -> List.of();
        };
    }

    private String applyPlaceholders(String message, Player player, int amount) {
        return message
                .replace("%player%", player.getDisplayName())
                .replace("%amount%", Integer.toString(amount));
    }
}
