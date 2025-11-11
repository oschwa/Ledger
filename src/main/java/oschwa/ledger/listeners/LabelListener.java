package oschwa.ledger.listeners;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import oschwa.ledger.LedgerPlugin;
import oschwa.ledger.enums.LedgerConfigMessage;
import oschwa.ledger.enums.LedgerErrorMessage;
import oschwa.ledger.registries.ChestRegistry;

public class LabelListener implements Listener {

    private final ChestRegistry chestRegistry;

    public LabelListener(ChestRegistry chestRegistry) {
        this.chestRegistry = chestRegistry;
    }

    @EventHandler
    public void onLabelToChestClick(PlayerInteractEvent e) {

        Player player = e.getPlayer();

        if (!e.getAction().isRightClick()) return;

        Block block = player.getTargetBlockExact(5);

        if (block == null) return;

        ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();

        NamespacedKey key = new NamespacedKey(LedgerPlugin.getPlugin(),
                "ledger_label");

        if (!itemMeta.getPersistentDataContainer().has(key)) return;

        if (block.getBlockData().getMaterial() != Material.CHEST) {
            LedgerErrorMessage.NOT_CHEST.send(player);
            return;
        }

        LedgerConfigMessage.SUCCESSFUL_ASSIGNMENT.send(player,
                itemMeta.getPersistentDataContainer().get(key, PersistentDataType.STRING));

        //  prevent chest GUI from appearing.
        e.setCancelled(true);
    }
}
