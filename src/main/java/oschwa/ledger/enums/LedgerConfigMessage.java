package oschwa.ledger.enums;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum LedgerConfigMessage {
    NEW_LEDGER(ChatColor.YELLOW + "[Ledger] New Ledger created."),
    NEW_MEMBER_ADDED(ChatColor.YELLOW + "[Ledger] %s has been added to your Ledger."),
    LEDGER_SCRAPPED(ChatColor.YELLOW + "[Ledger] Ledger scrapped."),
    MEMBER_REMOVED(ChatColor.YELLOW + "[Ledger] %s has been removed from your Ledger."),
    LEFT_LEDGER(ChatColor.YELLOW + "[Ledger] You have left %s's Ledger."),
    LEDGER_SCRAP_NOTIF(ChatColor.YELLOW + "[Ledger] %s's Ledger has been scrapped, you are no longer part of an active Ledger."),
    NEW_LABEL(ChatColor.YELLOW + "[Ledger] New label %s created."),
    LABEL_GET_SUCCESSFUL(ChatColor.YELLOW + "[Ledger] Label '%s' retrieved."),
    SUCCESSFUL_ASSIGNMENT(ChatColor.YELLOW + "[Ledger] %s successfully assigned."),
    NO_LABELS(ChatColor.YELLOW + "[Ledger] No currently registered labels"),
    ITEMS_ADDED(ChatColor.YELLOW + "[Ledger] Materials successfully assigned to '%s'");

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
