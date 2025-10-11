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

public class ScrapCommand implements CommandExecutor {

    private final LedgerGroupRegistry ledgerGroupRegistry;

    public ScrapCommand(LedgerGroupRegistry ledgerGroupRegistry) {
        this.ledgerGroupRegistry = ledgerGroupRegistry;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) {
            LedgerErrorMessage.PLAYER_ONLY.send(commandSender);
            return true;
        }

        Player player = (Player) commandSender;

        Optional<LedgerGroup> ledgerGroup =
                ledgerGroupRegistry.getGroupByOwner(player);

        if (ledgerGroup.isEmpty()) {
            LedgerErrorMessage.LEDGER_NOT_EXIST.send(player);
        }

        for (Player member : ledgerGroup.get().getMembers().values()) {
            LedgerConfigMessage.LEDGER_SCRAP_NOTIF.send(member, player.getName());
        }

        ledgerGroupRegistry.removeGroup(player);

        LedgerConfigMessage.LEDGER_SCRAPPED.send(player);

        return true;
    }
}
