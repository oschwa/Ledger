package oschwa.ledger.listeners;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import oschwa.ledger.LedgerPlugin;
import oschwa.ledger.enums.LedgerConfigMessage;
import oschwa.ledger.enums.LedgerErrorMessage;
import oschwa.ledger.labels.Label;
import oschwa.ledger.player.Ledger;
import oschwa.ledger.registries.ChestRegistry;
import oschwa.ledger.registries.LedgerRegistry;

import java.util.Optional;

public class LabelListener implements Listener {

    private final LedgerRegistry ledgerRegistry;
    private final ChestRegistry chestRegistry;

    public LabelListener(LedgerRegistry ledgerRegistry, ChestRegistry chestRegistry) {
        this.ledgerRegistry = ledgerRegistry;
        this.chestRegistry = chestRegistry;
    }

    @EventHandler
    public void onLabelToChestClick(PlayerInteractEvent e) {

        Player player = e.getPlayer();

        //  determine if player is registered in any ledger.

        Optional<Ledger> ledger = ledgerRegistry.get(e.getPlayer());

        if (ledger.isEmpty()) return;

        //  determine if the player right-clicked on a chest
        //  with a ledger label.

        if (!e.getAction().isRightClick()) return;

        ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();

        NamespacedKey key = new NamespacedKey(LedgerPlugin.getPlugin(),
                "ledger_label");

        if (!itemMeta.getPersistentDataContainer().has(key)) return;

        Optional<Label> label = ledger.get()
                .getLabel(itemMeta.getPersistentDataContainer()
                        .get(key, PersistentDataType.STRING));

        if (label.isEmpty()) return;

        Block block = player.getTargetBlockExact(5);

        if (block == null) return;

        if (!(block.getState() instanceof Chest)) {
            LedgerErrorMessage.NOT_CHEST.send(player);
            return;
        }

        chestRegistry.add(label.get(), (Chest) block.getState());

        LedgerConfigMessage.SUCCESSFUL_ASSIGNMENT.send(player,
                itemMeta.getPersistentDataContainer().get(key, PersistentDataType.STRING));

        //  prevent chest GUI from appearing.
        e.setCancelled(true);
    }
}
