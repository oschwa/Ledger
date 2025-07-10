package oschwa.ledger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import oschwa.ledger.exceptions.GroupExistsException;
import oschwa.ledger.registries.LedgerGroupRegistry;

public class NewCommand implements CommandExecutor {

    private final LedgerGroupRegistry ledgerGroupRegistry;

    public NewCommand(LedgerGroupRegistry ledgerGroupRegistry) {
        this.ledgerGroupRegistry = ledgerGroupRegistry;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) return false;

        try {
            ledgerGroupRegistry.addGroup((Player)commandSender);
            commandSender.sendMessage("New Ledger created!");
        } catch (GroupExistsException e) {
            commandSender.sendMessage(e.getMessage());
        }

        return true;
    }
}
