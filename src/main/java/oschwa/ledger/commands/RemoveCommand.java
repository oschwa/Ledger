package oschwa.ledger.commands;

import org.bukkit.Bukkit;
import org.bukkit.Server;
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

public class RemoveCommand implements CommandExecutor {

    private final Server server;
    private final LedgerRegistry ledgerRegistry;

    public RemoveCommand(Server server, LedgerRegistry ledgerRegistry) {
        this.server = server;
        this.ledgerRegistry = ledgerRegistry;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) return false;

        Player player = (Player)commandSender;

        Optional<Ledger> ledgerGroup =
                ledgerRegistry.get(player);

        if (ledgerGroup.isEmpty()) {
            LedgerErrorMessage.LEDGER_NOT_EXIST.send(player);
            return false;
        }

        Optional<Player> otherPlayer =
                Optional.ofNullable(Bukkit.getPlayer(strings[0]));

        if (otherPlayer.isEmpty()) {
            LedgerErrorMessage.NO_PLAYER.send(player, strings[0]);
            return true;
        }

        if (!ledgerGroup.get().hasMember(otherPlayer.get().getUniqueId())) {
            LedgerErrorMessage.MEMBER_NOT_FOUND.send(player);
        }

        ledgerGroup.get().removeMember(otherPlayer.get().getUniqueId());

        LedgerConfigMessage.MEMBER_REMOVED.send(player);

        return true;
    }
}
