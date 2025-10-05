package oschwa.ledger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import oschwa.ledger.enums.LedgerConfigMessage;
import oschwa.ledger.enums.LedgerErrorMessage;
import oschwa.ledger.player.LedgerGroup;
import oschwa.ledger.registries.LedgerGroupRegistry;

import java.util.Optional;

public class LeaveCommand implements CommandExecutor {

    private final LedgerGroupRegistry ledgerGroupRegistry;

    public LeaveCommand(LedgerGroupRegistry ledgerGroupRegistry) {
        this.ledgerGroupRegistry = ledgerGroupRegistry;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) {
            LedgerErrorMessage.PLAYER_ONLY.send(commandSender);
            return true;
        }

        Player player = (Player) commandSender;

        Optional<LedgerGroup> ledgerGroup =
                ledgerGroupRegistry.getGroupByPlayer(player);

        if (ledgerGroup.isEmpty()) {
            LedgerErrorMessage.NOT_IN_LEDGER.send(player);
            return true;
        }

        ledgerGroup.get().removeMember(player.getUniqueId());
        LedgerConfigMessage.LEFT_LEDGER.send(player,
                ledgerGroup.get().getOwner().getName());
        return true;
    }
}
