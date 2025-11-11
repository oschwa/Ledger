package oschwa.ledger.commands;

import org.bukkit.Bukkit;
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

public class AddCommand implements CommandExecutor {

    private final LedgerRegistry ledgerRegistry;

    public AddCommand(LedgerRegistry ledgerRegistry) {
        this.ledgerRegistry = ledgerRegistry;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String @NotNull [] strings) {

        if (!(commandSender instanceof Player)) {
            LedgerErrorMessage.PLAYER_ONLY.send(commandSender);
            return true;
        }

        Player player = (Player) commandSender;

        Optional<Ledger> ledgerGroup =
                ledgerRegistry.get(player);

        if (ledgerGroup.isEmpty()) {
            LedgerErrorMessage.LEDGER_NOT_EXIST.send(player);
            return true;
        }

        if (strings.length == 0) {
            LedgerErrorMessage.NO_PLAYER_NAME.send(player);
            return true;
        }

        Optional<Player> otherPlayer =
                Optional.ofNullable(Bukkit.getPlayer(strings[0]));

        if (otherPlayer.isEmpty()) {
            LedgerErrorMessage.NO_PLAYER.send(player, strings[0]);
            return true;
        }

        if (otherPlayer.get().equals(player)) {
            LedgerErrorMessage.CANNOT_ADD_OWNER.send(player);
            return true;
        }

        if (ledgerGroup.get().hasMember(otherPlayer.get().getUniqueId())) {
            LedgerErrorMessage.MEMBER_EXISTS.send(player, otherPlayer.get().getName());
            return true;
        }

        ledgerGroup.get().addMember(otherPlayer.get());
        LedgerConfigMessage.NEW_MEMBER_ADDED.send(player, otherPlayer.get().getName());

        return true;
    }
}
