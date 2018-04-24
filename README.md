[ ![Download](https://api.bintray.com/packages/ihaq/maven/config-manager/images/download.svg) ](https://bintray.com/ihaq/maven/config-manager/_latestVersion)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg) ](LICENSE)

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

## Download
[ ![Download](https://api.bintray.com/packages/ihaq/maven/config-manager/images/download.svg) ](https://bintray.com/ihaq/maven/config-manager/_latestVersion)

#### Maven

Replace VERSION with the verion above.
```xml
<repository>
    <id>jcenter</id>
    <name>jcenter-bintray</name>
    <url>http://jcenter.bintray.com</url>
</repository>

<dependency>
    <groupId>me.ihaq.configmanager</groupId>
    <artifactId>ConfigManager</artifactId>
    <version>VERSION</version>
</dependency>
```

#### Gradle
```gradle
dependencies {
    compile 'me.ihaq.configmanager:ConfigManager:VERSION'
}

repositories {
    jcenter()
}
```
