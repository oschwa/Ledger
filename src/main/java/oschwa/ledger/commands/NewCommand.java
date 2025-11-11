package oschwa.ledger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import oschwa.ledger.enums.LedgerConfigMessage;
import oschwa.ledger.enums.LedgerErrorMessage;
import oschwa.ledger.player.Ledger;
import oschwa.ledger.registries.LedgerRegistry;

public class NewCommand implements CommandExecutor {

    private final LedgerRegistry ledgerRegistry;

    public NewCommand(LedgerRegistry ledgerRegistry) {
        this.ledgerRegistry = ledgerRegistry;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) {
            LedgerErrorMessage.PLAYER_ONLY.send(commandSender, command.getName());
            return true;
        }

        Player player = (Player) commandSender;

        if (ledgerRegistry.contains(player)) {
            LedgerErrorMessage.LEDGER_EXISTS.send(player);
            return true;
        }

        ledgerRegistry.add(player, new Ledger(player));

        LedgerConfigMessage.NEW_LEDGER.send(player);

        return true;
    }
}
