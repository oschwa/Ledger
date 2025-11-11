package oschwa.ledger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import oschwa.ledger.enums.LedgerErrorMessage;
import oschwa.ledger.player.Ledger;
import oschwa.ledger.registries.LedgerRegistry;

import java.util.Optional;

public class ListCommand implements CommandExecutor {

    private final LedgerRegistry ledgerRegistry;

    public ListCommand(LedgerRegistry ledgerRegistry) {
        this.ledgerRegistry = ledgerRegistry;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) {
            LedgerErrorMessage.PLAYER_ONLY.send(commandSender);
            return true;
        }

        Player player = (Player) commandSender;

        Optional<Ledger> ledgerGroup =
                ledgerRegistry.getGroupByPlayer(player);

        if (ledgerGroup.isEmpty()) {
            LedgerErrorMessage.LEDGER_NOT_EXIST.send(player);
            return true;
        }

        player.sendMessage(ledgerGroup.get().toString());

        return true;
    }
}
