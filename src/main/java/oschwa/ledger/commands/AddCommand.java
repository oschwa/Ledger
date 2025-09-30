package oschwa.ledger.commands;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import oschwa.ledger.Ledger;
import oschwa.ledger.enums.LedgerConfigMessage;
import oschwa.ledger.enums.LedgerErrorMessage;
import oschwa.ledger.exceptions.GroupDoesNotExistException;
import oschwa.ledger.exceptions.MemberExistsException;
import oschwa.ledger.registries.LedgerGroupRegistry;

import java.util.Optional;

public class AddCommand implements CommandExecutor {

    private final LedgerGroupRegistry ledgerGroupRegistry;

    public AddCommand(LedgerGroupRegistry ledgerGroupRegistry) {
        this.ledgerGroupRegistry = ledgerGroupRegistry;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String @NotNull [] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) commandSender;

        if (!ledgerGroupRegistry.containsGroup(player)) {
            LedgerErrorMessage.LEDGER_NOT_EXIST.send(player);
            return true;
        }

        if (strings.length == 0) {
            LedgerErrorMessage.NO_PLAYER_NAME.send(player);
            return true;
        }

        Optional<Player> otherPlayer = Optional.ofNullable(Bukkit.getPlayer(strings[0]));

        if (otherPlayer.isEmpty()) {
            LedgerErrorMessage.NO_PLAYER.send(player, strings[0]);
            return true;
        }

        if (otherPlayer.get().equals(player)) {
            LedgerErrorMessage.CANNOT_ADD_OWNER.send(player);
            return true;
        }

        if (ledgerGroupRegistry.getGroup(player).hasMember(otherPlayer.get().getUniqueId())) {
            LedgerErrorMessage.MEMBER_EXISTS.send(player, otherPlayer.get().getName());
            return true;
        }

        ledgerGroupRegistry.getGroup(player).addMember(otherPlayer.get());
        LedgerConfigMessage.NEW_MEMBER_ADDED.send(player, otherPlayer.get().getName());

        return true;
    }
}
