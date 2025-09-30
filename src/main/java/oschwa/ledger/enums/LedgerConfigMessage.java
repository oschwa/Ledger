package oschwa.ledger.enums;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum LedgerConfigMessage {
    NEW_LEDGER(ChatColor.YELLOW + "[Ledger] New Ledger created."),
    NEW_MEMBER_ADDED(ChatColor.YELLOW + "[Ledger] %s has been added to your Ledger."),
    LEDGER_SCRAPPED(ChatColor.YELLOW + "[Ledger] Ledger scrapped."),
    MEMBER_REMOVED(ChatColor.YELLOW + "[Ledger] %s has been removed from your Ledger");

    private final String message;

    LedgerConfigMessage(String message) {
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
