package oschwa.ledger;

import org.bukkit.plugin.java.JavaPlugin;
import oschwa.ledger.commands.ManualCommand;
import oschwa.ledger.commands.NewCommand;
import oschwa.ledger.commands.ScrapCommand;
import oschwa.ledger.registries.LedgerGroupRegistry;

public class Ledger extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        LedgerGroupRegistry ledgerGroupRegistry = new LedgerGroupRegistry();
        this.getCommand("man").setExecutor(new ManualCommand());
        this.getCommand("new").setExecutor(new NewCommand(ledgerGroupRegistry));
        this.getCommand("scrap").setExecutor(new ScrapCommand(ledgerGroupRegistry));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
