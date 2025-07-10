package oschwa.ledger.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ManualCommand implements CommandExecutor {
    private final String[] manual;

    public ManualCommand() {
        manual = new String[] {
                ChatColor.YELLOW + "/ledger:man -> manual page",
                ChatColor.YELLOW + "/ledger:new -> create a new Ledger",
                ChatColor.YELLOW + "/ledger:scrap -> delete your existing Ledger",
                ChatColor.YELLOW + "/ledger:add [player name] -> add a player in the server to your Ledger",
                ChatColor.YELLOW + "/ledger:leave -> leave another player's Ledger",
                ChatColor.YELLOW + "/ledger:members -> view names of player in your Ledger"
        };
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) return false;
        commandSender.sendMessage(manual);
        return true;
    }
}
