package oschwa.ledger.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import oschwa.ledger.LedgerPlugin;
import oschwa.ledger.enums.LedgerErrorMessage;
import oschwa.ledger.labels.Label;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LedgerAssignMenu implements Listener {

    private final Player player;

    private Inventory menu;

    private Inventory playerInventory;

    private Label label;

    private Map<Integer, ItemStack> prevSlotMap;

    private final int SIZE = 36;

    private int prevSlot;

    public LedgerAssignMenu(Player player, Label label) {
        this.player = player;

        this.label = label;

        String title = ChatColor.YELLOW + label.getName();

        menu = Bukkit.createInventory(player, SIZE,
                "Assign items to " + title);

        playerInventory = player.getInventory();

        prevSlot = -1;

        //  clone player inventory to repopulate
        //  upon menu close.

        prevSlotMap = new HashMap<>();

        for (int i = 0; i < SIZE; i++) {
            if (playerInventory.getItem(i) == null) continue;

            prevSlotMap.put(i, playerInventory.getItem(i));
        }

        //  populate menu with existing items.

        Map<ItemStack, Integer> contents = label.getLabelContents();

        for (Map.Entry<ItemStack, Integer> entry : contents.entrySet()) {

            menu.setItem(entry.getValue(), entry.getKey());

        }

        Bukkit.getServer().getPluginManager()
                .registerEvents(this, LedgerPlugin.getPlugin());
    }

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e) {

        if (menu == null) return;

        if (!(e.getClickedInventory() instanceof PlayerInventory)) return;

        if (e.getWhoClicked() != player) return;

        if (e.getCursor().getType() == Material.AIR &&
            e.getCurrentItem() == null) return;

        InventoryAction action = e.getAction();

        if (action == InventoryAction.PICKUP_ALL ||
                action == InventoryAction.PICKUP_HALF ||
                action == InventoryAction.PICKUP_ONE ||
                action == InventoryAction.PICKUP_SOME) {
            Bukkit.getLogger().info("Item picked up.");
            prevSlot = e.getSlot();
        }

        else if (action == InventoryAction.PLACE_ALL
                || action == InventoryAction.PLACE_SOME
                || action == InventoryAction.PLACE_ONE) {
            if (prevSlot == -1) return;

            Bukkit.getLogger().info("Prev slot is : " + prevSlot);

            if (prevSlotMap.get(prevSlot) != null &&
                    prevSlotMap.get(prevSlot).isSimilar(e.getCursor())) {
                e.setCancelled(true);
                LedgerErrorMessage.CANNOT_MUTATE_INVENTORY.send(player);
                Bukkit.getLogger().info("Attempting to place item in inventory. Not allowed.");
            }

            prevSlot = -1;
        }
    }


    public void showMenu(Player player) {
        player.openInventory(menu);
    }
}
