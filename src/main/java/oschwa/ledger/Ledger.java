package oschwa.ledger;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import oschwa.ledger.commands.LedgerCommand;

public class Ledger extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("ledger").setExecutor(new LedgerCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
