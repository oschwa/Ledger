package oschwa.ledger.listeners;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class LabelListener implements Listener {
    @EventHandler
    public void onLabelToChestInteract(PlayerInteractEvent event) {

        if (!(event.getClickedBlock() instanceof Chest)) return;

        ItemStack item = event.getPlayer().getActiveItem();

        if (item.getType() != Material.NAME_TAG) return;




    }
}
