## Declaration

```java
import me.affanhaq.keeper.data.ConfigFile;
import me.affanhaq.keeper.data.ConfigValue;

/**
 * Every object that contains fields that you want to be load must be annotated.
 */
@ConfigFile("config.yml")
public class TestPlugin extends Plugin {

    /**
     * Every field that you want to load must be annotated.
     */
    @ConfigValue("messages.join")
    private String join = "Default join message";

}
```

## Usage

```java
import me.affanhaq.keeper.Keeper;
import me.affanhaq.keeper.data.ConfigFile;
import me.affanhaq.keeper.data.ConfigValue;
import net.md_5.bungee.api.plugin.Plugin;

@ConfigFile("config.yml")
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
