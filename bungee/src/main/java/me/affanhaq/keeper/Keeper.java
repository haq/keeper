package me.affanhaq.keeper;

import me.affanhaq.keeper.data.ConfigValue;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Keeper {

    private Plugin plugin;
    private Set<Object> objects;

    private File configFile;
    private ConfigurationProvider configProvider;
    private Configuration config;

    /**
     * @param plugin instance of your plugin
     */
    public Keeper(Plugin plugin) {
        this.plugin = plugin;
        objects = new HashSet<>();

        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                plugin.getDataFolder().mkdirs();
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        configProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
        loadConfigFile();
    }

    /**
     * The objects that contains fields that need to be loaded from the config.
     *
     * @param objects the objects you want to register
     * @return instance of this class so you can build
     */
    public Keeper register(Object... objects) {
        this.objects.addAll(Arrays.asList(objects));

        // adding the default config values and saving the config
        Arrays.stream(objects).forEach(o -> save(o, false));
        saveConfigFile();
        return this;
    }

    /**
     * @return instance of this class so you can build
     */
    public Keeper load() {
        objects.forEach(object -> Arrays.stream(object.getClass().getDeclaredFields()).forEach(field -> {

            if (!field.isAnnotationPresent(ConfigValue.class)) {
                return;
            }

            Object value = config.get(field.getAnnotation(ConfigValue.class).value());

            if (value == null) {
                return;
            }

            if (value instanceof String) {
                value = ChatColor.translateAlternateColorCodes('&', (String) value);
            }

            try {
                field.setAccessible(true);
                field.set(object, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }));
        return this;
    }

    /**
     * Saves the config with updated values.
     *
     * @return instance of this class so you can build
     */
    public Keeper save() {
        objects.forEach(o -> save(o, true));
        saveConfigFile();
        return this;
    }

    /**
     * Reloads the config.
     *
     * @return instance of this class so you can build
     */
    public Keeper reload() {
        save();
        loadConfigFile();
        load();
        return this;
    }

    private void save(Object object, boolean override) {
        Arrays.stream(object.getClass().getDeclaredFields()).forEach(field -> {

            if (!field.isAnnotationPresent(ConfigValue.class)) {
                return;
            }

            try {
                field.setAccessible(true);

                String path = field.getAnnotation(ConfigValue.class).value();
                Object value = field.get(object);

                if (value instanceof String) {
                    value = ((String) value).replaceAll("" + ChatColor.COLOR_CHAR, "&");
                }

                if (override) {
                    config.set(path, value);
                } else if (config.get(path) == null) {
                    config.set(path, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private void saveConfigFile() {
        try {
            configProvider.save(config, configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConfigFile() {
        try {
            config = configProvider.load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}