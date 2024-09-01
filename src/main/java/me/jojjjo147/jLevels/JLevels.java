package me.jojjjo147.jLevels;

import me.jojjjo147.jLevels.commands.AddXPCommand;
import me.jojjjo147.jLevels.commands.GiveBottleCommand;
import me.jojjjo147.jLevels.commands.LevelCommand;
import me.jojjjo147.jLevels.listeners.AchievementListener;
import me.jojjjo147.jLevels.listeners.JoinListener;
import me.jojjjo147.jLevels.listeners.MobKillListener;
import me.jojjjo147.jLevels.listeners.XpBottleListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class JLevels extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new XpBottleListener(this), this);

        XPManager xpmg = new XPManager(this);

        if (getConfig().getInt("rewards.monster-kill") > 0) {
            getServer().getPluginManager().registerEvents(new MobKillListener(this, xpmg), this);
        }

        if (getConfig().getInt("rewards.achievement") > 0) {
            getServer().getPluginManager().registerEvents(new AchievementListener(this, xpmg), this);
        }

        getCommand("level").setExecutor(new LevelCommand(this));
        getCommand("addxp").setExecutor(new AddXPCommand(this, new XPManager(this)));
        getCommand("givexpbottle").setExecutor(new GiveBottleCommand(this));

        getLogger().info("jLevels finished loading!");

    }


}
