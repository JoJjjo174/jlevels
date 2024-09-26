package me.jojjjo147.jLevels;

import me.jojjjo147.jLevels.commands.AddXPCommand;
import me.jojjjo147.jLevels.commands.GiveBottleCommand;
import me.jojjjo147.jLevels.commands.LevelCommand;
import me.jojjjo147.jLevels.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import org.bstats.bukkit.Metrics;
import gg.gyro.localeAPI.Locales;

public final class JLevels extends JavaPlugin {

    private static XPManager xpmg;
    private static Locales locales;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        if (!getConfig().getBoolean("uniform_language")) {
            Locales.saveDefaultConfig(this, getConfig().getString("default_language") + ".yml");
        } else {
            Locales.saveDefaultConfig(this, "en_us.yml");
            Locales.saveDefaultConfig(this, "fr_fr.yml");
        }

        locales = new Locales(this, getConfig().getString("default_language"));

        xpmg = new XPManager(this);

        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new XpBottleListener(this, xpmg), this);

        if (getConfig().getInt("rewards.monster-kill") > 0) {
            getServer().getPluginManager().registerEvents(new MobKillListener(this, xpmg), this);
        }

        if (getConfig().getInt("rewards.achievement") > 0) {
            getServer().getPluginManager().registerEvents(new AchievementListener(this, xpmg), this);
        }

        if (getConfig().getInt("rewards.player-kill") > 0) {
            getServer().getPluginManager().registerEvents(new PlayerDeathListener(this, xpmg), this);
        }

        getCommand("level").setExecutor(new LevelCommand(this));
        getCommand("addxp").setExecutor(new AddXPCommand(this, xpmg));
        getCommand("givexpbottle").setExecutor(new GiveBottleCommand(this));

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null && getConfig().getBoolean("enable-placeholder-api")) {
            new PlaceholderApiHook(this).register();
        }

        int pluginId = 23375;
        Metrics metrics = new Metrics(this, pluginId);

        getLogger().info("jLevels finished loading!");

    }

    public static XPManager getXpmg() {
        return xpmg;
    }

    public String getString(Player target, String key) {
        if (getConfig().getBoolean("uniform_language")) {
            return locales.get(key);
        } else {
            return locales.get(target.getLocale(), key);
        }
    }
}
