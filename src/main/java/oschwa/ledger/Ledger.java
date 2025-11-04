package oschwa.ledger;

import org.bukkit.plugin.java.JavaPlugin;
import oschwa.ledger.commands.*;
import oschwa.ledger.registries.LedgerGroupRegistry;

public class Ledger extends JavaPlugin {

    private static Ledger plugin;
    private final LedgerGroupRegistry ledgerGroupRegistry = new LedgerGroupRegistry();

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        this.getCommand("man").setExecutor(new ManualCommand());
        this.getCommand("new").setExecutor(new NewCommand(ledgerGroupRegistry));
        this.getCommand("scrap").setExecutor(new ScrapCommand(ledgerGroupRegistry));
        this.getCommand("add").setExecutor(new AddCommand(ledgerGroupRegistry));
        this.getCommand("list").setExecutor(new ListCommand(ledgerGroupRegistry));
        this.getCommand("leave").setExecutor(new LeaveCommand(ledgerGroupRegistry));
        this.getCommand("new_label").setExecutor(new NewLabelCommand(ledgerGroupRegistry));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Ledger getPlugin() {
        return plugin;
    }
}
