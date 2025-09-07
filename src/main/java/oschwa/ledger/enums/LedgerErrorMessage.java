package oschwa.ledger.enums;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum LedgerErrorMessage {
    MEMBER_NOT_FOUND(ChatColor.RED + "[Ledger] %s is not a member of your Ledger."),
    LEDGER_NOT_EXIST(ChatColor.RED + "[Ledger] You do not have an active Ledger."),
    NO_PLAYER_NAME(ChatColor.RED + "[Ledger] You must provide a player name."),
    MEMBER_EXISTS(ChatColor.RED + "[Ledger] %s is already a member of your Ledger."),
    NO_PLAYER(ChatColor.RED + "[Ledger] %s does not exist on this server."),
    LEDGER_EXISTS(ChatColor.RED + "[Ledger] You already have a Ledger.");

    private final String message;

    LedgerErrorMessage(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }

    public void send(Player player) {
        player.sendMessage(message);
    }

    public void send(Player player, Object... args) {
        player.sendMessage(String.format(message, args));
    }
}
