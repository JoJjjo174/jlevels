package me.jojjjo147.jLevels;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceholderApiHook extends PlaceholderExpansion {

    private final JLevels plugin;

    public PlaceholderApiHook(JLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "jlevel";
    }

    @Override
    public @NotNull String getAuthor() {
        return "JoJjjo147";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {

        if( player != null ) {
            PersistentDataContainer data = player.getPersistentDataContainer();

            if(data.has(new NamespacedKey(plugin, "level"), PersistentDataType.INTEGER)) {

                return String.valueOf(data.get(new NamespacedKey(plugin, "level"), PersistentDataType.INTEGER));

            } else {
                return "0";
            }

        } else {
            return "0";
        }
    }

}
