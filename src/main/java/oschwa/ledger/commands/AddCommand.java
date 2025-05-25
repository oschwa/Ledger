package oschwa.ledger.commands;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import oschwa.ledger.registries.LedgerGroupRegistry;

public class AddCommand implements CommandExecutor {

    private Server server;
    private LedgerGroupRegistry ledgerGroupRegistry;

    public AddCommand(Server server, LedgerGroupRegistry ledgerGroupRegistry) {
        this.server = server;
        this.ledgerGroupRegistry = ledgerGroupRegistry;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) return false;

        Player newPlayer = server.getPlayer(strings[0]);

        if (newPlayer == null) return false;

        ledgerGroupRegistry.getGroup((Player)commandSender).addMember(newPlayer);

        commandSender.sendMessage(newPlayer.getName() + " has been added to your Ledger.");

        return true;
    }
}
