package me.ihaq.configmanager;

import me.ihaq.configmanager.data.ConfigValue;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ConfigManager {

    private JavaPlugin plugin;
    private Set<Object> objects;

    /**
     * @param plugin instance of your plugin
     */
    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        objects = new HashSet<>();
    }

    /**
     * The objects that contains fields that need to be loaded from the config.
     *
     * @param objects the objects you want to register
     * @return instance of this class so you can build
     */
    public ConfigManager register(Object... objects) {
        this.objects.addAll(Arrays.asList(objects));

        // adding the default config values and saving the config
        Arrays.stream(objects).forEach(o -> save(o, false));
        plugin.saveConfig();

        return this;
    }

    /**
     * The objects you no longer want to have their config fields loaded/saved.
     *
     * @param objects the objects you want to remove from loading/saving
     * @return instance of this class so you can build
     */
    public ConfigManager unregister(Object... objects) {
        this.objects.removeAll(Arrays.asList(objects));
        return this;
    }

    /**
     * @return instance of this class so you can build
     */
    public ConfigManager load() {
        objects.forEach(object -> Arrays.stream(object.getClass().getDeclaredFields()).forEach(field -> {

            if (!field.isAnnotationPresent(ConfigValue.class)) {
                return;
            }

            Object value = plugin.getConfig().get(field.getAnnotation(ConfigValue.class).value());

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
    public ConfigManager save() {
        objects.forEach(o -> save(o, true));
        plugin.saveConfig();
        return this;
    }

    /**
     * Reloads the config.
     *
     * @return instance of this class so you can build
     */
    public ConfigManager reload() {
        save();
        plugin.reloadConfig();
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
                    plugin.getConfig().set(path, value);
                } else if (plugin.getConfig().get(path) == null) {
                    plugin.getConfig().set(path, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

}