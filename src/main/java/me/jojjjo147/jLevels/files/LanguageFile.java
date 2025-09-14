package me.jojjjo147.jLevels.files;

import me.jojjjo147.jLevels.JLevels;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class LanguageFile {

    private final JLevels plugin;
    private final String name;
    private  File file;
    private  FileConfiguration languageFile;

    public LanguageFile(JLevels plugin, String language) {

        this.plugin = plugin;
        this.name = language;

        setup();

    }

    public void setup() {

        File folder = new File(plugin.getDataFolder(), "languages");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        file = new File(folder, name);

        if (!file.exists()) {
            try (InputStream in = plugin.getResource("languages/" + name)) {
                if (in != null) {
                    Files.copy(in, file.toPath());

                } else {
                    file.createNewFile();

                }

            } catch (IOException e) {
                plugin.getLogger().warning(String.format("Failed to load %s file", name));
            }
        }

        reload();
    }

    public FileConfiguration get() {
        return languageFile;
    }

    public void save() {
        try {
            languageFile.save(file);
        } catch (IOException e) {

        }
    }

    public void reload() {
        languageFile = YamlConfiguration.loadConfiguration(file);
    }

    public String getLanguage() {
        return name;
    }

}
