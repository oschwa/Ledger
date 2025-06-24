package oschwa.ledger.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import oschwa.ledger.exceptions.GroupDoesNotExistException;
import oschwa.ledger.registries.LedgerGroupRegistry;

public class ScrapCommand implements CommandExecutor {

    private final LedgerGroupRegistry ledgerGroupRegistry;

    public ScrapCommand(LedgerGroupRegistry ledgerGroupRegistry) {
        this.ledgerGroupRegistry = ledgerGroupRegistry;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) return false;

        try {
            ledgerGroupRegistry.removeGroup((Player)commandSender);
            commandSender.sendMessage("Ledger scrapped!");
        } catch (GroupDoesNotExistException e) {
            commandSender.sendMessage(e.getMessage());
            return false;
        }

        return true;
    }
}
