package me.ihaq.keeper;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

class ConfigurationFile {

    private final File file;
    private final FileConfiguration configuration;

    public ConfigurationFile(JavaPlugin plugin, String name) {
        configuration = new YamlConfiguration();
        file = new File(plugin.getDataFolder(), name);

        // creating the config file
        if (!file.exists()) {
            System.out.println("Making file " + name);

            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void load() {
        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }
}
