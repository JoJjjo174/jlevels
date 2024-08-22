package me.jojjjo147.jLevels;

import me.jojjjo147.jLevels.commands.LevelCommand;
import me.jojjjo147.jLevels.listeners.JoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class JLevels extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getCommand("level").setExecutor(new LevelCommand(this));

    }


}
