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

public class ScrapCommand implements CommandExecutor {

    private final LedgerRegistry ledgerRegistry;

    public ScrapCommand(LedgerRegistry ledgerRegistry) {
        this.ledgerRegistry = ledgerRegistry;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) {
            LedgerErrorMessage.PLAYER_ONLY.send(commandSender);
            return true;
        }

        Player player = (Player) commandSender;

        Optional<Ledger> ledgerGroup =
                ledgerRegistry.get(player);

        if (ledgerGroup.isEmpty()) {
            LedgerErrorMessage.LEDGER_NOT_EXIST.send(player);
        }

        /*
        ledgerGroup.get().getMembers().values().forEach(member ->
                LedgerConfigMessage.LEDGER_SCRAP_NOTIF.send(member, player.getName()));
         */

        ledgerRegistry.remove(player);

        LedgerConfigMessage.LEDGER_SCRAPPED.send(player);

        return true;
    }
}
