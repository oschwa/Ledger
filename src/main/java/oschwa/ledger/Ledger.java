package oschwa.ledger;

import org.bukkit.plugin.java.JavaPlugin;
import oschwa.ledger.commands.ManualCommand;
import oschwa.ledger.registries.LedgerGroupRegistry;

public class Ledger extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        LedgerGroupRegistry ledgerGroupRegistry = new LedgerGroupRegistry();
        this.getCommand("man").setExecutor(new ManualCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
