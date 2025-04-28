package oschwa.ledger;

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
