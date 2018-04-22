# ConfigManager

A simple config manager for your Bukkit/Spigot plugins. You may want to make a default config for your plugin to start off.

## Declaration

```java
public class TestPlugin extends JavaPlugin {

    @ConfigValue("messages.join")
    private String join = "JOIN!";

}
```

## Usage
```java
public class TestPlugin extends JavaPlugin {

    @ConfigValue("messages.join")
    private String joinMessage = "JOIN!";

    @ConfigValue("messages.quit")
    private String quitMessage = "QUIT!";

    private ConfigManager configManager = new ConfigManager(this); // send in JavaPlugin so it can get yor config

    @Override
    public void onEnable() {
        configManager
            .register(this) // regestring objects that contain ConfigValue fields
            .load(); // loads all the values
    }

    @Override
    public void onDisable() {
        // saving the config values, if this is called before load it will save their set values
        configManager.save();
    }

}
```

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details