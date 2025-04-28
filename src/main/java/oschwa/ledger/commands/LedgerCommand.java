package oschwa.ledger.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LedgerCommand implements CommandExecutor {

    private String[] manual;

    public LedgerCommand() {
        manual = new String[] {
                "/ledger -> manual page"
        };
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String @NotNull [] strings) {

        if (!(commandSender instanceof Player player)) {
            commandSender.sendPlainMessage("Only players can make a new Ledger.");
            return false;
        }

        if (command.getName().equalsIgnoreCase("ledger")) {
            player.sendMessage(manual);
        }

        return true;
    }
}
