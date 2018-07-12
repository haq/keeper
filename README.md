[![license](https://img.shields.io/github/license/mashape/apistatus.svg) ](LICENSE)

# ConfigManager
A simple config manager for your Spigot plugins. You may want to make a default config for your plugin to start off.

## Declaration
```java
public class TestPlugin extends JavaPlugin {

    @ConfigValue("messages.join")
    private String join;

}
```

## Usage
```java
public class TestPlugin extends JavaPlugin {

    @ConfigValue("messages.join")
    private String joinMessage;

    @ConfigValue("messages.quit")
    private String quitMessage;

    private ConfigManager configManager = new ConfigManager(this); // send in JavaPlugin so it can get yor config

    @Override
    public void onEnable() {
        configManager
            .register(this) // registering objects that contain ConfigValue fields
            .load(); // loads all the values
    }

    @Override
    public void onDisable() {
        // saving the config values, if this is called before load it will save their set values
        configManager.save();
    }

}
```

## Download

#### Maven
```xml
<repository>
    <id>ihaq-maven</id>
    <url>http://maven.ihaq.me/artifactory/libs-maven/</url>
</repository>

<dependency>
    <groupId>me.ihaq</groupId>
    <artifactId>config-manager</artifactId>
    <version>1.2.3</version>
</dependency>
```

#### Gradle
```gradle
repositories {
    maven {
        url "http://maven.ihaq.me/artifactory/libs-maven/"
    }
}

dependencies {
    compile 'me.ihaq:config-manager:1.2.3'
}
```