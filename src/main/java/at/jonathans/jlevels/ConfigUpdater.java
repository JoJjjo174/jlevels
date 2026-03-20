package at.jonathans.jlevels;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUpdater {

    final static private int newestVersion = 3;

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

                case 2:
                    config.set("playtime-reward-time", 3600);
                    config.set("rewards.playtime", 30);

                    config.set("config-version", 3);
                    plugin.saveConfig();
                    plugin.getLogger().info("Updated config from version 2 -> 3");
                    break;

                default:
                    plugin.getLogger().warning("Invalid Config version!");
                    return;

            }

            version = config.getInt("config-version");

        }

    }

}
