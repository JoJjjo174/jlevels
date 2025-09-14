package me.jojjjo147.jLevels;

import me.jojjjo147.jLevels.commands.AddXPCommand;
import me.jojjjo147.jLevels.commands.GiveBottleCommand;
import me.jojjjo147.jLevels.commands.LevelCommand;
import me.jojjjo147.jLevels.commands.ReloadConfigCommand;
import me.jojjjo147.jLevels.files.LanguageFile;
import me.jojjjo147.jLevels.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import org.bstats.bukkit.Metrics;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public final class JLevels extends JavaPlugin {

    private XPManager xpmg;
    private static boolean outdated = false;
    private LanguageFile langConfig;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        langConfig = new LanguageFile(this, getConfig().getString("language"));

        xpmg = new XPManager(this);

        if (getConfig().getBoolean("check-updates")) {
            outdated = isOutdated("ZucF3Myf");
        }

        getServer().getPluginManager().registerEvents(new JoinListener(this, outdated), this);
        getServer().getPluginManager().registerEvents(new XpBottleListener(this), this);

        if (getConfig().getInt("rewards.monster-kill") > 0) {
            getServer().getPluginManager().registerEvents(new MobKillListener(this), this);
        }

        if (getConfig().getInt("rewards.achievement") > 0) {
            getServer().getPluginManager().registerEvents(new AchievementListener(this), this);
        }

        if (getConfig().getInt("rewards.player-kill") > 0) {
            getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        }

        getCommand("level").setExecutor(new LevelCommand(this));
        getCommand("addxp").setExecutor(new AddXPCommand(this, xpmg));
        getCommand("givexpbottle").setExecutor(new GiveBottleCommand(this));
        getCommand("jreload").setExecutor(new ReloadConfigCommand(this));

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

    public XPManager getXpManager() {
        return xpmg;
    }

    public String getMessage(String key) {
        return langConfig.get().getString(key);
    }

    public LanguageFile getLanguageConfig() {
        return langConfig;
    }

    public void reloadAllConfigurations() {
        reloadConfig();
        langConfig.reload();

        if (!getConfig().getString("language").equalsIgnoreCase(langConfig.getLanguage())) {
            langConfig = new LanguageFile(this, getConfig().getString("language"));
        }
    }
}
