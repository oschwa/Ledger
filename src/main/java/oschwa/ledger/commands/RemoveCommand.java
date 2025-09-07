package oschwa.ledger.commands;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import oschwa.ledger.enums.LedgerErrorMessage;
import oschwa.ledger.exceptions.MemberDoesNotExistException;
import oschwa.ledger.registries.LedgerGroupRegistry;

public class RemoveCommand implements CommandExecutor {

    private final Server server;
    private final LedgerGroupRegistry ledgerGroupRegistry;

    public RemoveCommand(Server server, LedgerGroupRegistry ledgerGroupRegistry) {
        this.server = server;
        this.ledgerGroupRegistry = ledgerGroupRegistry;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) return false;

        Player player = (Player)commandSender;

        if (!ledgerGroupRegistry.containsGroup((Player)commandSender)) {
            LedgerErrorMessage.LEDGER_NOT_EXIST.send(player);
            return false;
        }

        Player otherPlayer = server.getPlayer(strings[0]);

        if (!ledgerGroupRegistry.getGroup((Player) commandSender)
                .removeMember(otherPlayer.getUniqueId())) {
            LedgerErrorMessage.MEMBER_NOT_FOUND.send(player, otherPlayer.getName());
            return false;
        }

        return true;
    }
}
