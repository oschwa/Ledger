package oschwa.ledger.labels;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import oschwa.ledger.LedgerPlugin;

public class Label {
    private final String name;
    private final ItemStack labelItem;

    public Label(String name) {
        this.name = name;

        ItemStack labelItem = new ItemStack(Material.NAME_TAG);
        ItemMeta labelMeta = labelItem.getItemMeta();

        labelMeta.customName(Component.text(this.name));

        labelMeta.getPersistentDataContainer()
                .set(new NamespacedKey(LedgerPlugin.getPlugin(),
                        "ledger_label"), PersistentDataType.STRING,
                        name);

        labelItem.setItemMeta(labelMeta);

        this.labelItem = labelItem;
    }

    public ItemStack getLabelItem() {
        return labelItem;
    }

    public String getName() {
        return name;
    }

    public static Label builder(String name) {
        return new Label(name);
    }
}
