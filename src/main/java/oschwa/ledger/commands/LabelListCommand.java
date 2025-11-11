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

public class LabelListCommand implements CommandExecutor {

    private final LedgerRegistry ledgerRegistry;

    public LabelListCommand(LedgerRegistry ledgerRegistry) {
        this.ledgerRegistry = ledgerRegistry;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) {
            LedgerErrorMessage.PLAYER_ONLY.send(commandSender);
        }

        Player player = (Player) commandSender;

        Optional<Ledger> ledger =
                ledgerRegistry.getGroupByPlayer(player);

        if (ledger.isEmpty()) {
            LedgerErrorMessage.LEDGER_NOT_EXIST.send(player);
            return true;
        }

        player.sendMessage(ledger.get().showRegisteredLabels());

        return true;
    }
}
