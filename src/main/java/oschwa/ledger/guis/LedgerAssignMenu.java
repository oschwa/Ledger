package oschwa.ledger.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import oschwa.ledger.LedgerPlugin;
import oschwa.ledger.labels.Label;

import java.util.HashMap;
import java.util.Map;

public class LedgerAssignMenu implements Listener {

    private final Player player;

    private Inventory menu;

    private Inventory playerInventory;

    private Map<Integer, ItemStack> prevIndexMap;

    private Label label;

    private final int SIZE = 36;

    public LedgerAssignMenu(Player player, Label label) {

        this.player = player;

        this.label = label;

        String title = ChatColor.YELLOW + label.getName();

        menu = Bukkit.createInventory(player, SIZE,
                "Assign items to " + title);

        playerInventory = player.getInventory();

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

        //  populate menu with existing items.

        Map<ItemStack, Integer> contents = label.getLabelContents();

        for (Map.Entry<ItemStack, Integer> entry : contents.entrySet()) {

            menu.setItem(entry.getValue(), entry.getKey());

        }

        Bukkit.getServer().getPluginManager()
                .registerEvents(this, LedgerPlugin.getPlugin());
    }

    public void showMenu(Player player) {
        player.openInventory(menu);
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {

        if (menu == null) return;

        if (event.getClickedInventory() != playerInventory) return;

        if (event.getCurrentItem().getType() == Material.AIR) return;

        menu.setItem(0, event.getCurrentItem());
    }
}
