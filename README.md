[![license](https://img.shields.io/github/license/mashape/apistatus.svg) ](LICENSE)

# ConfigManager
A basic **config manager** for your Spigot plugins.

## Declaration
```java
public class TestPlugin extends JavaPlugin {

    @ConfigValue("messages.join")
    private String join = "Default join message";

}
```

## Usage
```java
public class TestPlugin extends JavaPlugin {

    @ConfigValue("messages.join")
    private String joinMessage = "Default join message";

    @ConfigValue("messages.quit")
    private String quitMessage = "Default quit message";

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this)
                            .register(this) // registering objects that contain ConfigValue fields
                            .load();  // loads all the values
    }

    @Override
    public void onDisable() {
        // saves the config values
        configManager.save();
    }

}
```

## Download

#### Maven
```xml
<repository>
    <id>ihaq-maven</id>
    <url>http://maven.ihaq.me/libs-maven/</url>
</repository>

<dependency>
    <groupId>me.ihaq</groupId>
    <artifactId>config-manager</artifactId>
    <version>1.3</version>
</dependency>
```

#### Gradle
```gradle
repositories {
    maven {
        url "http://maven.ihaq.me/libs-maven/"
    }
}

dependencies {
    compile 'me.ihaq:config-manager:1.3'
}
```
