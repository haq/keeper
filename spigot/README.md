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

    @Override
    public void onEnable() {
        new Keeper(this)
                .register(this) // registering objects that contain ConfigValue fields
                .load();  // loads all the values
    }
    
}
```
