package oschwa.ledger.enums;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum LedgerErrorMessage {
    MEMBER_NOT_FOUND(ChatColor.RED + "[Ledger] %s is not a member of your Ledger."),
    LEDGER_NOT_EXIST(ChatColor.RED + "[Ledger] You do not have an active Ledger."),
    NO_PLAYER_NAME(ChatColor.RED + "[Ledger] You must provide a player name."),
    MEMBER_EXISTS(ChatColor.RED + "[Ledger] %s is already a member of your Ledger."),
    NO_PLAYER(ChatColor.RED + "[Ledger] %s does not exist on this server."),
    LEDGER_EXISTS(ChatColor.RED + "[Ledger] You already have a Ledger."),
    CANNOT_ADD_OWNER(ChatColor.RED + "[Ledger] You cannot add yourself to your own Ledger."),
    PLAYER_ONLY(ChatColor.RED + "[Ledger] /%s may only be used by players."),
    NOT_IN_LEDGER(ChatColor.RED + "[Ledger] You are not part of an active Ledger."),
    OWNER_CANNOT_LEAVE(ChatColor.RED + "[Ledger] You cannot leave your own Ledger."),
    MUST_BE_OWNER_FOR_LABEL(ChatColor.RED + "[Ledger] You must be a Ledger owner to create a new label."),
    INVALID_LABEL_ARG(ChatColor.RED + "[Ledger] Too many arguments for new label creation."),
    MUST_PROVIDE_LABEL_ARG(ChatColor.RED + "[Ledger] You must provide a label name."),
    LABEL_DOES_NOT_EXIST(ChatColor.RED + "[Ledger] Label does not exist in this Ledger.");

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

    public void send(CommandSender commandSender, Object... args) {
        commandSender.sendMessage(String.format(message, args));
    }
}
