package oschwa.ledger.labels;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import oschwa.ledger.LedgerPlugin;
import oschwa.ledger.enums.LedgerErrorMessage;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Label {
    private final String name;

    private final ItemStack labelItem;

    private final PersistentDataContainer pdc;

    private final NamespacedKey key;

    public Label(String name) {
        this.name = name;

        this.key = new NamespacedKey(LedgerPlugin.getPlugin(), "label_config");

        ItemStack labelItem = new ItemStack(Material.NAME_TAG);

        ItemMeta labelMeta = labelItem.getItemMeta();

        labelMeta.customName(Component.text(this.name));

        //  assign label item meta.

        PersistentDataContainer pdc = labelMeta.getPersistentDataContainer();

        this.pdc = pdc;

        pdc.set(key, PersistentDataType.STRING, name);

        //  assign map to as persistent data using Json.

        Map<String, String> config = new HashMap<>();

        Gson gson = new Gson();

        String json = gson.toJson(config);

        pdc.set(key, PersistentDataType.STRING, json);

        labelItem.setItemMeta(labelMeta);

        this.labelItem = labelItem;
    }

    public void assignItem(ItemStack item, Integer slot) {

        //  get persistent map.

        Optional<Map<String, String>> config = getMap();

        if (config.isEmpty()) return;

        //  add item.

        Material material = Material.getMaterial(item.getItemMeta().getAsString());

        String materialName = material.name();

        config.get().put(slot.toString(), materialName);

        //  assign map as persistent data using Json.

        saveMap(config.get());
    }

    private void saveMap(Map<String, String> config) {

        //  save map

        Gson gson = new Gson();

        String newMapJson = gson.toJson(config);

        pdc.set(key, PersistentDataType.STRING, newMapJson);

    }

    private Optional<Map<String, String>> getMap() {

        //  get persistent map.

        String currMapJson = pdc.get(key, PersistentDataType.STRING);

        if (currMapJson == null) return Optional.empty();

        Type type = new TypeToken<Map<String, String>>(){}.getType();

        Map<String, String> config = new Gson().fromJson(currMapJson, type);

        return Optional.ofNullable(config);

    }

    public Map<Integer, ItemStack> getLabelContents() {

        //  get map from data container.

        Optional<Map<String, String>> config = getMap();

        if (config.isEmpty()) return null;

        //  adapt strings arguments to chest slots and Bukkit materials.

        Map<Integer, ItemStack> contents = new HashMap<>();

        for (Map.Entry<String, String> entry : config.get().entrySet()) {

            int slot = Integer.parseInt(entry.getKey());

            Material material = Material.getMaterial(entry.getValue());

            ItemStack item = new ItemStack(material);

            contents.put(slot, item);
        }

        return contents;
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
