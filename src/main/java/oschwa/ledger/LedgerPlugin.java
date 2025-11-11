package oschwa.ledger;

import org.bukkit.plugin.java.JavaPlugin;
import oschwa.ledger.commands.*;
import oschwa.ledger.listeners.LabelListener;
import oschwa.ledger.registries.LedgerRegistry;

public class LedgerPlugin extends JavaPlugin {

    private static LedgerPlugin plugin;
    private final LedgerRegistry ledgerRegistry = new LedgerRegistry();

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        this.getCommand("man").setExecutor(new ManualCommand());
        this.getCommand("new").setExecutor(new NewCommand(ledgerRegistry));
        this.getCommand("scrap").setExecutor(new ScrapCommand(ledgerRegistry));
        this.getCommand("add").setExecutor(new AddCommand(ledgerRegistry));
        this.getCommand("list").setExecutor(new ListCommand(ledgerRegistry));
        this.getCommand("leave").setExecutor(new LeaveCommand(ledgerRegistry));
        this.getCommand("new_label").setExecutor(new NewLabelCommand(ledgerRegistry));

        getServer().getPluginManager().registerEvents(new LabelListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static LedgerPlugin getPlugin() {
        return plugin;
    }
}
