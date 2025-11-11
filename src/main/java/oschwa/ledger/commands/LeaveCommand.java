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

import java.util.Optional;

public class LeaveCommand implements CommandExecutor {

    private final LedgerRegistry ledgerRegistry;

    public LeaveCommand(LedgerRegistry ledgerRegistry) {
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
            LedgerErrorMessage.NOT_IN_LEDGER.send(player);
            return true;
        }

        if (ledgerGroup.get().getOwner().equals(player)) {
            LedgerErrorMessage.OWNER_CANNOT_LEAVE.send(player);
            return true;
        }

        ledgerGroup.get().removeMember(player.getUniqueId());
        LedgerConfigMessage.LEFT_LEDGER.send(player,
                ledgerGroup.get().getOwner().getName());
        return true;
    }
}
