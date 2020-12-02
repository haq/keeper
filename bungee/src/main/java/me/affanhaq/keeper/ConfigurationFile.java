package me.affanhaq.keeper;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

class ConfigurationFile {

    private final File file;
    private Configuration configuration;
    private final ConfigurationProvider configurationProvider;

    public ConfigurationFile(@NotNull File dataFolder, @NotNull String name) {
        configurationProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
        file = new File(dataFolder, name);

        // creating the config file
        if (!file.exists()) {
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
            configuration = configurationProvider.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            configurationProvider.save(configuration, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
