package oschwa.ledger.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import oschwa.ledger.enums.LedgerConfigMessage;
import oschwa.ledger.enums.LedgerErrorMessage;
import oschwa.ledger.player.LedgerGroup;
import oschwa.ledger.registries.LedgerGroupRegistry;
import java.util.Optional;

public class NewLabelCommand implements CommandExecutor {

    private final LedgerGroupRegistry ledgerGroupRegistry;

    public NewLabelCommand(LedgerGroupRegistry ledgerGroupRegistry) {
        this.ledgerGroupRegistry = ledgerGroupRegistry;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player)) {
            LedgerErrorMessage.PLAYER_ONLY.send(commandSender);
            return true;
        }

        Player player = (Player) commandSender;

        Optional<LedgerGroup> ledgerGroup = ledgerGroupRegistry.getGroupByOwner(player);

        if (ledgerGroup.isEmpty()) {
            LedgerErrorMessage.MUST_BE_OWNER_FOR_LABEL.send(player);
            return true;
        }

        if (strings.length != 1) {
            LedgerErrorMessage.INVALID_LABEL_ARG.send(player);
            return true;
        }

        String labelName = strings[0];

        ItemStack label = new ItemStack(Material.NAME_TAG);
        ItemMeta labelMeta = label.getItemMeta();

        labelMeta.displayName(Component.text(labelName));
        label.setItemMeta(labelMeta);

        player.getInventory().addItem(label);

        LedgerConfigMessage.NEW_LABEL.send(player, label.displayName());

        return true;
    }
}
