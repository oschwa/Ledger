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

        if (strings.length == 0) commandSender.sendMessage(manual);
        else if (strings[0].equalsIgnoreCase("new"))  addLedgerGroup(player);
        else if (strings[0].equalsIgnoreCase("scrap")) scrapLedgerGroup(player);
        else if (strings[0].equalsIgnoreCase("members")) getLedgerMembers(player);
        else if (strings[0].equalsIgnoreCase("leave")) leavePlayer(player);

        return true;
    }

    private void leavePlayer(Player player) {
        Player owner = ledgerGroupRegistry.getOwner(player.getUniqueId());
        if (owner == null) player.sendMessage("You do not belong to a registered Ledger");
        else {
            ledgerGroupRegistry.getGroup(owner).removeMember(player.getUniqueId());
        }
    }

    private void addLedgerGroup(Player player) {
        ledgerGroupRegistry.addGroup(player);
        player.sendMessage("New Ledger created");
    }

    private void scrapLedgerGroup(Player player) {
        ledgerGroupRegistry.removeGroup(player);
        player.sendMessage("Ledger scrapped");
    }

    private void getLedgerMembers(Player player) {
        player.sendMessage(ledgerGroupRegistry.getGroup(player).getMembersList());
    }
}
