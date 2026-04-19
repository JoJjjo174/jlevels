package at.jonathans.jlevels;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class JlevelsPlayer {

    private final JLevels plugin;
    private final Player player;
    private final PersistentDataContainer data;
    private final NamespacedKey levelNameSpacedkey;
    private final NamespacedKey xpNameSpacedKey;

    public JlevelsPlayer(JLevels plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        data = player.getPersistentDataContainer();
        levelNameSpacedkey = new NamespacedKey(plugin, "level");
        xpNameSpacedKey = new NamespacedKey(plugin, "xp");
    }

    public Player getPlayer() {
        return player;
    }

    public int getLevel() {
        int level = data.get(levelNameSpacedkey, PersistentDataType.INTEGER);
        return level;
    }

    public int getXp() {
        int xp = data.get(xpNameSpacedKey, PersistentDataType.INTEGER);
        return xp;
    }

    public int getRequiredXp() {
        return plugin.getXpManager().getRequiredXP(getLevel());
    }

    public int getRequiredXp(int level) {
        return plugin.getXpManager().getRequiredXP(level);
    }
}
