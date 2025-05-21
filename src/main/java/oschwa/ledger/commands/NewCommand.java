package oschwa.ledger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import oschwa.ledger.registries.LedgerGroupRegistry;

public class NewCommand implements CommandExecutor {

    private final LedgerGroupRegistry ledgerGroupRegistry;

    public NewCommand(LedgerGroupRegistry ledgerGroupRegistry) {
        this.ledgerGroupRegistry = ledgerGroupRegistry;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) return false;

        ledgerGroupRegistry.addGroup((Player)commandSender);

        return true;
    }
}
