package me.jojjjo147.jLevels.listeners;

import me.jojjjo147.jLevels.XPManager;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import me.jojjjo147.jLevels.JLevels;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class MobKillListener implements Listener {

    private final JLevels plugin;
    private final XPManager xpmg;

    public MobKillListener(JLevels plugin, XPManager xpmg) {
        this.plugin = plugin;
        this.xpmg = xpmg;
    }

    @EventHandler
    public void onMobKill(EntityDeathEvent e) {

        if (e.getEntity().getKiller() instanceof Player p && e.getEntity() instanceof Monster) {

            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("+10XP (Monster killed)"));

            xpmg.addXP(p, 10);

        }

    }

}
