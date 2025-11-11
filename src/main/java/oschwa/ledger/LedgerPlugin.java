package oschwa.ledger;

import org.bukkit.plugin.java.JavaPlugin;
import oschwa.ledger.commands.*;
import oschwa.ledger.listeners.LabelListener;
import oschwa.ledger.registries.ChestRegistry;
import oschwa.ledger.registries.LedgerRegistry;

public class LedgerPlugin extends JavaPlugin {

    private static LedgerPlugin plugin;
    private final LedgerRegistry ledgerRegistry = new LedgerRegistry();
    private final ChestRegistry chestRegistry = new ChestRegistry();

    @Override
    public void onEnable() {
        plugin = this;
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
    }

    public static LedgerPlugin getPlugin() {
        return plugin;
    }

    public void registerListeners() {
        getServer().getPluginManager()
                .registerEvents(new LabelListener(ledgerRegistry, chestRegistry),
                        this);
    }

    public void registerCommands() {
        this.getCommand("man").setExecutor(new ManualCommand());
        this.getCommand("new").setExecutor(new NewCommand(ledgerRegistry));
        this.getCommand("scrap").setExecutor(new ScrapCommand(ledgerRegistry));
        this.getCommand("add").setExecutor(new AddCommand(ledgerRegistry));
        this.getCommand("list").setExecutor(new ListCommand(ledgerRegistry));
        this.getCommand("leave").setExecutor(new LeaveCommand(ledgerRegistry));
        this.getCommand("new_label").setExecutor(new NewLabelCommand(ledgerRegistry));
        this.getCommand("label_list").setExecutor(new LabelListCommand(ledgerRegistry));
    }
}
