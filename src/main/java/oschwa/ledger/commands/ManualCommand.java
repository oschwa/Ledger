package oschwa.ledger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ManualCommand implements CommandExecutor {
    private final String[] manual;

    public ManualCommand() {
        manual = new String[] {
                "/ledger man -> manual page",
                "/ledger new -> create a new Ledger",
                "/ledger scrap -> delete your existing Ledger",
                "/ledger add [player name] -> add a player to your Ledger",
                "/ledger leave -> leave another player's Ledger",
                "/ledger members -> view names of player in your Ledger"
        };
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) return false;
        commandSender.sendMessage(manual);
        return true;
    }
}
