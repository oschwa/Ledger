package oschwa.ledger.commands;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
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

        if (!ledgerGroupRegistry.containsGroup((Player)commandSender)) {
            commandSender.sendMessage("You do not have a registered Ledger.");
            return false;
        }

        Player newPlayer = server.getPlayer(strings[0]);

        if (!ledgerGroupRegistry.getGroup((Player)commandSender).hasMember(newPlayer.getUniqueId())) {
            commandSender.sendMessage(newPlayer.getName() + " is not assigned to your Ledger.");
        }

        return ledgerGroupRegistry.getGroup((Player) commandSender)
                 .removeMember(newPlayer.getUniqueId());
    }
}
