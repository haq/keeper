package me.ihaq.configmanager;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class ConfigManager {

    private JavaPlugin plugin;
    private Set<Object> objects;

    /**
     * @param plugin instance of your plugin
     */
    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        objects = new LinkedHashSet<>();

        // copying their default config if they have one
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
    }

    /**
     * @param object the object to be registered
     */
    private void register(Object object) {
        objects.add(object);
    }

    /**
     * @param object the object to be unregistered
     */
    private void unregister(Object object) {
        objects.remove(object);
    }

    /**
     * The objects that contains fields that need to be loaded from the config.
     *
     * @param objects the objects you want to register
     * @return instance of this class so you can build
     */
    public ConfigManager register(Object... objects) {
        Arrays.stream(objects).forEach(this::register);
        return this;
    }

    /**
     * The objects you no longer want to have their config fields loaded/saved.
     *
     * @param objects the objects you want to remove from loading/saving
     * @return instance of this class so you can build
     */
    public ConfigManager unregister(Object... objects) {
        Arrays.stream(objects).forEach(this::unregister);
        return this;
    }

    /**
     * @return instance of this class so you can build
     */
    public ConfigManager load() {
        objects.forEach(object -> Arrays.stream(object.getClass().getDeclaredFields()).forEach(field -> {

            if (!field.isAnnotationPresent(ConfigValue.class))
                return;

            String path = field.getAnnotation(ConfigValue.class).value();

            Object value = plugin.getConfig().get(path);

            if (value instanceof String)
                value = ChatColor.translateAlternateColorCodes('&', (String) value);

            boolean accessible = field.isAccessible();
            try {
                if (!accessible)
                    field.setAccessible(true);

                field.set(object, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                if (!accessible)
                    field.setAccessible(false);
            }


        }));
        return this;
    }

    /**
     * @return instance of this class so you can build
     */
    public ConfigManager save() {
        objects.forEach(object -> Arrays.stream(object.getClass().getDeclaredFields()).forEach(field -> {

            if (!field.isAnnotationPresent(ConfigValue.class))
                return;

            boolean accessible = field.isAccessible();
            try {
                if (!accessible)
                    field.setAccessible(true);

                plugin.getConfig().set(field.getAnnotation(ConfigValue.class).value(), field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                if (!accessible)
                    field.setAccessible(false);
            }

        }));
        return this;
    }


}
