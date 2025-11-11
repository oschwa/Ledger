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

public class NewLabelCommand implements CommandExecutor {

    private final LedgerRegistry ledgerRegistry;

    public NewLabelCommand(LedgerRegistry ledgerRegistry) {
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

        Optional<Ledger> ledger = ledgerRegistry.get(player);

        if (ledger.isEmpty()) {
            LedgerErrorMessage.MUST_BE_OWNER_FOR_LABEL.send(player);
            return true;
        }

        if (strings.length != 1) {
            LedgerErrorMessage.INVALID_LABEL_ARG.send(player);
            return true;
        }

        String labelName = strings[0];

        Label newLabel = Label.builder(labelName);

        ledger.ifPresent(group -> group.addLabel(newLabel));

        player.getInventory().addItem(newLabel.getLabelItem());

        LedgerConfigMessage.NEW_LABEL.send(player, "'" +
                newLabel.getName() + "'");

        return true;
    }
}
