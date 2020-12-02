package me.ihaq.keeper;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

class ConfigFilee {

    private final File file;
    private final FileConfiguration configuration;

    public ConfigFilee(JavaPlugin plugin, String name) {
        file = new File(plugin.getDataFolder(), name);

        // creating the config file
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(name, false);
        }

        configuration = new YamlConfiguration();
        load();
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
