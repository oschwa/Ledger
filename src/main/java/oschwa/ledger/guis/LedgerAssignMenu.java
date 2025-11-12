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

    private Map<ItemStack, Integer> prevIndexMap;

    private Label label;
    private final int size;

    public LedgerAssignMenu(Player player, Label label) {
        this.player = player;
        this.label = label;
        size = 36;
        menu = Bukkit.createInventory(player, size,
                "Assign items to " + label.getName());

        this.prevIndexMap = new HashMap<>();

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
    public void onItemSelect(InventoryClickEvent e) {
        if (e.getClickedInventory() != menu) return;

        ItemStack cursor = e.getCursor();

        if (cursor.getType().isAir()) return;

        if (prevIndexMap.containsKey(cursor) &&
                prevIndexMap.get(cursor) == e.getSlot()) {
            prevIndexMap.remove(cursor);
        }

        prevIndexMap.put(cursor, e.getSlot());
    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent e) {
        Inventory inventory = player.getInventory();

        for (Map.Entry<ItemStack, Integer> entry : prevIndexMap.entrySet()) {
            inventory.setItem(entry.getValue(), entry.getKey());
        }
    }
}
