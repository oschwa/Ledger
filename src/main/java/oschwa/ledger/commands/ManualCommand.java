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
                ChatColor.YELLOW + "==============================",
                ChatColor.YELLOW + "How to use Ledger:",
                ChatColor.YELLOW + "==============================",
                ChatColor.YELLOW + "/man " + ChatColor.WHITE + "- manual page for plugin.",
                ChatColor.YELLOW + "/new " + ChatColor.WHITE + "- create and register a new Ledger.",
                ChatColor.YELLOW + "/scrap " + ChatColor.WHITE + "- delete your existing Ledger.",
                ChatColor.YELLOW + "/add [player name] " + ChatColor.WHITE + "- add a player in the server to your Ledger.",
                ChatColor.YELLOW + "/leave " + ChatColor.WHITE + "- leave another player's Ledger.",
                ChatColor.YELLOW + "/list " + ChatColor.WHITE + "- view all players in your Ledger."
        };
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) return false;
        commandSender.sendMessage(manual);
        return true;
    }
}
