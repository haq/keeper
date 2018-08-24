## Declaration
```java
public class TestPlugin extends Plugin {

    @ConfigValue("messages.join")
    private String join = "Default join message";

}
```

## Usage
```java
public class TestPlugin extends Plugin {

    @ConfigValue("messages.join")
    private String joinMessage = "Default join message";

    @ConfigValue("messages.quit")
    private String quitMessage = "Default quit message";

    @Override
    public void onEnable() {
        new Keeper(this)
                .register(this) // registering objects that contain ConfigValue fields
                .load();  // loads all the values
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
    <groupId>me.ihaq.keeper</groupId>
    <artifactId>bungee</artifactId>
    <version>1.0</version>
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
    compile 'me.ihaq.keeper:bungee:1.0'
}
```