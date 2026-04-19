package at.jonathans.jlevels;

import at.jonathans.jlevels.commands.*;
import at.jonathans.jlevels.listeners.*;
import at.jonathans.jlevels.commands.*;
import at.jonathans.jlevels.files.LanguageFile;
import at.jonathans.jlevels.listeners.*;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import org.bstats.bukkit.Metrics;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public final class JLevels extends JavaPlugin {

    private static JLevels instance;

    private static final String MODRINTH_ID = "ZucF3Myf";
    private static final int BSTATS_ID = 23375;

    private XPManager xpmg;
    private static boolean outdated = false;
    private LanguageFile langConfig;
    private BukkitTask playtimeRunnable;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();
        ConfigUpdater.updateConfig(this);

        langConfig = new LanguageFile(getConfig().getString("language"));

        xpmg = new XPManager();

        if (getConfig().getBoolean("check-updates")) {
            outdated = isOutdated();
        }

        getServer().getPluginManager().registerEvents(new JoinListener(outdated), this);
        getServer().getPluginManager().registerEvents(new XpBottleListener(), this);
        getServer().getPluginManager().registerEvents(new GuiClickListener(), this);
        getServer().getPluginManager().registerEvents(new GuiDragListener(), this);

        if (getConfig().getInt("rewards.monster-kill") > 0) {
            getServer().getPluginManager().registerEvents(new MobKillListener(), this);
        }

        if (getConfig().getInt("rewards.achievement") > 0) {
            getServer().getPluginManager().registerEvents(new AchievementListener(), this);
        }

        if (getConfig().getInt("rewards.player-kill") > 0) {
            getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        }

        if(getConfig().getInt("rewards.playtime") > 0) {
            registerPlaytimeRunnable();
        }

        if (getConfig().getInt("rewards.sleep") > 0) {
            getServer().getPluginManager().registerEvents(new SleepListener(), this);
        }

        getCommand("level").setExecutor(new LevelCommand());
        getCommand("addxp").setExecutor(new AddXPCommand());
        getCommand("givexpbottle").setExecutor(new GiveBottleCommand());
        getCommand("jreload").setExecutor(new ReloadConfigCommand());
        getCommand("setlevel").setExecutor(new SetLevelCommand());

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null && getConfig().getBoolean("enable-placeholder-api")) {
            new PlaceholderApiHook().register();
        }

        Metrics metrics = new Metrics(this, BSTATS_ID);
        addMetrics(metrics);

        getLogger().info("jLevels finished loading!");

    }

    private void addMetrics(Metrics metrics) {

        metrics.addCustomChart(new SimplePie("language", () -> {
            return langConfig.getLanguage();
        }));

    }

    private void registerPlaytimeRunnable() {
        long period = getConfig().getInt("playtime-reward-time") * 20L;
        long delay = period / 2;

        playtimeRunnable = new BukkitRunnable() {
            @Override
            public void run() {

                for (Player player : Bukkit.getOnlinePlayers()) {
                    xpmg.addXP(player, getConfig().getInt("rewards.playtime"), getMessage("xpreason-playtime"));
                }

            }
        }.runTaskTimer(this, delay, period);
    }

    private boolean isOutdated() {
        try {
            URL url = new URL("https://api.modrinth.com/v2/project/" + MODRINTH_ID + "/version");
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

        String message = langConfig.get().getString(key);

        if (message == null) {
            getLogger().severe("Couldn't get message from lang file");
            return "";
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void reload() {
        reloadConfig();
        xpmg.reload();
        langConfig.reload();

        if (!getConfig().getString("language").equalsIgnoreCase(langConfig.getLanguage())) {
            langConfig = new LanguageFile(getConfig().getString("language"));
        }

        if (playtimeRunnable != null) {
            playtimeRunnable.cancel();
            registerPlaytimeRunnable();
        }
    }

    public static JLevels getInstance() {
        return instance;
    }
}
