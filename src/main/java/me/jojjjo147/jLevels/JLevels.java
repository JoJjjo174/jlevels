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

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public final class JLevels extends JavaPlugin {

    private static XPManager xpmg;
    private static Locales locales;
    private static boolean outdated = false;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        if (getConfig().getBoolean("uniform_language")) {
            Locales.saveDefaultConfig(this, getConfig().getString("default_language") + ".yml");
        } else {
            Locales.saveDefaultConfig(this, "en_us.yml");
            Locales.saveDefaultConfig(this, "fr_fr.yml");
            Locales.saveDefaultConfig(this, "de_de.yml");
        }

        locales = new Locales(this, getConfig().getString("default_language"));

        xpmg = new XPManager(this);

        if (getConfig().getBoolean("check-updates")) {
            if (isOutdated("ZucF3Myf")) {
                outdated = true;
            }
        }

        getServer().getPluginManager().registerEvents(new JoinListener(this, outdated), this);
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

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null && getConfig().getBoolean("enable-placeholder-api")) {
            new PlaceholderApiHook(this).register();
        }

        int pluginId = 23375;
        Metrics metrics = new Metrics(this, pluginId);

        getLogger().info("jLevels finished loading!");

    }

    public boolean isOutdated(String modrinthID) {
        try {
            URL url = new URL("https://api.modrinth.com/v2/project/" + modrinthID + "/version");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();

            if (status > 299) {
                getLogger().warning("Error checking for updates");
                return true;
            }

            InputStreamReader reader = new InputStreamReader(con.getInputStream());
            Scanner scanner = new Scanner(reader);
            StringBuilder json = new StringBuilder();
            while (scanner.hasNext()) {
                json.append(scanner.nextLine());
            }

            scanner.close();
            con.disconnect();

            String latestVersion = json.toString().split("\"version_number\":\"")[1].split("\"")[0];

            if(!getDescription().getVersion().equalsIgnoreCase(latestVersion)) {
                return true;
            }

            return false;

        } catch (IOException e) {
            getLogger().warning("Error checking for updates");
            return true;
        }
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
