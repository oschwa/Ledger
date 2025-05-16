package oschwa.ledger.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import oschwa.ledger.registries.LedgerGroupRegistry;

public class LedgerCommand implements CommandExecutor {

    private String[] manual;
    private LedgerGroupRegistry ledgerGroupRegistry;

    public LedgerCommand(LedgerGroupRegistry ledgerGroupRegistry) {
        this.ledgerGroupRegistry = ledgerGroupRegistry;
        manual = new String[] {
                "/ledger -> manual page",
                "/ledger new -> create a new Ledger",
                "/ledger scrap -> delete your existing Ledger",
                "/ledger members -> view names of player in your Ledger"
        };
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String @NotNull [] strings) {

        if (!(commandSender instanceof Player player)) {
            commandSender.sendPlainMessage("Only players can make a new Ledger.");
            return false;
        }

        if (!command.getName().equalsIgnoreCase("ledger")) {
            return false;
        }

        else {
            if (strings.length == 0) {
                commandSender.sendMessage(manual);
            } else if (strings[0].equalsIgnoreCase("new")) {
                ledgerGroupRegistry.addGroup(player);
                commandSender.sendMessage("New Ledger created");
            } else if (strings[0].equalsIgnoreCase("scrap")) {
                ledgerGroupRegistry.removeGroup(player);
                commandSender.sendMessage("Ledger scrapped");
            } else if (strings[0].equalsIgnoreCase("members")) {
                commandSender.sendMessage(ledgerGroupRegistry.getGroup(player).getMembersList());
            }
        }

        return true;
    }
}
