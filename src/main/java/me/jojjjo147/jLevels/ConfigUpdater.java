package me.jojjjo147.jLevels;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUpdater {

    final static private int newestVersion = 2;

    public static void updateConfig(JLevels plugin) {
        FileConfiguration config = plugin.getConfig();

        int version = config.getInt("config-version");

        while (version < newestVersion) {

            switch (version) {

                case 1:
                    // ---------------------------------- Update Language ----------------------------------
                    String language = config.getString("default_language");
                    config.set("language", language);
                    // --------------------------------- Remove old fields ---------------------------------
                    config.set("default_language", null);
                    config.set("uniform_language", null);
                    // --------------------------------- Create new fields ---------------------------------
                    config.set("max-level", -1);
                    config.set("default-level-colour", "f");
                    // -------------------------------------------------------------------------------------

                    config.set("config-version", 2);
                    plugin.saveConfig();
                    plugin.getLogger().info("Updated config from version 1 -> 2");
                    break;

                default:
                    plugin.getLogger().warning("Invalid Config version!");
                    return;

            }

            version = config.getInt("config-version");

        }

    }

}
