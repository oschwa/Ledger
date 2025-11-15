package oschwa.ledger.guis;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import oschwa.ledger.LedgerPlugin;
import oschwa.ledger.labels.Label;

import java.util.HashMap;
import java.util.Map;

public class LedgerAssignMenu implements Listener {

    private final Player player;

    private Inventory menu;

    private Map<Integer, ItemStack> prevIndexMap;

    private Label label;

    private final int SIZE = 36;

    public LedgerAssignMenu(Player player, Label label) {
        this.player = player;

        this.label = label;

        menu = Bukkit.createInventory(player, SIZE,
                "Assign items to " + label.getName());

        this.prevIndexMap = new HashMap<>();

        //  clone player inventory to repopulate
        //  upon menu close.

        Inventory inventory = player.getInventory();

        for (int i = 0; i < inventory.getSize(); i++) {

            ItemStack item = inventory.getItem(i);

            if (item != null) {

                prevIndexMap.put(i, item.clone());

            }
        }

        Bukkit.getServer().getPluginManager()
                .registerEvents(this, LedgerPlugin.getPlugin());
    }

    public void showMenu(Player player) {
        player.openInventory(menu);
    }

    @EventHandler
    public void onItemAssign(InventoryClickEvent e) {
        if (e.getClickedInventory() != menu) return;

        ItemStack cursor = e.getCursor();

        if (cursor.getType().isAir()) return;

        //  TODO: logic for assigning Material to Label.
    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player)) return;

        if (!(e.getPlayer().equals(this.player))) return;

        if (!(e.getInventory().equals(menu))) return;

        Inventory inventory = player.getInventory();

        for (Map.Entry<Integer, ItemStack> entry : prevIndexMap.entrySet()) {

            inventory.setItem(entry.getKey(), entry.getValue());

        }

    }
}
