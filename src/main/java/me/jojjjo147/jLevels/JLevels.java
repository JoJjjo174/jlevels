package me.jojjjo147.jLevels;

import me.jojjjo147.jLevels.commands.LevelCommand;
import me.jojjjo147.jLevels.listeners.JoinListener;
import me.jojjjo147.jLevels.listeners.MobKillListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class JLevels extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new MobKillListener(this, new XPManager(this)), this);

        getCommand("level").setExecutor(new LevelCommand(this));

    }


}
