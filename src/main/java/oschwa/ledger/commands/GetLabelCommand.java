package oschwa.ledger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import oschwa.ledger.enums.LedgerConfigMessage;
import oschwa.ledger.enums.LedgerErrorMessage;
import oschwa.ledger.labels.Label;
import oschwa.ledger.player.Ledger;
import oschwa.ledger.registries.LedgerRegistry;

import java.util.Optional;

public class GetLabelCommand implements CommandExecutor {

    private final LedgerRegistry ledgerRegistry;

    public GetLabelCommand(LedgerRegistry ledgerRegistry) {
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

        Optional<Ledger> ledgerGroup = ledgerRegistry.getGroupByPlayer(player);

        if (ledgerGroup.isEmpty()) {
            LedgerErrorMessage.LEDGER_NOT_EXIST.send(player);
            return true;
        }

        if (strings.length != 1) {
            LedgerErrorMessage.MUST_PROVIDE_LABEL_ARG.send(player);
            return true;
        }

        String labelName = strings[0];

        Optional<Label> label = ledgerGroup.get().getLabel(labelName);

        if (label.isEmpty()) {
            LedgerErrorMessage.LABEL_DOES_NOT_EXIST.send(player);
        }

        player.getInventory().addItem(label.get().getLabelItem());

        LedgerConfigMessage.LABEL_GET_SUCCESSFUL.send(player);

        return true;
    }
}
