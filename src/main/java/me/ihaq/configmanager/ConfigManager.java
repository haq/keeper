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

        // copying their default config if they have one
        plugin.saveDefaultConfig();
    }

    /**
     * The objects that contains fields that need to be loaded from the config.
     *
     * @param objects the objects you want to register
     * @return instance of this class so you can build
     */
    public ConfigManager register(Object... objects) {
        this.objects.addAll(Arrays.asList(objects));
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
     * @return instance of this class so you can build
     */
    public ConfigManager save() {
        objects.forEach(object -> Arrays.stream(object.getClass().getDeclaredFields()).forEach(field -> {

            if (!field.isAnnotationPresent(ConfigValue.class)) {
                return;
            }

            try {
                field.setAccessible(true);

                Object value = field.get(object);

                if (value instanceof String) {
                    value = ((String) value).replaceAll("" + ChatColor.COLOR_CHAR, "&");
                }

                plugin.getConfig().set(field.getAnnotation(ConfigValue.class).value(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }));
        plugin.saveConfig();
        return this;
    }

    /**
     * Reloads the config.
     *
     * @return instance of this class so you can build
     */
    public ConfigManager reload() {
        plugin.reloadConfig();
        load();
        return this;
    }


}
