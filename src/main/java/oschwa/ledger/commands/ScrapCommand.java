package oschwa.ledger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import oschwa.ledger.registries.LedgerGroupRegistry;

public class ScrapCommand implements CommandExecutor {

    private final LedgerGroupRegistry ledgerGroupRegistry;

    public ScrapCommand(LedgerGroupRegistry ledgerGroupRegistry) {
        this.ledgerGroupRegistry = ledgerGroupRegistry;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) return false;

        ledgerGroupRegistry.removeGroup((Player)commandSender);

        return true;
    }
}
