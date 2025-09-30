package oschwa.ledger.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import oschwa.ledger.enums.LedgerConfigMessage;
import oschwa.ledger.enums.LedgerErrorMessage;
import oschwa.ledger.exceptions.GroupDoesNotExistException;
import oschwa.ledger.registries.LedgerGroupRegistry;

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

        if (!ledgerGroupRegistry.containsGroup(player)) {
            LedgerErrorMessage.LEDGER_NOT_EXIST.send(player);
        }

        ledgerGroupRegistry.removeGroup(player);

        LedgerConfigMessage.LEDGER_SCRAPPED.send(player);

        return true;
    }
}
