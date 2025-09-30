package oschwa.ledger.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import oschwa.ledger.Ledger;
import oschwa.ledger.enums.LedgerConfigMessage;
import oschwa.ledger.enums.LedgerErrorMessage;
import oschwa.ledger.exceptions.GroupExistsException;
import oschwa.ledger.registries.LedgerGroupRegistry;

public class NewCommand implements CommandExecutor {

    private final LedgerGroupRegistry ledgerGroupRegistry;

    public NewCommand(LedgerGroupRegistry ledgerGroupRegistry) {
        this.ledgerGroupRegistry = ledgerGroupRegistry;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) {
            LedgerErrorMessage.PLAYER_ONLY.send(commandSender, command.getName());
            return true;
        }

        Player player = (Player) commandSender;

        if (ledgerGroupRegistry.containsGroup(player)) {
            LedgerErrorMessage.LEDGER_EXISTS.send(player);
            return true;
        }

        ledgerGroupRegistry.addGroup(player);

        LedgerConfigMessage.NEW_LEDGER.send(player);

        return true;
    }
}
